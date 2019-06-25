package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
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
    private String imagePath;//图片路径
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "user_id")
    private User user;
}
