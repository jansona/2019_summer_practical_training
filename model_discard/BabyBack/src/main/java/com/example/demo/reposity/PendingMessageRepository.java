package com.example.demo.reposity;

import com.example.demo.entity.MatchBaby;
import com.example.demo.entity.PendingMessage;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PendingMessageRepository extends JpaRepository<PendingMessage, Integer>, JpaSpecificationExecutor<PendingMessage> {
    List<PendingMessage> findByUser(User user);
}
