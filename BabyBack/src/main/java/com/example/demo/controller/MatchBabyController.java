package com.example.demo.controller;

import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.MatchBabyRepository;
import com.example.demo.service.ApiService;
import com.example.demo.utils.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "match-baby")
public class MatchBabyController {
    @Autowired
    ApiService apiService;
    @Autowired
    MatchBabyRepository matchBabyRepository;

    PageHelper pageHelper = new PageHelper();

    @ApiOperation(value = "新增孤儿")
    @PutMapping("/insert")     // TODO 填写节点
    public MatchBaby insertMatchBaby(@RequestBody MatchBaby matchBaby) {
        return matchBabyRepository.save(matchBaby);
    }

    @ApiOperation(value = "查找孤儿")
    @PostMapping("/find")    // TODO 填写节点
    public ResponseBase findMatchBaby(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                                 Pageable pageable,
                                      @RequestParam(value = "id", required = false, defaultValue = "") String id) {
        Specification<MatchBaby> matchBabySpecification = apiService.createMatchBabySpecification(id);
        return new ResponseBase(200,"查询成功", matchBabyRepository.findAll(matchBabySpecification, pageable));
    }

    @ApiOperation(value = "查找特定用户上传的孤儿")
    @PostMapping("/find-by-user")
    public ResponseBase findMatchBabyByUser(Pageable page, @RequestParam(value = "user") User user) {
        List<MatchBaby> result = matchBabyRepository.findAllByUser(user);

        result = (List<MatchBaby>) pageHelper.doPage(result, page);

        Page<MatchBaby> pageResult = new PageImpl(result, page, result.size());
        return new ResponseBase(200, "查询成功", pageResult);
    }

    @ApiOperation(value = "删除孤儿")
    @DeleteMapping("/delete")      // TODO 填写节点
    public ResponseBase deleteMatchBaby(@RequestParam(value = "id") Integer id) {
        matchBabyRepository.deleteById(id);
        return new ResponseBase(200, "删除成功", null);
    }

}
