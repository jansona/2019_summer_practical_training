package com.example.demo.reposity;

import com.example.demo.entity.Article;
import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MatchBabyRepository extends JpaRepository<MatchBaby,Integer>, JpaSpecificationExecutor<MatchBaby> {
}
