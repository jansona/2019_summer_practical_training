package com.example.demo.controller;

import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.UserRepository;
import com.example.demo.utils.LocationConvertor;
import com.example.demo.utils.SMSSender;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
public class RegisterController {

    final String sendMarkPrefix = "SEND:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "发送验证码")
    @GetMapping("/sms-verify")
    public ResponseBase sendRegisterSMS(@RequestParam String tel) {
        ResponseBase responseBase;

        if(userRepository.findByTel(tel) != null){
            return new ResponseBase(50004, "短信验证电话已被占用", null);
        }

        if(stringRedisTemplate.hasKey(sendMarkPrefix + tel)){
            return new ResponseBase(50005, "短信验证过于频繁", null);
        }else{
            stringRedisTemplate.opsForValue().set(sendMarkPrefix + tel, tel, 1L, TimeUnit.MINUTES);
        }

        try {
            String code = SMSSender.sendSMS(tel);
            stringRedisTemplate.delete(tel);
            stringRedisTemplate.opsForValue().set(tel, code, 10L, TimeUnit.MINUTES);
            responseBase = new ResponseBase(200, "验证码短信发送成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(50001, "验证码短信发送异常", null);
        }
        return responseBase;
    }

    @ApiOperation(value = "验证并注册一个用户")
    @PostMapping("/register")
    public ResponseBase registerUser(@RequestBody User user, @RequestParam String code) {
        ResponseBase responseBase;
        try {
            String realCode = stringRedisTemplate.opsForValue().get(user.getTel());

            if (realCode != null && realCode.equals(code)) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                if(user.getLocation() != null && !user.getLocation().equals("")) {
                    try {
                        user.setCoordinate(LocationConvertor.getCoordinate(user.getLocation()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
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
