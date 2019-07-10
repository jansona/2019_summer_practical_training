package com.example.demo.utils;

import com.example.demo.entity.LostBaby;
import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.MatchBabyRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Optional;


@Service(value = "RecognizeService")
public class Recognizer {

    public enum MatchTarget{
        LOST_BABY,
        MATCH_BABY
    }

    public static Log log = LogFactory.getLog(Recognizer.class);

    @Autowired
    private LostBabyRepository lostBabyRepository;
    @Autowired
    private MatchBabyRepository matchBabyRepository;

    FileManager fileManager = new FileManager();

    private static Recognizer recognizer;
    //我也很绝望啊，LostBabyRepository自动注入为null，只有这样解决
    @PostConstruct
    public void init() {
        recognizer = this;
        recognizer.lostBabyRepository = this.lostBabyRepository;
        recognizer.matchBabyRepository = this.matchBabyRepository;
    }

    public ResponseBase recognition(MultipartFile file, String fileName, MatchTarget matchTarget) {

        String targetPath = "";
        switch (matchTarget){
            case LOST_BABY:
                targetPath = "./photo/lost";
                break;
            case MATCH_BABY:
                targetPath = "./photo/match";
                break;
        }

        ResponseBase responseBase;
        ArrayList<String> matches = new ArrayList<>();
        try {
            file.transferTo(fileManager.generateFile(FileManager.Path.TEMP, fileName));
            String[] cmd = {"docker", "exec", "fr", "face_recognition", "/photo/lost", "/photo/temp/" + fileName};
//            String[] cmd = {"face_recognition", targetPath, "./photo/temp/" + fileName};
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String tempStr = input.readLine();

            while (tempStr != null) {
                if(tempStr.contains("WARNING")){
                    tempStr = input.readLine();
                    continue;
                }
                String id = tempStr.split(",")[1];
                matches.add(id);
                tempStr = input.readLine();
            }
            input.close();
            ir.close();

            ArrayList<?> matchedBabies;
            switch (matchTarget){
                case LOST_BABY:
                    matchedBabies = babyId2LostBaby(matches);
                    break;
                default:
                    matchedBabies = babyId2MatchBaby(matches);
                    break;
            }

            responseBase = new ResponseBase(200, "待识别照片上传成功", matchedBabies);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(40005, "待识别照片上传异常", null);
        }

        return responseBase;
    }

    public ResponseBase analyze(String txt, MatchTarget matchTarget){

        String scriptName = "";
        switch (matchTarget){
            case LOST_BABY:
                scriptName = "./ldf.py";
                break;
            case MATCH_BABY:
                scriptName = "./ldf2.py";
                break;
        }

        ResponseBase responseBase;
        ArrayList<String> matches = new ArrayList<>();
        ArrayList<LostBaby> matchedBaby;

//        String[] cmd = {"python3", scriptName, txt};
        String[] cmd = {"py", scriptName, txt};
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String tempStr = input.readLine();
            if(tempStr == null){
                responseBase = new ResponseBase(50006, "自然语言分析异常", null);
            }else{
                for(int i=0; i<4; i++) {
                    tempStr = input.readLine();
                }
                while(tempStr != null){
                    String id = tempStr;
                    matches.add(id);
                    tempStr = input.readLine();
                }
                input.close();
                ir.close();

                ArrayList<?> matchedBabies;
                switch (matchTarget){
                    case LOST_BABY:
                        matchedBabies = babyId2LostBaby(matches);
                        break;
                    default:
                        matchedBabies = babyId2MatchBaby(matches);
                        break;
                }

                responseBase = new ResponseBase(200, "自然语言分析成功", matchedBabies);
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
            responseBase = new ResponseBase(50006, "自然语言分析异常", null);
        }

        return responseBase;
    }


    private ArrayList<LostBaby> babyId2LostBaby(ArrayList<String> ids){
        ArrayList<LostBaby> matchedBabies = new ArrayList<>();
        for(String id : ids){
            try{
                LostBaby lostBaby = recognizer.lostBabyRepository.findById(Integer.valueOf(id)).get();
                lostBaby.getUser().setPassword("");
                matchedBabies.add(lostBaby);
            }catch (Exception e){
                e.printStackTrace();
                log.warn("存在无效图片：" + id);
            }
        }
        return matchedBabies;
    }

    private ArrayList<MatchBaby> babyId2MatchBaby(ArrayList<String> ids){
        ArrayList<MatchBaby> matchedBabies = new ArrayList<>();
        for(String id : ids){
            try{
                MatchBaby matchBaby = recognizer.matchBabyRepository.findById(Integer.valueOf(id)).get();
                matchBaby.getUser().setPassword("");
                matchedBabies.add(matchBaby);
            }catch (Exception e){
                e.printStackTrace();
                log.warn("存在无效图片：" + id);
            }
        }
        return matchedBabies;
    }

}
