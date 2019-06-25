package com.example.demo.reposity;

import com.example.demo.entity.LostBaby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostBabyRepository extends JpaRepository<LostBaby,Integer> {
}
