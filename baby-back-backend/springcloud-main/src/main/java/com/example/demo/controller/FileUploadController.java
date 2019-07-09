package com.example.demo.controller;

import com.example.demo.entity.LostBaby;
import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.MatchBabyRepository;
import com.example.demo.reposity.UserRepository;
import com.example.demo.utils.FileManager;
import com.example.demo.utils.Recognizer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping(value = "file")
public class FileUploadController {

    String photoBaseUrl = "/resource/photo/";

    @Value("${server.port}")
    private String port;

    @Value("${eureka.instance.ip-address}")
    private String IP;

    enum Action {
        AS_PROFILE,
        AS_LOST_PICS,
        AS_MATCH_PICS,
        RECOGNITION
    }

    private Recognizer recognizer = new Recognizer();
    private FileManager fileManager = new FileManager();

    @Autowired
    UserRepository userRepository;
    @Autowired
    LostBabyRepository lostBabyRepository;
    @Autowired
    MatchBabyRepository matchBabyRepository;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public ResponseBase uploadPic(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id") String id, Action action) {
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
            case AS_LOST_PICS:
                responseBase = fileManager.saveLostPic(file, fileName);
                break;
            case AS_MATCH_PICS:
                responseBase = fileManager.saveMatchPic(file, fileName);
                break;
            case AS_PROFILE:
                responseBase = fileManager.saveProfile(file, fileName);
                break;
            case RECOGNITION:
                responseBase = recognizer.recognition(file, String.format("%s.%s", generateRandomFilename(), postfix));
                break;
            default:
                responseBase = new ResponseBase(40006, "未知图片上传行为", null);
        }

        updateEntity(id, action, postfix);

        return responseBase;
    }

    @ApiOperation("自然语言分析接口")
    @PostMapping("/analyze-txt")
    public ResponseBase getTextAndRecog(@RequestBody String txt){
        return recognizer.analyze(txt);
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

    private void updateEntity(String id, Action action, String suffix){

        int nId = Integer.valueOf(id);
        photoBaseUrl = "http://"+IP + ":"+ port+"/resource/photo/";

        switch (action){
            case AS_PROFILE:
                User user = userRepository.findById(nId).get();
                user.setProfileUrl(String.format("%s%s%s.%s", photoBaseUrl, "profile/", id, suffix));
                userRepository.save(user);
                break;
            case AS_LOST_PICS:
                LostBaby lostBaby = lostBabyRepository.findById(nId).get();
                lostBaby.setPicUrl(String.format("%s%s%s.%s", photoBaseUrl, "lost/", id, suffix));
                lostBabyRepository.save(lostBaby);
                break;
            case AS_MATCH_PICS:
                MatchBaby matchBaby = matchBabyRepository.findById(nId).get();
                matchBaby.setPicUrl(String.format("%s%s%s.%s", photoBaseUrl, "match/", id, suffix));
                matchBabyRepository.save(matchBaby);
                break;
        }
    }

}
