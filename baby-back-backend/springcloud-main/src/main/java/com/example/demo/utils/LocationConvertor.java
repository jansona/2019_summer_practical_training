package com.example.demo.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LocationConvertor {

    public static Float DISTANCE_I = 2.0F;
    public static Float DISTANCE_II = 5.0F;
    public static Float DISTANCE_III = 20.0F;

    public static Pair<Float, Float> getCoordinate(String addr) throws IOException {

        if(addr == null || addr.equals("")){
            return null;
        }

        String lng = null;//经度
        String lat = null;//纬度
        String address = null;
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        }catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String key = "f247cdb592eb43ebac6ccd27f796e2d2";
        String url = String .format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
            if (httpsConn != null) {
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data = null;
                int count = 1;
                while((data= br.readLine())!=null){
                    if(count==5){
                        lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
                        count++;
                    }else if(count==6){
                        lat = data.substring(data.indexOf(":")+1);//纬度
                        count++;
                    }else{
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        return new Pair<>(Float.valueOf(lng), Float.valueOf(lat));
    }

    public static Boolean shouldResponse(Pair<Float, Float> c0, Pair<Float, Float> c1, Float radius){
        if(c0 == null || c1 == null){
            return false;
        }
        double deltaLon = c0.getKey() - c1.getKey();
        double deltaLat = c0.getValue() - c1.getValue();
        double distance = Math.sqrt(Math.pow(deltaLon, 2.0) + Math.pow(deltaLat, 2.0));
        return distance <= radius;
    }

}
