package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.reposity.ArticleRepository;
import com.example.demo.reposity.CommentRepository;
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

import java.net.Inet4Address;
import java.util.List;

@RestController
@RequestMapping(value = "Comment")
public class CommentController {
    @Autowired
    ApiService apiService;
    @Autowired
    CommentRepository commentRepository;

    @ApiOperation(value="新增一篇评论")
    @PutMapping("")     // TODO 填写节点
    public Comment insertComment(@RequestBody Comment comment){
        return commentRepository.save(comment);
    }

    @ApiOperation(value="查找评论")
    @PostMapping("")    // TODO 填写节点
    public Page<Comment> findComment(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息")
                                          Pageable pageable,
                                  @RequestParam(value = "id",required = false,defaultValue ="") String id,
                                  @RequestParam(value = "article_id",required = false,defaultValue ="") String user_id,
                                  @RequestParam(value = "user_id", required = false, defaultValue = "") String article_id){
        Specification<Comment> commentSpecification = apiService.createCommentSpecification(article_id, user_id);
        return commentRepository.findAll(commentSpecification, pageable);
    }

    @ApiOperation(value = "查询特定文章的评论")
    @PostMapping("/temp")   // TODO 待填
    public List<Comment> findCommandOfArticle(@RequestParam(value = "article_id") Article article){
        return commentRepository.findAllByArticle(article);
    }

    @ApiOperation(value="删除一篇评论")
    @DeleteMapping("")      // TODO 填写节点
    public void deleteComment(@RequestParam(value = "id") Integer id){
        commentRepository.deleteById(id);
    }

}
