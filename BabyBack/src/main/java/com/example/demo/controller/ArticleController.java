package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.response.ResponseBase;
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

@CrossOrigin
@RestController
@RequestMapping(value = "article")
public class ArticleController {
    @Autowired
    ApiService apiService;
    @Autowired
    ArticleRepository articleRepository;

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
                                    @RequestParam(value = "id", required = false, defaultValue = "") String id) {

        ResponseBase responseBase;
        try {
            Specification<Article> articleSpecification = apiService.createArticleSpecification(id);
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
    public Page<Article> findArticleByUser(Pageable page, @RequestParam(value = "user") User user) {
        List<Article> result = articleRepository.findAllByUser(user);

        pageHelper.doPage(result, page);

        Page<Article> pageResult = new PageImpl(result, page, result.size());
        return pageResult;
    }

    @ApiOperation(value = "删除一篇文章")
    @DeleteMapping("/delete")      // TODO 填写节点
    public void deleteArticle(@RequestParam(value = "id") Integer id) {
        articleRepository.deleteById(id);
    }

    @ApiOperation(value = "点赞")
    @PostMapping("/like")
    public void likeArticle(@RequestParam(value = "article_id") Integer id){
        Article article = articleRepository.findById(id).get();
        article.setLikeNum(article.getLikeNum() + 1);
    }

}
