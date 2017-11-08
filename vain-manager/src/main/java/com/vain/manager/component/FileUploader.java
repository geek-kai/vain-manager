package com.vain.manager.component;

import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WriteRender;
import com.jcraft.jsch.ChannelSftp;
import com.aliyun.oss.OSSClient;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.vain.manager.constant.SysConfigKeys;
import com.vain.manager.dao.UploadFileDao;
import com.vain.manager.entity.UploadFile;
import com.vain.manager.shiro.session.UserSession;
import com.vain.manager.util.StrUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * @author vain
 * @date： 2017/11/8 9:15
 * @description： 文件上传
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileUploader {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysConfigComponent sysConfigComponent;

    @Autowired
    private UploadFileDao uploadFileDao;

    private Session session;

    private ChannelSftp channelSftp;

    /**
     * upload:一次性上传文件, 调用方无需管理session（sftp）
     *
     * @param is
     * @param uploadFile
     * @return
     * @throws Exception
     */
    private UploadFile SftpUpload(InputStream is, UploadFile uploadFile) throws Exception {
        try {
            //连接sftp
            init();
            uploadFile.setPath(combinePath(uploadFile.getUUID()));
            initDir(uploadFile.getPath().substring(0, uploadFile.getPath().lastIndexOf('/')));
            channelSftp.put(is, uploadFile.getPath());
            // 上传成功后，设置url
            uploadFile.setUrl(combineUrl(uploadFile.getUUID()));
            uploadFile.setLength(getUploadedLength(uploadFile));
            // 存入数据库
            uploadFileDao.insert(uploadFile);
        } finally {
            IOUtils.close(is);
            destroy();
        }
        return uploadFile;
    }

    /**
     * uploadStep:分步上传文件, 调用方需要管理session
     *
     * @param is
     * @param uploadFile
     * @return
     * @throws Exception
     */
    public UploadFile uploadStep(InputStream is, UploadFile uploadFile) throws Exception {
        try {
            if (uploadFile.getPath() == null) {
                uploadFile.setPath(combinePath(uploadFile.getUUID()));
                initDir(uploadFile.getPath().substring(0, uploadFile.getPath().lastIndexOf('/')));
            }
            channelSftp.put(is, uploadFile.getPath(), ChannelSftp.APPEND);
        } finally {
            IOUtils.close(is);
        }
        return uploadFile;
    }

    /**
     * uploadStepCompleted:分步上传文件完成
     *
     * @param uploadFile
     * @return
     * @throws Exception
     */
    public UploadFile uploadStepCompleted(UploadFile uploadFile) throws Exception {
        // 上传成功后，设置url
        uploadFile.setUrl(combineUrl(uploadFile.getUUID()));
        // 存入数据库
        uploadFileDao.insert(uploadFile);
        return uploadFile;
    }

    /**
     * getUploadedLength:获取已经上传的文件的大小
     *
     * @param uploadFile
     * @return
     */
    private long getUploadedLength(UploadFile uploadFile) throws Exception {
        try {
            if (uploadFile.getPath() == null) {
                uploadFile.setPath(combinePath(uploadFile.getUUID()));
            }
            @SuppressWarnings("rawtypes")
            Vector vector = channelSftp.ls(uploadFile.getPath());
            for (Object obj : vector) {
                if (obj instanceof ChannelSftp.LsEntry) {
                    ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) obj;
                    return lsEntry.getAttrs().getSize();
                }
            }
        } catch (SftpException e) {
            logger.warn(e.getMessage(), e);
            // 文件不存在时，返回0l
            if (e.getMessage().contains("No such file")) {
                return 0L;
            }
            throw e;
        }
        return 0L;
    }

    public void deleteFile(UploadFile uploadFile) throws Exception {
        if (uploadFile.getPath() == null) {
            uploadFile.setPath(combinePath(uploadFile.getUUID()));
        }
        channelSftp.rm(uploadFile.getPath());
    }

    /**
     * init:初始化session，用于分步上传的场景
     *
     * @throws Exception
     */
    private void init() throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(
                sysConfigComponent.getStringValue(SysConfigKeys.SYS_FILE_UPLOAD_HOST_USER),
                sysConfigComponent.getStringValue(SysConfigKeys.SYS_FILE_UPLOAD_HOST_IP),
                sysConfigComponent.getIntValue(SysConfigKeys.SYS_FILE_UPLOAD_HOST_PORT));
        session.setPassword(sysConfigComponent.getStringValue(SysConfigKeys.SYS_FILE_UPLOAD_HOST_PASSWD));
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();

        channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
    }

    /**
     * destroy:资源回收，用于分步上传的场景
     */
    private void destroy() {
        if (channelSftp != null && channelSftp.isConnected()) {
            channelSftp.disconnect();
        }

        if (session != null && session.isConnected()) {
            session.disconnect();

        }
    }

    private void initDir(String dir) throws Exception {
        try {
            channelSftp.stat(dir);
            logger.debug("dir " + dir + " already exists");
        } catch (SftpException e) {
            // 目录不存在，需要逐级创建
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                String[] tmps = dir.split("/");
                String subDir = "";
                for (String tmp : tmps) {
                    if (tmp.length() == 0) {
                        continue;
                    }

                    subDir += "/" + tmp;

                    try {
                        channelSftp.stat(subDir);
                    } catch (SftpException e1) {
                        // 目录不存在，从前往后依次创建
                        if (e1.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                            channelSftp.mkdir(subDir);
                            logger.debug("mkdir " + subDir);
                        } else {
                            throw e1;
                        }
                    }

                }
            } else {
                throw e;
            }
        }
    }

    private String combinePath(String uid) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysConfigComponent.getStringValue(SysConfigKeys.SYS_FILE_UPLOAD_ROOT_PATH));
        sb.append(uid);
        return sb.toString();
    }

    private String combineUrl(String uid) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysConfigComponent.getStringValue(SysConfigKeys.SYS_FILE_UPLOAD_ROOT_URL));
        sb.append(uid);
        return sb.toString();
    }


    /**
     * 使用阿里云的oss
     * uploadFile 文件传空的时候 UUID为日期 type UUID 拼接
     *
     * @param is
     * @param uploadFile
     * @return
     * @throws Exception
     * @see com.vain.manager.entity.UploadFile
     */
    private UploadFile OSSUpload(InputStream is, UploadFile uploadFile) throws Exception {
        //获取数据库配置 创建oss客户端
        OSSClient ossClient = new OSSClient(sysConfigComponent.getStringValue(SysConfigKeys.OSS_ENDPOINT),
                sysConfigComponent.getStringValue(SysConfigKeys.OSS_ACCESS_KEYID),
                sysConfigComponent.getStringValue(SysConfigKeys.OSS_ACCESSKEY_SECRET));
        //设置文件大小
        uploadFile.setLength((long) is.available());
        //上传文件
        ossClient.putObject(sysConfigComponent.getStringValue(SysConfigKeys.OSS_BUCKETNAME),
                (uploadFile.getUUID()), is);
        // 上传成功后，拼接url
        uploadFile.setUrl(combineOSSUrl(uploadFile.getUUID()));
        // 存入数据库
        uploadFileDao.insert(uploadFile);
        //释放资源
        ossClient.shutdown();
        return uploadFile;
    }

    /**
     * 拼接oss访问路径
     *
     * @param uid
     * @return
     */
    private String combineOSSUrl(String uid) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysConfigComponent.getStringValue(SysConfigKeys.OSS_FILE_URL)).append("/").append(uid);
        return sb.toString();
    }

    /**
     * 上传
     *
     * @param file 文件
     * @param type 类型
     * @param path 如果需要自定义
     * @return
     * @throws Exception
     */
    public UploadFile uploadFile(MultipartFile file, int type, String path) throws Exception {
        if (file == null)
            return null;
        File temp = null;
        File out = null;
        BufferedImage read = null;
        //上传文件实体
        UploadFile uploadFile = new UploadFile(StrUtil.isBlank(path) ? null : path, new String(file.getOriginalFilename().getBytes("iso-8859-1"), "utf-8"),
                type, file.getSize(), UserSession.getUserId());
        read = ImageIO.read(file.getInputStream());// 获取图片的宽高
        uploadFile.setWidth(read.getWidth());
        uploadFile.setHeight(read.getHeight());
        if (file.getSize() > 1024 * 150) { //大于150kb才去压缩
            temp = File.createTempFile("pattern", "temp.jpg");//临时文件
            out = File.createTempFile("pattern", "out.jpg");//SimpleImage临时文件
            ScaleParameter scaleParam = new ScaleParameter(read.getWidth(), read.getHeight());  //将图像分辨缩略到3200x3200以内，不足3200x3200则不做任何处理
            InputStream inStream = null;
            FileOutputStream outStream = null;
            WriteRender wr = null;
            try {
                inStream = file.getInputStream();
                //图片泛红处理
                outStream = new FileOutputStream(out);
                ImageRender rr = new ReadRender(inStream);
                ImageRender sr = new ScaleRender(rr, scaleParam);
                wr = new WriteRender(sr, outStream);
                wr.render(); //触发图像处理
                //400k以上取0.8  150-400k取0.9  150以下不压缩  长图取原像素比例压缩
                Thumbnails.of(out).scale(1f).outputQuality(file.getSize() > 1024 * 400 ? 0.8f : 0.9f).outputFormat("jpg").toFile(temp);
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                if (inStream != null)
                    inStream.close();
                if (outStream != null)
                    outStream.close();
                if (wr != null) {
                    try {
                        wr.dispose();                   //释放simpleImage的内部资源
                    } catch (SimpleImageException ignore) {
                        logger.error(ignore.getMessage());
                    }
                }
            }
        }
        //上传到oss
        //OSSUpload(temp != null ? new FileInputStream(temp) : file.getInputStream(), uploadFile);
        SftpUpload(file.getInputStream(),uploadFile);

        //删除缓存文件
        if (temp != null)
            temp.deleteOnExit();
        if (out != null)
            out.deleteOnExit();
        return uploadFile;
    }
}
