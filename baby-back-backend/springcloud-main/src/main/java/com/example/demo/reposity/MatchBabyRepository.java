package com.example.demo.reposity;

import com.example.demo.entity.Article;
import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MatchBabyRepository extends JpaRepository<MatchBaby, Integer>, JpaSpecificationExecutor<MatchBaby> {
    List<MatchBaby> findAllByUser(User user);
}
