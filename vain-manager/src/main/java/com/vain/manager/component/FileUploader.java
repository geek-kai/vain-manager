package com.vain.manager.component;

import com.alibaba.fastjson.util.IOUtils;
import com.jcraft.jsch.ChannelSftp;
import com.aliyun.oss.OSSClient;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.vain.manager.constant.SysConfigKeys;
import com.vain.manager.dao.UploadFileDao;
import com.vain.manager.entity.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

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
     * upload:一次性上传文件, 调用方无需管理session
     *
     * @param is
     * @param uploadFile
     * @return
     * @throws Exception
     */
    public UploadFile upload(InputStream is, UploadFile uploadFile) throws Exception {

        try {

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
     * 使用阿里云的oss
     *
     * @param is
     * @param uploadFile
     * @return
     * @throws Exception
     */
    public UploadFile OSSUpload(InputStream is, UploadFile uploadFile) throws Exception {
        //获取数据库配置 创建oss客户端
        OSSClient ossClient = new OSSClient(sysConfigComponent.getStringValue(SysConfigKeys.OSS_ENDPOINT),
                sysConfigComponent.getStringValue(SysConfigKeys.OSS_ACCESS_KEYID),
                sysConfigComponent.getStringValue(SysConfigKeys.OSS_ACCESSKEY_SECRET));
        //oss根据文件路径来创建文件夹
        uploadFile.setLength((long) is.available());
        //上传文件
        ossClient.putObject(sysConfigComponent.getStringValue(SysConfigKeys.OSS_BUCKETNAME),
                ("ikaymall" + uploadFile.getUUID()),
                is);

        // 上传成功后，设置url
        uploadFile.setUrl(combineOSSUrl(uploadFile.getUUID()));

        // 存入数据库
        uploadFileDao.insert(uploadFile);

        //释放资源
        ossClient.shutdown();
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
    public long getUploadedLength(UploadFile uploadFile) throws Exception {

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
                return 0l;
            }

            throw e;
        }

        return 0l;

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
    public void destroy() {
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
     * 拼接oss访问路径
     *
     * @param uid
     * @return
     */
    private String combineOSSUrl(String uid) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysConfigComponent.getStringValue(SysConfigKeys.OSS_FILE_URL));
        sb.append("/vain");
        sb.append(uid);
        return sb.toString();
    }
}
