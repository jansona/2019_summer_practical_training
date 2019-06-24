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
    private String name;
    private Integer age;
    private String place;
    private Date date;
    private String description;
    private String imagePath;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "user_id")
    private User user;
}
