package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.ArticleRepository;
import com.example.demo.reposity.UserRepository;
import com.example.demo.service.ApiService;
import com.example.demo.utils.PageHelper;
import io.swagger.annotations.Api;
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
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "article")
public class ArticleController {
    @Autowired
    ApiService apiService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    PageHelper pageHelper = new PageHelper();

    @ApiOperation(value = "新增一篇文章")
    @PutMapping("/insert")     // TODO 填写节点
    public ResponseBase insertArticle(@RequestBody Article article) {
        Article article_insert = null;
        ResponseBase responseBase;
        try {
            article_insert = articleRepository.save(article);
            responseBase = new ResponseBase(200, "新增文章成功", article_insert);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(30001, "新增文章失败", article);
        }

        return responseBase;
    }

    @ApiOperation(value = "查找文章")
    @PostMapping("/find")    // TODO 填写节点
    public ResponseBase findArticle(@PageableDefault(value = 5, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                            Pageable pageable,
                                    @RequestParam(value = "id", required = false, defaultValue = "") String id,
                                    @RequestParam(value = "key_word", required = false, defaultValue = "")String keyWord) {

        ResponseBase responseBase;
        try {
            Specification<Article> articleSpecification = apiService.createArticleSpecification(id, keyWord);
            Page<Article> page = articleRepository.findAll(articleSpecification, pageable);
            responseBase = new ResponseBase(200, "查找文章成功", page);
        } catch (Exception e) {
            e.printStackTrace();
            responseBase = new ResponseBase(30002, "查找文章失败", null);
        }
        return responseBase;
    }

    @ApiOperation(value = "查找特定用户的文章")
    @PostMapping("/find-by-user")   // TODO 待填
    public ResponseBase findArticleByUser(Pageable page, @RequestParam(value = "user") User user) {
        List<Article> result = articleRepository.findAllByUser(user);

        pageHelper.doPage(result, page);

        Page<Article> pageResult = new PageImpl(result, page, result.size());
        return new ResponseBase(200, "查询成功", pageResult);
    }

    @ApiOperation(value = "查找特定用户名的文章")
    @PostMapping("/find-by-username")
    public ResponseBase findByUsername(Pageable pageable, @RequestParam(value = "user_name")String userName){
        ResponseBase responseBase;

        Specification<User> userSpecification = apiService.createUserSpecification("", userName, "", "");
        Page<User> userPage = userRepository.findAll(userSpecification, pageable);

        List<User> userList = userPage.getContent();

        List<Article> articleList = new ArrayList<>();

        for(User user : userList){
            Set<Article> tempSet = user.getArticles();
            for(Article article : tempSet){
                articleList.add(article);
            }
        }

        pageHelper.doPage(articleList, pageable);

        Page<Article> pageResult = new PageImpl(articleList, pageable, articleList.size());
        return new ResponseBase(200, "查询成功", pageResult);
    }

    @ApiOperation(value = "删除一篇文章")
    @DeleteMapping("/delete")      // TODO 填写节点
    public ResponseBase deleteArticle(@RequestParam(value = "id") Integer id) {
        articleRepository.deleteById(id);
        return new ResponseBase(200, "删除成功", null);
    }

    @ApiOperation(value = "点赞")
    @PostMapping("/like")
    public ResponseBase likeArticle(@RequestParam(value = "article_id") Integer id){
        Article article = articleRepository.findById(id).get();
        article.setLikeNum(article.getLikeNum() + 1);
        articleRepository.save(article);
        return new ResponseBase(200, "点赞成功", null);
    }

}
