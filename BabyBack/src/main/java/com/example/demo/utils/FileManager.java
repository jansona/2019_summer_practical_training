package com.example.demo.utils;

import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileManager<RequestMapping> {

    enum Path{
        BASE,
        LOST,
        MATCH,
        TEMP,
        PROFILE
    }

    String store_base_path = "./photo/";
    String store_lost_path = "./photo/lost/";
    String store_match_path = "./photo/match/";
    String store_temp_path = "./photo/temp/";
    String store_profile_path = "./photo/profile/";

    File files[];

    UserRepository userRepository;
    LostBabyRepository lostBabyRepository;


    public FileManager(){
        String paths[] = {store_base_path, store_lost_path, store_match_path, store_temp_path, store_profile_path};
        files = new File[paths.length];

        for(int i = 0; i < files.length; i++){
            files[i] = new File(paths[i]);
        }
    }

    public String saveLostPic(MultipartFile file, String fileName) {

        checkAndMakeDir();

        String result = "succeed";
        try {
            file.transferTo(generateFile(Path.LOST, fileName));
        } catch (IOException ioe) {
            result = "failed";
            ioe.printStackTrace();
        }

        return result;
    }

    public String saveMatchPic(MultipartFile file, String fileName){

        checkAndMakeDir();

        String result = "succeed";
        try {
            file.transferTo(generateFile(Path.MATCH, fileName));
        } catch (IOException ioe) {
            result = "failed";
            ioe.printStackTrace();
        }

        return result;
    }

    public String saveProfile(MultipartFile file, String fileName) {

        checkAndMakeDir();

        String result = "succeed";
        try {
            file.transferTo(generateFile(Path.PROFILE, fileName));
        } catch (Exception ioe) {
            result = "failed";
        }

        return result;
    }

    public File generateFile(Path basePath, String fileName){

        String pathStr = files[basePath.ordinal()].getAbsolutePath() + "/" + fileName;
        File file = new File(pathStr);
        return file;
    }

    public void checkAndMakeDir(){

        for(File file : files){
            try {
                if (!file.exists()) {
                    file.mkdir();
                }
            }catch(Exception ioe){
                ioe.printStackTrace();
            }
        }
    }

}