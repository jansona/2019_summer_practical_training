package com.example.demo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

public class Recognizer {

    @Value("${store-path}")
    String store_path;

    public String recognition(MultipartFile file, String fileName) throws Exception {
        file.transferTo(new File(store_path + "photo/stranger/" +
                fileName + ".jpg"));
        String result = "";
        try {
            String[] cmd = { "docker", "exec", "fr_2", "face_recognition", "/photo/I_know", "/photo/stranger/" + fileName + ".jpg"};
            Process p = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            result = input.readLine();
            input.close();
            ir.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String results[] = result.split(",");
        Map<String, String> processedResult = new HashMap<>();

        return results[1];
    }

}
