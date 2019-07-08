package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //添加
    @GetMapping(value = "/redisAdd")
    public void saveRedis(@RequestParam String key, @RequestParam String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @PostMapping(value = "/redisAddWithDuration")
    public void addRedisWithDuration(@RequestParam String key, @RequestParam String value, @RequestParam Long dura){
        stringRedisTemplate.opsForValue().set(key, value, dura, TimeUnit.SECONDS);
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

    @PostMapping(value = "/websocket/send")
    public void sendMessageToUser(@RequestParam Integer user_id, @RequestParam String message){
        CustomWebSocket.sendMessageToUser(user_id, message);
    }

    @GetMapping(value = "/websocket/send-all")
    public void sendAll(){
        try {
            CustomWebSocket.sendInfo("test");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
