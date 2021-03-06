package com.example.demo.reposity;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findById(Integer id);

    User findByTel(String tel);

    User findByEmail(String email);

}
