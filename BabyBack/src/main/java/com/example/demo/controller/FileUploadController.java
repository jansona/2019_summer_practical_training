package com.example.demo.controller;

import com.example.demo.entity.ResponseBase;
import com.example.demo.utils.FileManager;
import com.example.demo.utils.Recognizer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Random;

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
    public ResponseBase uploadPic(@RequestParam(name = "file") MultipartFile file, Action action) {
        String result = "";

        switch (action) {
            case AS_PICS:
                result = fileManager.savePic(file, file.getOriginalFilename());
                break;
            case AS_PROFILE:
                result = fileManager.saveProfile(file, file.getOriginalFilename());
                break;
            case RECOGNITION:
                result = recognizer.recognition(file, generateRandomFilename());

        }

        return new ResponseBase().succes(result);
    }


    public String generateRandomFilename() {
        String RandomFilename = "";
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
