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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;//失踪宝贝姓名
    private String sex;//性别
    private Date birthday;//生日
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

    private String picUrl;

    // 联系人信息
    private String contactName;
    private String contactRel;
    private String contactAddress;
    private String contactEmail;
    private String contactPhone;
    private String otherContactMethod;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactRel() {
        return contactRel;
    }

    public void setContactRel(String contactRel) {
        this.contactRel = contactRel;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getOtherContactMethod() {
        return otherContactMethod;
    }

    public void setOtherContactMethod(String otherContactMethod) {
        this.otherContactMethod = otherContactMethod;
    }
}
