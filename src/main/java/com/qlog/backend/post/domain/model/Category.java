package com.qlog.backend.post.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //카테고리 ID

    @Column(unique = true, nullable = false)
    private String name; //카테고리 이름

    @ManyToMany(mappedBy = "categories")
    private Set<Post> posts = new HashSet<>(); //Post 테이블과 N:M 관계

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public void changeCategoryName(String name) {
        this.name = name;
    }
}
