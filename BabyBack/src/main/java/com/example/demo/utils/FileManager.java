package com.example.demo.utils;

import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileManager<RequestMapping> {

//    @Value("${store-path}")
    String store_path = "d://jupyter_workplace/2019_summer_practical_training/face_recognition/";

    UserRepository userRepository;
    LostBabyRepository lostBabyRepository;

    public String savePic(MultipartFile file, String fileName){
        String result = "succeed";
        try {
            file.transferTo(new File(store_path + "photo/I_know/" + fileName + ".jpg"));
        }catch (IOException ioe){
            result = "failed";
        }

        return result;
    }

    public String saveProfile(MultipartFile file, String fileName){
        String result = "succeed";
        try{
            file.transferTo(new File(store_path + "photo/profile/" + fileName + ".jpg"));
        }catch (IOException ioe){
            result = "failed";
        }

        return result;
    }

}