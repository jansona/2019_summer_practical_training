package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    private String account;//账号
    private String passWord;//密码
    private String name;//姓名
    private String tel;//联系电话
    private String email;//邮箱
    @OneToMany(mappedBy = "user",fetch= FetchType.EAGER)
    @JsonIgnore
    private Set<LostBaby> lostBabies;//发布的走丢儿童信息
    @OneToMany(mappedBy = "user",fetch= FetchType.EAGER)
    @JsonIgnore
    private Set<MatchBaby> matchBabies;//发布的可能匹配的儿童信息
    @OneToMany(mappedBy = "user",fetch= FetchType.EAGER)
    @JsonIgnore
    private Set<Article> articles;//发布的文章
    @OneToMany(mappedBy = "user",fetch= FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;//发布的可能匹配的儿童信息


}
