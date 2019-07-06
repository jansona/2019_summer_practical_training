package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Comment;
import com.example.demo.entity.ResponseBase;
import com.example.demo.entity.User;
import com.example.demo.reposity.CommentRepository;
import com.example.demo.reposity.UserRepository;
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
    @Autowired
    UserRepository userRepository;

    PageHelper pageHelper = new PageHelper();

    @ApiOperation(value = "新增一篇评论")
    @PutMapping("/insert")     // TODO 填写节点
    public ResponseBase insertComment(@RequestBody Comment comment) {
        ResponseBase responseBase;
        return new ResponseBase(200, "加入评论成功", commentRepository.save(comment));
    }

    @ApiOperation(value = "查找评论")
    @PostMapping("/find")    // TODO 填写节点
    public ResponseBase findComment(@PageableDefault(value = 20, sort = {"id"}, direction = Sort.Direction.DESC) @ApiParam(value = "分页信息")
                                             Pageable pageable,
                                     @RequestParam(value = "id", required = false, defaultValue = "") String id) {
        Specification<Comment> commentSpecification = apiService.createCommentSpecification(id);
        return new ResponseBase(200, "查找成功", commentRepository.findAll(commentSpecification, pageable));
    }

    @ApiOperation(value = "查询特定文章的评论")
    @PostMapping("/find-by-article")   // TODO 待填
    public ResponseBase findCommentByArticle(Pageable page,
                                             @RequestParam(value = "article") Article article) {
        List<Comment> result = commentRepository.findAllByArticle(article);

        int totalNum = result.size();
        result = (List<Comment>) pageHelper.doPage(result, page);

        Page<Comment> pageResult = new PageImpl(result, page, totalNum);
        return new ResponseBase(200, "查找成功", pageResult);
    }

    @ApiOperation(value = "查询特定用户的评论")
    @PostMapping("/find-by-user")   // TODO 待填
    public ResponseBase findCommentByUser(Pageable page, @RequestParam(value = "user") User user) {
        List<Comment> result = commentRepository.findAllByUser(user);

        int totalNum = result.size();
        result = (List<Comment>) pageHelper.doPage(result, page);

        Page<Comment> pageResult = new PageImpl(result, page, totalNum);
        return new ResponseBase(200, "查找成功", pageResult);
    }

    @ApiOperation(value = "查询特定用户的特定索引的评论(为无限滚动提供数据)")
    @PostMapping("/infinite-scroll")
    public ResponseBase getArticleInfinite(@RequestParam(value = "user") Long user, @RequestParam(value = "index") Integer index){
        ResponseBase responseBase;
        List<Comment> commentList = commentRepository.findAllByUser(userRepository.findById(user).get());
        if(index >= commentList.size()){
            return new ResponseBase(200, "无更多评论", null);
        }
        Comment comment = commentList.get(index);
        comment.setUser(null);
        comment.getArticle().setUser(null);
        return new ResponseBase(200, "成功返回无限滚动所需数据", comment);
    }

    @ApiOperation(value = "删除一篇评论")
    @DeleteMapping("/delete")      // TODO 填写节点
    public ResponseBase deleteComment(@RequestParam(value = "id") Integer id) {
        commentRepository.deleteById(id);
        return new ResponseBase(200, "删除成功", null);
    }

}
