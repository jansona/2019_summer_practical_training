package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "article_id")
    private Article article;
    private String content;
}
