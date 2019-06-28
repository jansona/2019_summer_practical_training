package com.example.demo.controller;

import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class LoginViewController {

    // 预先设置好的正确的用户名和密码，用于登录验证
    private String rightUserName = "";
    private String rightPassword = "";

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

        String account = (String)map.get("account");
        String password = (String)map.get("password");
//        HttpSession session = (HttpSession)map.get("session");

        ResponseBase responseBase;

        if (null == account || null == password) {
            return new ResponseBase(20001, "登陆异常", null);    // TODO 重定向地址填写
        }

        // 前端传回的密码实际为用户输入的：用户名（小写）+ 密码（小写）组合的字符串生成的md5值
        // 此处先通过后台保存的正确的用户名和密码计算出正确的md5值，然后和前端传回来的作比较

        User user;
        if(account.contains("@")){
            user = userRepository.findByEmail(account);
        }else{
            user = userRepository.findByTel(account);
        }
        if(user != null) {
            rightPassword = user.getPassWord();
        }else{
            return new ResponseBase(20001, "账号或密码错误", null);
        }

//        User user = userRepository.findByAccount(account);
//        rightUserName = user.getAccount();
//        rightPassword = user.getPassWord();
//        String md5info = rightUserName.toLowerCase() + rightPassword.toLowerCase();
//        String realPassword = DigestUtils.md5DigestAsHex(md5info.getBytes());
        String realPassword = rightPassword;

        if (!password.equals(realPassword)) {
            responseBase = new ResponseBase(20001, "账号或密码错误", null);    // TODO 重定向地址填写
        }else{
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
        }catch (Exception e){
            e.printStackTrace();
            responseBase = new ResponseBase(20002, "登出异常", null);
        }
        return responseBase;
    }

}