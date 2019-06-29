package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加
    @GetMapping(value = "/redisAdd")
    public void saveRedis(@RequestParam String key, @RequestParam String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    //获取
    @GetMapping(value = "/redisGet")
    public String getRedis(@RequestParam String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @GetMapping(value = "/redisDelete")
    public void deleteRedis(@RequestParam String key){
        stringRedisTemplate.delete(key);
    }
}
