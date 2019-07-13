package com.example.demo.reposity;

import com.example.demo.entity.Article;
import com.example.demo.entity.Comment;
import com.example.demo.entity.LostBaby;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LostBabyRepository extends JpaRepository<LostBaby, Integer>, JpaSpecificationExecutor<LostBaby> {
    List<LostBaby> findAllByUser(User user);
}
