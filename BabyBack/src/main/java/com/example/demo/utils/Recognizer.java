package com.example.demo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recognizer {

    FileManager fileManager = new FileManager();

    public ArrayList<String> recognition(MultipartFile file, String fileName) {
        ArrayList<String> matches = new ArrayList<>();
        try {
            file.transferTo(fileManager.generateFile(FileManager.Path.TEMP, fileName));
            String[] cmd = {"docker", "exec", "fr_3", "face_recognition", "/photo/lost", "/photo/temp/" + fileName + ".jpg"};
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

            // TODO 结构不明，暂时这么安排
//            String results[] = result.split(",");
//            Map<String, String> processedResult = new HashMap<>();
//            result = results[1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return matches;
    }

}
