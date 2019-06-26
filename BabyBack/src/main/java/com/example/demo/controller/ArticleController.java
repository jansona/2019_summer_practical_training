package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import com.example.demo.reposity.ArticleRepository;
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
@RequestMapping(value = "Article")
public class ArticleController {
    @Autowired
    ApiService apiService;
    @Autowired
    ArticleRepository articleRepository;

    @ApiOperation(value="新增一篇文章")
    @PutMapping("")     // TODO 填写节点
    public Article insertArticle(@RequestBody Article article){
        return articleRepository.save(article);
    }

    @ApiOperation(value="查找文章")
    @PostMapping("")    // TODO 填写节点
    public Page<Article> findArticle(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息")
                                       Pageable pageable,
                               @RequestParam(value = "id",required = false,defaultValue ="") String id,
                               @RequestParam(value = "user_id",required = false,defaultValue ="") String user_id
    ){
        Specification<Article> articleSpecification = apiService.createArticleSpecification(id, user_id);
        return articleRepository.findAll(articleSpecification, pageable);
    }

//    @ApiOperation(value = "查找特定用户的文章")
//    @PostMapping("")
//    public Page<Article> findArticleOfUser(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息")
//        Pageable pageable, @RequestParam(value = "user_id") String user_id
//    ){
//
//    }

    @ApiOperation(value="删除一篇文章")
    @DeleteMapping("")      // TODO 填写节点
    public void deleteArticle(@RequestParam(value = "id") Integer id){
        articleRepository.deleteById(id);
    }

}
