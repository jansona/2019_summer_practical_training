//package com.example.demo.utils;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//
//public class FileDownloader {
//
//
//    public static MultipartFile downloadPic(String urlStr){
//        try{
//            URL url = new URL(urlStr);
//            URLConnection urlConnection = url.openConnection();
//            urlConnection.connect();
//            BufferedInputStream bin = new BufferedInputStream(urlConnection.getInputStream());
//        }catch (MalformedURLException mue){
//            mue.printStackTrace();
//        }catch (IOException ioe){
//            ioe.printStackTrace();
//        }
//    }
//
//}
