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
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginViewController {

    // 预先设置好的正确的用户名和密码，用于登录验证
    private String rightUserName = "";
    private String rightPassword = "";

    final String resetMarkPrefix = "RESET:";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserRepository userRepository;

    /**
     * 登录校验
     *
     * @param map
     * @return
     */
    @CrossOrigin
    @PostMapping("/login")
    public ResponseBase login(@RequestBody Map<String, Object> map) {

        String account = (String) map.get("account");
        String password = (String) map.get("password");
//        HttpSession session = (HttpSession)map.get("session");

        ResponseBase responseBase;

        if (null == account || null == password) {
            return new ResponseBase(20001, "登陆异常", null);    // TODO 重定向地址填写
        }

        // 前端传回的密码实际为用户输入的：用户名（小写）+ 密码（小写）组合的字符串生成的md5值
        // 此处先通过后台保存的正确的用户名和密码计算出正确的md5值，然后和前端传回来的作比较

        User user;
        if (account.contains("@")) {
            user = userRepository.findByEmail(account);
        } else {
            user = userRepository.findByTel(account);
        }
        if (user != null) {
            rightPassword = user.getPassword();
        } else {
            return new ResponseBase(20001, "账号或密码错误", null);
        }

        String realPassword = rightPassword;

        if (!password.equals(realPassword)) {
            responseBase = new ResponseBase(20001, "账号或密码错误", null);    // TODO 重定向地址填写
        } else {
            responseBase = new ResponseBase(200, "登陆成功", user);
        }

        // 校验通过时，在session里放入一个标识
        // 后续通过session里是否存在该标识来判断用户是否登录
//        session.setAttribute("account", account);
        return responseBase; // TODO 重定向地址填写
    }

    /**
     * 注销登录
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    public ResponseBase logout(@RequestBody HttpSession session) {
        ResponseBase responseBase;
        try {
            session.invalidate();
            responseBase = new ResponseBase(200, "登出成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(20002, "登出异常", null);
        }
        return responseBase;
    }


    @ApiOperation(value = "发送验证码")
    @GetMapping("/sms-verify-reset-pwd")
    public ResponseBase sendRegisterSMS(@RequestParam String tel) {
        ResponseBase responseBase;

        if(userRepository.findByTel(tel) == null){
            return new ResponseBase(60001, "该电话未注册", null);
        }

        if(stringRedisTemplate.hasKey(resetMarkPrefix + tel)){
            return new ResponseBase(60002, "短信验证过于频繁", null);
        }else{
            stringRedisTemplate.opsForValue().set(resetMarkPrefix + tel, tel, 1L, TimeUnit.MINUTES);
        }

        try {
            String code = SMSSender.sendSMS(tel);
            stringRedisTemplate.delete(tel);
            stringRedisTemplate.opsForValue().set(tel, code, 10L, TimeUnit.MINUTES);
            responseBase = new ResponseBase(200, "验证码短信发送成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(60003, "验证码短信发送异常", null);
        }
        return responseBase;
    }

    @ApiOperation(value = "验证并重置密码")
    @PostMapping(value = "/reset-pwd")
    public ResponseBase resetPwd(@RequestParam String tel, @RequestBody String pwd, @RequestParam String code){
        ResponseBase responseBase;

        User newUser = userRepository.findByTel(tel);

        try {
            String realCode = stringRedisTemplate.opsForValue().get(tel);

            if (realCode != null && realCode.equals(code)) {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                newUser.setPassword(bCryptPasswordEncoder.encode(pwd));
                userRepository.save(newUser);
                stringRedisTemplate.delete(newUser.getTel());
                responseBase = new ResponseBase(200, "重置密码成功", null);
            } else {
                responseBase = new ResponseBase(50003, "重置密码失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBase(50002, "短信验证码验证异常", null);
        }
        return responseBase;
    }

}