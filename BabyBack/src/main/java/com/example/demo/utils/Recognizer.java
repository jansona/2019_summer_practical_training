package com.example.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;

public class Recognizer {

    FileManager fileManager = new FileManager();

    public String recognition(MultipartFile file, String fileName) {
        String result = "failed";
        try {
            file.transferTo(fileManager.generateFile(FileManager.Path.MATCH, fileName));
            String[] cmd = {"docker", "exec", "fr_3", "face_recognition", "/photo/missing", "/photo/match/" + fileName + ".jpg"};
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
