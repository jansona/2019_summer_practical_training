package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.User;
import com.example.demo.reposity.ArticleRepository;
import com.example.demo.reposity.MatchBabyRepository;
import com.example.demo.reposity.UserRepository;
import com.example.demo.service.ApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "MatchBaby")
public class MatchBabyController {
    @Autowired
    ApiService apiService;
    @Autowired
    MatchBabyRepository matchBabyRepository;

    @ApiOperation(value = "新增孤儿")
    @PutMapping("")     // TODO 填写节点
    public MatchBaby insertMatchBaby(@RequestBody MatchBaby matchBaby) {
        return matchBabyRepository.save(matchBaby);
    }

    @ApiOperation(value = "查找孤儿")
    @PostMapping("")    // TODO 填写节点
    public Page<MatchBaby> findMatchBaby(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                                 Pageable pageable,
                                         @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                         @RequestParam(value = "user_id", required = false, defaultValue = "") String user_id
    ) {
        Specification<MatchBaby> matchBabySpecification = apiService.createMatchBabySpecification(id, user_id);
        return matchBabyRepository.findAll(matchBabySpecification, pageable);
    }

    @ApiOperation(value = "删除孤儿")
    @DeleteMapping("")      // TODO 填写节点
    public void deleteMatchBaby(@RequestParam(value = "id") Integer id) {
        matchBabyRepository.deleteById(id);
    }

}
