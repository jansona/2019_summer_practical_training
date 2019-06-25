package com.example.demo.reposity;

import com.example.demo.entity.Article;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ArticleRepository extends JpaRepository<Article,Integer>, JpaSpecificationExecutor<Article> {
}
