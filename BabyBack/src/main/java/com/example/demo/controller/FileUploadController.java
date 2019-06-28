package com.example.demo.controller;

import com.example.demo.entity.ResponseBase;
import com.example.demo.utils.FileManager;
import com.example.demo.utils.Recognizer;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping(value = "file")
public class FileUploadController {

    enum Action {
        AS_PROFILE,
        AS_PICS,
        RECOGNITION
    }

    private Recognizer recognizer = new Recognizer();
    private FileManager fileManager = new FileManager();

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public ResponseBase uploadPic(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id")String id, Action action) {
        String result = "";
        ArrayList<String> matches = null;

        String fileName;
        try{
            file.getOriginalFilename();
            file.getName();
            String postfix = file.getOriginalFilename().split("\\.")[1];
            fileName = String.format("%s.%s", id, postfix);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseBase(40001, "上传文件名异常", null);
        }

        switch (action) {
            case AS_PICS:
                result = fileManager.saveLostPic(file, fileName);
                break;
            case AS_PROFILE:
                result = fileManager.saveProfile(file, fileName);
                break;
            case RECOGNITION:
                matches = recognizer.recognition(file, generateRandomFilename());

        }

        return new ResponseBase(200, result, matches);
    }


    public String generateRandomFilename() {
        String RandomFilename;
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +
                String.valueOf(intDay) + "_";

        RandomFilename = now + String.valueOf(random > 0 ? random : (-1) * random);

        return RandomFilename;
    }

}
