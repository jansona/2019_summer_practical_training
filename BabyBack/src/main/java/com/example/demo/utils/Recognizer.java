package com.example.demo.utils;

import com.example.demo.entity.LostBaby;
import com.example.demo.response.ResponseBase;
import com.example.demo.reposity.LostBabyRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;


@Service(value = "RecognizeService")
public class Recognizer {

    public static Log log = LogFactory.getLog(Recognizer.class);

    @Autowired
    private LostBabyRepository lostBabyRepository;

    FileManager fileManager = new FileManager();

    private static Recognizer recognizer;
    //我也很绝望啊，LostBabyRepository自动注入为null，只有这样解决
    @PostConstruct
    public void init() {
        recognizer = this;
        recognizer.lostBabyRepository = this.lostBabyRepository;
    }

    public ResponseBase recognition(MultipartFile file, String fileName) {
        ResponseBase responseBase;
        ArrayList<String> matches = new ArrayList<>();
        ArrayList<LostBaby> matchedBabies = new ArrayList<>();
        try {
            file.transferTo(fileManager.generateFile(FileManager.Path.TEMP, fileName));
            String[] cmd = {"docker", "exec", "fr", "face_recognition", "/photo/lost", "/photo/temp/" + fileName};
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String tempStr = input.readLine();
            while (tempStr != null) {
                String id = tempStr.split(",")[1];
                matches.add(id);
                tempStr = input.readLine();
            }
            input.close();
            ir.close();

            for(String id : matches){
                try{
                    matchedBabies.add(recognizer.lostBabyRepository.findById(Integer.valueOf(id)).get());
                }catch (Exception e){
                    e.printStackTrace();
                    log.warn("存在无效图片：" + id);
                }
            }

            responseBase = new ResponseBase(200, "待识别照片上传成功", matchedBabies);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(40005, "待识别照片上传异常", null);
        }

        return responseBase;
    }

}
