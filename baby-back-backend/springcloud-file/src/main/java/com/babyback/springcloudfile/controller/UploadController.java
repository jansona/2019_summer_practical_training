package com.babyback.springcloudfile.controller;


import com.babyback.springcloudfile.entity.ResponseBase;
import com.babyback.springcloudfile.utils.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/file")
public class UploadController {


    @Value("${server.port}")
    private String port;

    @Value("${eureka.instance.ip-address}")
    private String IP;

    private FileManager fileManager = new FileManager();

    enum Action {
        AS_PROFILE,
        AS_LOST_PICS,
        AS_MATCH_PICS,
        RECOGNITION
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public ResponseBase upload(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id") String id, String action) throws FileNotFoundException {
        ResponseBase responseBase;
        String postfix = "";
        ArrayList<String> matches = null;

        String fileName;
        try {
            file.getOriginalFilename();
            file.getName();
            postfix = file.getOriginalFilename().split("\\.")[1];
            fileName = String.format("%s.%s", id, postfix);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase(40001, "上传文件名异常", null);
        }

        switch (action) {
            case "AS_LOST_PICS":
                responseBase = fileManager.saveLostPic(file, fileName);
                break;
            case "AS_MATCH_PICS":
                responseBase = fileManager.saveMatchPic(file, fileName);
                break;
            case "AS_PROFILE":
                responseBase = fileManager.saveProfile(file, fileName);
                break;
            default:
                responseBase = new ResponseBase(40006, "未知图片上传行为", null);
        }
        responseBase.setData((Object)"http://"+IP + ":"+ port+"/resource/photo/");

        return responseBase;
    }
}