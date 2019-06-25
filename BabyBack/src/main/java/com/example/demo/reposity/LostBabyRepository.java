package com.example.demo.reposity;

import com.example.demo.entity.LostBaby;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LostBabyRepository extends JpaRepository<LostBaby,Integer> , JpaSpecificationExecutor<LostBaby> {

}
