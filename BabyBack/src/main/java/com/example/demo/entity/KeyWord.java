package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class KeyWord {
    @Id
    private String name;
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private Set<LostBaby> lostBabies = new HashSet<>();

    public KeyWord() {
    }

    public KeyWord(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LostBaby> getLostBabies() {
        return lostBabies;
    }

    public void setLostBabies(Set<LostBaby> lostBabies) {
        this.lostBabies = lostBabies;
    }
}
