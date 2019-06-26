package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class LostBaby {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;//失踪宝贝姓名
    private Integer age;//失踪时年龄
    private Double height;//失踪时身高
    private String place;//失踪时地点
    private String nativePlace;//失踪人籍贯
    private Date date;//失踪时间
    private String babyDescription;//失踪人特征描述
    private String missDescription;//失踪经过描述
    private String backGround;//家庭背景及线索资料
    private String otherDescription;//其他信息描述
    private String otherExplain;//其他说明
    private String relationship;//和报备人关系
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "lostBabies")
    @JsonIgnore
    private Set<KeyWord> keyWords = new HashSet<>();

    public LostBaby() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBabyDescription() {
        return babyDescription;
    }

    public void setBabyDescription(String babyDescription) {
        this.babyDescription = babyDescription;
    }

    public String getMissDescription() {
        return missDescription;
    }

    public void setMissDescription(String missDescription) {
        this.missDescription = missDescription;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    public String getOtherExplain() {
        return otherExplain;
    }

    public void setOtherExplain(String otherExplain) {
        this.otherExplain = otherExplain;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }
}
