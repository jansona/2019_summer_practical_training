package com.example.demo.utils;

import com.example.demo.entity.ResponseBase;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class FileManager<RequestMapping> {
    @Value("${server.ip}")
    private String IP;

    enum Path {
        BASE,
        LOST,
        MATCH,
        TEMP,
        PROFILE
    }

    String store_base_path = "/photo/";
    String store_lost_path = "/photo/lost/";
    String store_match_path = "/photo/match/";
    String store_temp_path = "/photo/temp/";
    String store_profile_path = "/photo/profile/";

    File files[];

    UserRepository userRepository;
    LostBabyRepository lostBabyRepository;

    public InetAddress getFirstNonLoopbackAddress(boolean preferIpv4, boolean preferIPv6) throws SocketException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
                InetAddress addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }

    public FileManager() {
        String paths[] = {store_base_path, store_lost_path, store_match_path, store_temp_path, store_profile_path};
        files = new File[paths.length];

        try {
            File basePath = new File(ResourceUtils.getURL("classpath:").getPath());
            for (int i = 0; i < files.length; i++) {
                files[i] = new File(basePath.getAbsolutePath(),"static"+paths[i]);
                System.out.println(String.format("文件种类：%d,路径：%s", i,files[i].getAbsolutePath()));
            }
            String ip = getFirstNonLoopbackAddress(true,false).getHostAddress();
            String hostName = getFirstNonLoopbackAddress(true,false).getHostName();
            System.out.println(String.format("IP:%s,hostName:%s",ip,hostName));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public ResponseBase saveLostPic(MultipartFile file, String fileName) {
        ResponseBase responseBase;
        checkAndMakeDir();

        try {
            file.transferTo(generateFile(Path.LOST, fileName));
            responseBase = new ResponseBase(200, "失踪者照片上传成功", null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            responseBase = new ResponseBase(40002, "失踪者照片上传异常", null);
        }

        return responseBase;
    }

    public ResponseBase saveMatchPic(MultipartFile file, String fileName) {
        ResponseBase responseBase;
        checkAndMakeDir();

        try {
            file.transferTo(generateFile(Path.MATCH, fileName));
            responseBase = new ResponseBase(200, "匹配值照片上传成功", null);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            responseBase = new ResponseBase(40003, "匹配者照片上传失败", null);
        }

        return responseBase;
    }

    public ResponseBase saveProfile(MultipartFile file, String fileName) {
        ResponseBase responseBase;
        checkAndMakeDir();

        try {
            file.transferTo(generateFile(Path.PROFILE, fileName));
            responseBase = new ResponseBase(200, "头像上传成功", null);
        } catch (Exception ioe) {
            ioe.printStackTrace();
            responseBase = new ResponseBase(40004, "头像上传异常", null);
        }

        return responseBase;
    }

    public File generateFile(Path basePath, String fileName) {

        String pathStr = files[basePath.ordinal()].getAbsolutePath() + "/" + fileName;
        File file = new File(pathStr);
        return file;
    }

    public void checkAndMakeDir() {

        for (File file : files) {
            try {
                if (!file.exists()) {
                    file.mkdir();
                }
            } catch (Exception ioe) {
                ioe.printStackTrace();
            }
        }
    }

}