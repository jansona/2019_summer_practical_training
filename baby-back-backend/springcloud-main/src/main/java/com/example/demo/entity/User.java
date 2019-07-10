package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.util.Pair;
import javax.persistence.*;
import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Column(unique = true)
//    private String account;//账号
    private String password;//密码
    private String username;//姓名
    @Column(unique = true)
    private String tel;//联系电话
    private String email;//邮箱
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<LostBaby> lostBabies;//发布的走丢儿童信息
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<MatchBaby> matchBabies;//发布的可能匹配的儿童信息
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Article> articles;//发布的文章
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;//发布的可能匹配的儿童信息

    private String profileUrl;

    private String location;
    @JsonIgnore
    private Pair<Float, Float> coordinate;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String getAccount() {
//        return account;
//    }
//
//    public void setAccount(String account) {
//        this.account = account;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<LostBaby> getLostBabies() {
        return lostBabies;
    }

    public void setLostBabies(Set<LostBaby> lostBabies) {
        this.lostBabies = lostBabies;
    }

    public Set<MatchBaby> getMatchBabies() {
        return matchBabies;
    }

    public void setMatchBabies(Set<MatchBaby> matchBabies) {
        this.matchBabies = matchBabies;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Pair<Float, Float> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Pair<Float, Float> coordinate) {
        this.coordinate = coordinate;
    }
}
