package com.blogapi.entity;

import ch.qos.logback.classic.db.names.ColumnName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor


@Entity
//@Table(name = "posts" ,uniqueConstraints = {@UniqueConstraint(ColumnName={"titles"})})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable=false,unique=true)//another way to make title unique
    private String title;

    @Column(name="description",nullable=false)
    private String description;

    @Column(name="content",nullable=false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

}
