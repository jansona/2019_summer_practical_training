package com.example.demo.reposity;

import com.example.demo.entity.Article;
import com.example.demo.entity.LostBaby;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

    //    String findAllByUserIdSQL = "SELECT * FROM ";
    List<Article> findAllByUser(User user);
}
