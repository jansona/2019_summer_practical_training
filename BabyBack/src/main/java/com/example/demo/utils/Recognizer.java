package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

public class Recognizer {

    //    @Value("${ybg.store-path}")
    String store_path = "d://jupyter_workplace/2019_summer_practical_training/face_recognition/";

    public String getStore_path() {
        return store_path;
    }

    public void setStore_path(String store_path) {
        this.store_path = store_path;
    }

    public String recognition(MultipartFile file, String fileName) {
        String result = "failed";
        try {
            file.transferTo(new File(store_path + "photo/stranger/" +
                    fileName + ".jpg"));
            String[] cmd = {"docker", "exec", "fr_2", "face_recognition", "/photo/I_know", "/photo/stranger/" + fileName + ".jpg"};
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();
            input.close();
            ir.close();

            // TODO 结构不明，暂时这么安排
            String results[] = result.split(",");
            Map<String, String> processedResult = new HashMap<>();
            result = results[1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
