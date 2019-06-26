package com.example.demo.utils;

import com.example.demo.entity.LostBaby;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

public class FileManager<RequestMapping> {

    @Value("${store-path}")
    String store_path;

    UserRepository userRepository;
    LostBabyRepository lostBabyRepository;

}