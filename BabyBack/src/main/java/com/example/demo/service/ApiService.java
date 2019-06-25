package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.reposity.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    LostBabyRepository lostBabyRepository;
    @Autowired
    MatchBabyRepository matchBabyRepository;
    @Autowired
    UserRepository userRepository;


    public Specification<User> createSpecification(String id,
                                                  String account,
                                                   String name,
                                                   String tel,
                                                   String email) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //用于暂时存放查询条件的集合
                List<Predicate> predicatesList = new ArrayList<>();

                //like示例
                if (!id.equals("")) {
                    Predicate contactPredicate = cb.equal(root.get("id"), Integer.valueOf(id));
                    predicatesList.add(contactPredicate);
                }
                if (!account.equals("")) {
                    Predicate descriptionPredicate = cb.like(root.get("account"), "%" + account + "%");
                    predicatesList.add(descriptionPredicate);
                }
                if (!name.equals("")) {
                    Predicate titlePredicate = cb.like(root.get("name"), "%" + name + "%");
                    predicatesList.add(titlePredicate);
                }
                if (!tel.equals("")) {
                    Predicate serviceNamePredicate = cb.like(root.get("tel"), "%" + tel + "%");
                    predicatesList.add(serviceNamePredicate);
                }
                if (!email.equals("")) {
                    Predicate serviceNamePredicate = cb.like(root.get("email"), "%" + email + "%");
                    predicatesList.add(serviceNamePredicate);
                }


                Predicate[] predicates = new Predicate[predicatesList.size()];
                return cb.and(predicatesList.toArray(predicates));
            }


        };

    }
}
