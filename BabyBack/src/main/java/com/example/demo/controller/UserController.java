package com.example.demo.controller;

import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.UserRepository;
import com.example.demo.service.ApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    ApiService apiService;
    @Autowired
    UserRepository userRepository;


    @ApiOperation(value = "新增一个用户")
    @PostMapping("/insert")
    public ResponseBase insertUser(@RequestBody User user) {
        ResponseBase responseBase;
        if (userRepository.findByTel(user.getTel()) != null) {
            responseBase = new ResponseBase(13240, "该手机号已被使用", user);
        } else {
            responseBase = new ResponseBase(200, "注册成功", userRepository.save(user));
        }
        return responseBase;
    }

    @ApiOperation(value = "查找功能")
    @PostMapping("/find")
    public ResponseBase findUser(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                       Pageable pageable,
                               @RequestParam(value = "id", required = false, defaultValue = "") String id,
                               @RequestParam(value = "name", required = false, defaultValue = "") String name,
                               @RequestParam(value = "tel", required = false, defaultValue = "") String tel,
                               @RequestParam(value = "email", required = false, defaultValue = "") String email) {
        ResponseBase responseBase;
        try {
            Specification<User> userSpecification = apiService.createUserSpecification(id, name, tel, email);
            responseBase = new ResponseBase(200, "用户信息获取成功", userRepository.findAll(userSpecification, pageable));
        }catch (Exception e){
            responseBase = new ResponseBase(60001, "获取用户信息异常", null);
        }
        return responseBase;
    }

    @ApiOperation(value = "查找单个特定用户")
    @PostMapping("/find-by-id")
    public ResponseBase findById(@RequestParam(value = "id") Integer id) {
        return new ResponseBase(200, "获取用户信息成功", userRepository.findById(id));
    }

    @ApiOperation(value = "删除一个用户")
    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam(value = "id") Integer id) {
        userRepository.deleteById(id);
    }

}
