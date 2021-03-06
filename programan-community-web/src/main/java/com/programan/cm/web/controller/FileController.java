package com.programan.cm.web.controller;

import com.programan.cm.common.Config;
import com.programan.cm.common.utils.JSONResult;
import com.sun.tools.javac.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import java.io.File;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private Config config;

    @Autowired
    public void setConfig(Config config) {
        this.config = config;
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JSONResult upload(@RequestParam("file") MultipartFile file,
                             @RequestParam("path") String path){

        logger.info("/file/upload" + file.getSize());
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片
        String filePath = config.getPath();
        try {
            //将图片保存到static文件夹里
            String savePath = "/" + path + "/" + fileName;
            file.transferTo(new File(filePath + savePath));

            return JSONResult.success("ok", "success", savePath);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.failed("error", e.getMessage(), null);
        }
    }

}
