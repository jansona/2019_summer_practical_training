package com.example.demo.controller;

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

@RestController
@RequestMapping(value = "User")
public class UserController {
    @Autowired
    ApiService apiService;
    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "新增一个用户")
    @PutMapping("")
    public User insertUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @ApiOperation(value = "查找功能")
    @PostMapping("")
    public Page<User> findUser(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                       Pageable pageable,
                               @RequestParam(value = "id", required = false, defaultValue = "") String id,
                               @RequestParam(value = "account", required = false, defaultValue = "") String account,
                               @RequestParam(value = "name", required = false, defaultValue = "") String name,
                               @RequestParam(value = "tel", required = false, defaultValue = "") String tel,
                               @RequestParam(value = "email", required = false, defaultValue = "") String email
    ) {
        Specification<User> userSpecification = apiService.createUserSpecification(id, account, name, tel, email);
        return userRepository.findAll(userSpecification, pageable);
    }

    @ApiOperation(value = "删除一个用户")
    @DeleteMapping("")
    public void deleteUser(@RequestParam(value = "id") Integer id) {
        userRepository.deleteById(id);
    }

}
