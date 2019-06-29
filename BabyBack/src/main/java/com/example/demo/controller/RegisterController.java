package com.example.demo.controller;

import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.UserRepository;
import com.example.demo.utils.SMSSender;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
public class RegisterController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "发送验证码")
    @PostMapping("/sms-verify")
    public ResponseBase logout(@RequestBody String tel) {
        ResponseBase responseBase;

        if(userRepository.findByTel(tel) != null){
            return new ResponseBase(50004, "短信验证电话已被占用", null);
        }

        try {
            String code = SMSSender.sendSMS(tel);
            stringRedisTemplate.delete(tel);
            stringRedisTemplate.opsForValue().set(tel, code);
            responseBase = new ResponseBase(200, "验证码短信发送成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(50001, "验证码短信发送异常", null);
        }
        return responseBase;
    }

    @ApiOperation(value = "验证并注册一个用户")
    @PostMapping("/register")
    public ResponseBase insertUser(@RequestBody User user, @RequestParam String code) {
        ResponseBase responseBase;
        try {
            String realCode = stringRedisTemplate.opsForValue().get(user.getTel());
//            char[] chrCharArray; //创建一个字符数组chrCharArray
//            chrCharArray = realCode.toCharArray(); //将字符串变量转换为字符数组
//            realCode = String.valueOf(chrCharArray ); //将字符数组转换为字符串

            System.out.println("AAAAAAAAAAAAAAAA: " + realCode + " : " + code);
//            int a = Integer.valueOf(realCode);
            int b = Integer.valueOf(code);

            if (realCode != null && realCode.equals(code)) {
                User user_saved = userRepository.save(user);
                stringRedisTemplate.delete(user.getTel());
                responseBase = new ResponseBase(200, "短信验证成功", user_saved);
            } else {
                responseBase = new ResponseBase(50003, "短信验证失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase(50002, "短信验证码验证异常", null);
        }
        return responseBase;
    }

}
