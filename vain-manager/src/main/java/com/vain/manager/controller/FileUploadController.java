package com.vain.manager.controller;

import com.alibaba.fastjson.JSON;
import com.vain.manager.common.entity.Response;
import com.vain.manager.component.FileUploader;
import com.vain.manager.entity.UploadFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vain
 * @date： 2017/11/8 9:34
 * @description： 文件上传
 */
@RequestMapping(value = "/upload")
@Controller
public class FileUploadController {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    private FileUploader fileUploader;

    //图片上传接口
    @RequestMapping(value = "/uploadPics", method = RequestMethod.POST)
    public void uploadPics(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Response<UploadFile> res = new Response<>();
        List<UploadFile> pics = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadFile uploadFile = fileUploader.uploadFile(file, UploadFile.TYPE_PHOTO, "vain");
            pics.add(uploadFile);
        }
        res.setData(pics);
        // 解决IE9弹下载框的问题
        response.setContentType("text/html");
        // 解决返回参数乱码
        response.setCharacterEncoding("UTF-8");

        String respBody = JSON.toJSONString(res);
        logger.info(respBody);

        PrintWriter pw = response.getWriter();
        pw.write(respBody);
        pw.close();
    }

    @RequestMapping(value = "/uploadAudio", method = RequestMethod.POST)
    public void uploadAudio(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileUploader.uploadFile(file, UploadFile.TYPE_AUDIO, null);
    }

    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    public void uploadVideo(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileUploader.uploadFile(file, UploadFile.TYPE_VIDEO, null);
    }

}
