package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.reposity.LostBabyRepository;
import com.example.demo.reposity.UserRepository;
import com.example.demo.service.ApiService;
import com.example.demo.utils.LocationConvertor;
import com.example.demo.utils.PageHelper;
import com.example.demo.utils.Recognizer;
import com.example.demo.utils.UserInformer;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@CrossOrigin
@RestController
@RequestMapping(value = "lost-baby")
public class LostBabyController {

    @Autowired
    ApiService apiService;
    @Autowired
    LostBabyRepository lostBabyRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    PageHelper pageHelper = new PageHelper();
    Recognizer recognizer = new Recognizer();
    UserInformer userInformer = new UserInformer();

    @CrossOrigin
    @ApiOperation(value = "新增一个丢失儿童信息")
    @PostMapping("/insert")
    public ResponseBase insertLostBaby(@RequestBody LostBaby lostBaby, @RequestParam(defaultValue = "false")boolean urgent) {
        ResponseBase responseBase;

        if(lostBaby.getPlace() != null && !lostBaby.getPlace().equals("")){
            try{
                lostBaby.setCoordinate(LocationConvertor.getCoordinate(lostBaby.getPlace()));
            } catch (Exception e){
                e.printStackTrace();
                return new ResponseBase(60002, "无效地址", null);
            }
        }
        LostBaby lostBabySaved = lostBabyRepository.save(lostBaby);

        matchOnInsertInfo(lostBabySaved);

        if(urgent){
            String compositedKey = String.format("%d-%d", lostBabySaved.getId(), 2);
            stringRedisTemplate.opsForValue().set(compositedKey, "", 2, TimeUnit.SECONDS);
        }

        return new ResponseBase(200, "插入成功", lostBabySaved);
    }

    @ApiOperation(value = "查找功能")
    @PostMapping("/find")
    public ResponseBase findLostBaby(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                             Pageable pageable,
                                     @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                     @RequestParam(value = "place", required = false, defaultValue = "") String place,
                                     @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "height", required = false, defaultValue = "") String height,
                                     @RequestParam(value = "nativePlace", required = false, defaultValue = "") String nativePlace,
                                     @RequestParam(value = "date", required = false, defaultValue = "") String date) {
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
    public ResponseBase findLostBabyByUser(Pageable page, @RequestParam(value = "user") User user) {

        List<LostBaby> result = lostBabyRepository.findAllByUser(user);

        int totalNum = result.size();
        result = (List<LostBaby>) pageHelper.doPage(result, page);

        Page<LostBaby> pageResult = new PageImpl(result, page, totalNum);
        return new ResponseBase(200, "查询成功", pageResult);
    }

    void matchOnInsertInfo(LostBaby lostBaby){

        String info = lostBaby.toString();
        ResponseBase responseBase = recognizer.analyze(info, Recognizer.MatchTarget.MATCH_BABY);
        ArrayList<MatchBaby> matchBabies = (ArrayList<MatchBaby>)responseBase.getData();
        ArrayList<String> matchIDs = new ArrayList<>();
        if(matchBabies == null){
            return;
        }
        String matchResult = "";
        for(MatchBaby matchBaby : matchBabies){
//            matchIDs.add(matchBaby.getId().toString());
            matchResult += matchBaby.getId().toString() + ",";
        }
//        String matchResult = String.join(",", (Iterable<? extends CharSequence>) matchIDs.iterator());

        ArrayList<Integer> userIDs = new ArrayList<>();
        userIDs.add(lostBaby.getUser().getId());

        userInformer.infoUser(userIDs, PendingMessage.MessageType.MATCH_NOTIFICATION, matchResult);
    }

//    @ApiOperation(value = "根据关键字查找用户")
//    @GetMapping("/find-by-key/{key}")
//    public ResponseBase findLostBabyByKey(Pageable page, @PathVariable String key) {
//        Set<LostBaby> set = new HashSet<>();
//        for (String str : apiService.initKeyWord(key)) {
//            set.addAll(keyWordRepository.findByName(str).getLostBabies());
//        }
//
//        List result = new ArrayList();
//        result.addAll(set);
//
//        int totalNum = result.size();
//        result = pageHelper.doPage(result, page);
//
//        Page<LostBaby> pageResult = new PageImpl(result, page, totalNum);
//        return new ResponseBase(200, "查询成功", pageResult);
//    }

}
