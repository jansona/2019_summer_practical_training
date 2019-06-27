package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import com.example.demo.reposity.ArticleRepository;
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

@RestController
@RequestMapping(value = "Article")
public class ArticleController {
    @Autowired
    ApiService apiService;
    @Autowired
    ArticleRepository articleRepository;

    PageHelper pageHelper = new PageHelper();

    @ApiOperation(value = "新增一篇文章")
    @PutMapping("")     // TODO 填写节点
    public Article insertArticle(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @ApiOperation(value = "查找文章")
    @PostMapping("")    // TODO 填写节点
    public Page<Article> findArticle(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                             Pageable pageable,
                                     @RequestParam(value = "id", required = false, defaultValue = "") String id
    ) {
        Specification<Article> articleSpecification = apiService.createArticleSpecification(id);
        return articleRepository.findAll(articleSpecification, pageable);
    }

    @ApiOperation(value = "查找特定用户的文章")
    @PostMapping("/find-by-user")   // TODO 待填
    public Page<Article> findArticleByUser(Pageable page, @RequestParam(value = "user") User user){
        List<Article> result = articleRepository.findAllByUser(user);

        pageHelper.doPage(result, page);

        Page<Article> pageResult = new PageImpl(result, page, result.size());
        return pageResult;
    }

    @ApiOperation(value = "删除一篇文章")
    @DeleteMapping("")      // TODO 填写节点
    public void deleteArticle(@RequestParam(value = "id") Integer id) {
        articleRepository.deleteById(id);
    }

}
