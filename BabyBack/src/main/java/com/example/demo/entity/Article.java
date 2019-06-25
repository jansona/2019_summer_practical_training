package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "article",fetch= FetchType.EAGER)
    @JsonIgnore
    private Set<Comment> comments;//发布的可能匹配的儿童信息
    private String content;
}
