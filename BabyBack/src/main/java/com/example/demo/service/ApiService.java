package com.example.demo.service;

import com.example.demo.entity.LostBaby;
import com.example.demo.entity.User;
import com.example.demo.reposity.*;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public String initDescription(LostBaby lostBaby){
        StringBuilder sb=new StringBuilder();
        if(lostBaby.getBabyDescription()!=null){
            sb.append(lostBaby.getBabyDescription());
        }
        if(lostBaby.getMissDescription()!=null){
            sb.append(lostBaby.getMissDescription());
        }
        if(lostBaby.getBackGround()!=null){
            sb.append(lostBaby.getBackGround());
        }
        if(lostBaby.getOtherDescription()!=null){
            sb.append(lostBaby.getOtherDescription());
        }
        if(lostBaby.getOtherExplain()!=null){
            sb.append(lostBaby.getOtherExplain());
        }
        return sb.toString();

    }
    public Set<String> initKeyWord(String keyWord){
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> Tags=segmenter.sentenceProcess(keyWord);
        HashSet<String> h=new HashSet<>(Tags);
        return h;
    }



    public Specification<LostBaby> createLostBabySpecification(String id,String place,String name,String height,String nativePlace,String date) {
        return new Specification<LostBaby>() {

            @Override
            public Predicate toPredicate(Root<LostBaby> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicatesList = new ArrayList<>();

                //like示例
                if (!id.equals("")) {
                    Predicate contactPredicate = cb.equal(root.get("id"), Integer.valueOf(id));
                    predicatesList.add(contactPredicate);
                }
                if (!place.equals("")) {
                    Predicate descriptionPredicate = cb.like(root.get("place"), "%" + place + "%");
                    predicatesList.add(descriptionPredicate);
                }
                if (!name.equals("")) {
                    Predicate titlePredicate = cb.like(root.get("name"), "%" + name + "%");
                    predicatesList.add(titlePredicate);
                }
                if (!height.equals("")) {
                    Predicate serviceNamePredicate = cb.equal(root.get("height"), Double.valueOf(height));
                    predicatesList.add(serviceNamePredicate);
                }
                if (!nativePlace.equals("")) {
                    Predicate serviceNamePredicate = cb.like(root.get("email"), "%" + nativePlace + "%");
                    predicatesList.add(serviceNamePredicate);
                }
                if (!date.equals("")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {

                        Predicate serviceNamePredicate = cb.equal(root.get("date"), sdf.parse(date));
                        predicatesList.add(serviceNamePredicate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
//                if(!keyWord.equals("")){

                   // CriteriaBuilder.In<String> in=cb.in(root.get("babyDescription"));
//                    for (String s:initKeyWord(keyWord)) {
//                        Predicate kWPredicate = cb.like(root.get("name"), "%" + s + "%");
//                        predicatesList.add(kWPredicate);
//                    }
                    //predicatesList.add(in);
//                }

                Predicate[] predicates = new Predicate[predicatesList.size()];
                return cb.and(predicatesList.toArray(predicates));

            }
        };
    }

            public Specification<User> createUserSpecification(String id,
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