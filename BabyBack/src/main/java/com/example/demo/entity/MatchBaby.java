package com.example.demo.entity;


import javax.persistence.*;
import java.util.Date;


@Entity
public class MatchBaby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String sex;
    private Date birthday;
    private Double height;//失踪时身高
    private String place;//失踪时地点
    private String nativePlace;//失踪人籍贯
    private Date date;//失踪时间
    private String babyDescription;//失踪人特征描述
    private String missDescription;//失踪经过描述
    private String otherDescription;//其他信息描述
    private String otherExplain;//其他说明

    private String picUrl;

    // 联系人信息
    private String contactName;
    private String contactRel;
    private String contactAddress;
    private String contactEmail;
    private String contactPhone;
    private String otherContactMethod;

    private String description;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public MatchBaby() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
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
