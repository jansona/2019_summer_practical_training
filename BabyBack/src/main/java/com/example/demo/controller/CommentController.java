package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.reposity.CommentRepository;
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
@RequestMapping(value = "comment")
public class CommentController {
    @Autowired
    ApiService apiService;
    @Autowired
    CommentRepository commentRepository;

    PageHelper pageHelper = new PageHelper();

    @ApiOperation(value="新增一篇评论")
    @PutMapping("/insert")     // TODO 填写节点
    public Comment insertComment(@RequestBody Comment comment){
        return commentRepository.save(comment);
    }

    @ApiOperation(value="查找评论")
    @PostMapping("/find")    // TODO 填写节点
    public Page<Comment> findComment(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC)@ApiParam(value = "分页信息")
                                          Pageable pageable,
                                  @RequestParam(value = "id",required = false,defaultValue ="") String id){
        Specification<Comment> commentSpecification = apiService.createCommentSpecification(id);
        return commentRepository.findAll(commentSpecification, pageable);
    }

    @ApiOperation(value = "查询特定文章的评论")
    @PostMapping("/find-by-article")   // TODO 待填
    public Page<Comment> findCommentByArticle(Pageable page, @RequestParam(value = "article") Article article){
        List<Comment> result = commentRepository.findAllByArticle(article);

        pageHelper.doPage(result, page);

        Page<Comment> pageResult = new PageImpl(result, page, result.size());
        return pageResult;
    }

    @ApiOperation(value = "查询特定用户的评论")
    @PostMapping("/find-by-user")   // TODO 待填
    public Page<Comment> findCommentByUser(Pageable page, @RequestParam(value = "user") User user){
        List<Comment> result = commentRepository.findAllByUser(user);

        pageHelper.doPage(result, page);

        Page<Comment> pageResult = new PageImpl(result, page, result.size());
        return pageResult;
    }

    @ApiOperation(value="删除一篇评论")
    @DeleteMapping("/delete")      // TODO 填写节点
    public void deleteComment(@RequestParam(value = "id") Integer id){
        commentRepository.deleteById(id);
    }

}
