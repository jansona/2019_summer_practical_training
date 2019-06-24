package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class MatchBaby {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    private String imagePath;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "user_id")
    private User user;
}
