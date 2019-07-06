package com.example.demo.reposity;

import com.example.demo.entity.Article;
import com.example.demo.entity.Comment;
import com.example.demo.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {

//    final String findByArticleIdSQL = "SELECT *" +
//            "FROM article" +
//            "WHERE user_id= :id;";
//
//    @Query(value = findByArticleIdSQL, nativeQuery = true)
//    public List<Comment> findByArticleId(@Param("team_id") String sId);

    List<Comment> findAllByArticle(Article article);

    List<Comment> findAllByUser(User user);

}
