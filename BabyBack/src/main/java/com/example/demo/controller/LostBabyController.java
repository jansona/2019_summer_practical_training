package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.reposity.KeyWordRepository;
import com.example.demo.reposity.LostBabyRepository;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "lost-baby")
public class LostBabyController {

    @Autowired
    ApiService apiService;
    @Autowired
    LostBabyRepository lostBabyRepository;
    @Autowired
    KeyWordRepository keyWordRepository;

    PageHelper pageHelper = new PageHelper();

    @CrossOrigin
    @ApiOperation(value = "新增一个丢失儿童信息")
    @PostMapping("/insert")
    public ResponseBase insertLostBaby(@RequestBody LostBaby lostBaby) {
        lostBabyRepository.save(lostBaby);
        lostBaby = lostBabyRepository.findById(lostBaby.getId()).get();

        for (String str : apiService.initKeyWord(apiService.initDescription(lostBaby))) {
            if (keyWordRepository.existsByName(str)) {
                KeyWord existK = keyWordRepository.findByName(str);
                existK.getLostBabies().add(lostBaby);
                keyWordRepository.save(existK);
            } else {
                KeyWord kw = new KeyWord(str);
                kw.getLostBabies().add(lostBaby);
                keyWordRepository.save(kw);
            }
        }

        return new ResponseBase(200, "插入成功", lostBaby);
    }

    @CrossOrigin
    @ApiOperation(value = "查找功能")
    @PostMapping("/find")
    public ResponseBase findLostBaby(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                             Pageable pageable,
                                     @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                     @RequestParam(value = "place", required = false, defaultValue = "") String place,
                                     @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "height", required = false, defaultValue = "") String height,
                                     @RequestParam(value = "nativePlace", required = false, defaultValue = "") String nativePlace,
                                     @RequestParam(value = "date", required = false, defaultValue = "") String date
    ) {
        Specification<LostBaby> lostBabySpecification = apiService.createLostBabySpecification(id, place, name, height, nativePlace, date);
        return new ResponseBase(200, "查找成功", lostBabyRepository.findAll(lostBabySpecification, pageable));
    }

    @ApiOperation(value = "删除某信息")
    @DeleteMapping("/delete")
    public ResponseBase deleteLostBaby(@RequestParam(value = "id") Integer id) {
        lostBabyRepository.deleteById(id);
        return new ResponseBase().succes("删除成功");
    }

    @ApiOperation(value = "查找特定用户上传的失踪者")
    @PostMapping("/find-by-user")   // TODO 待填
    public Page<LostBaby> findLostBabyByUser(Pageable page, @RequestParam(value = "user") User user){
        List<LostBaby> result = lostBabyRepository.findAllByUser(user);

        pageHelper.doPage(result, page);

        Page<LostBaby> pageResult = new PageImpl(result, page, result.size());
        return pageResult;
    }

    @ApiOperation(value = "根据关键字查找用户")
    @GetMapping("/{key}")
    public ResponseBase findLostBabyByKey(Pageable page, @PathVariable String key) {
        Set<LostBaby> set = new HashSet<>();
        for (String str : apiService.initKeyWord(key)) {
            set.addAll(keyWordRepository.findByName(str).getLostBabies());
        }

        List result = new ArrayList();
        result.addAll(set);

        pageHelper.doPage(result, page);

        Page<LostBaby> pageResult = new PageImpl(result, page, set.size());
        return new ResponseBase(200, "查询成功", page);
    }

}
