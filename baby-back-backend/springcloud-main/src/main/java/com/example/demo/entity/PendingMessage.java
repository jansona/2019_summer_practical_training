package com.example.demo.entity;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
public class PendingMessage {

    public enum MessageType{
        LOST_NOTIFICATION,
        MATCH_NOTIFICATION,
        COMMENT_INFO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    private MessageType type;

    private String content;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
