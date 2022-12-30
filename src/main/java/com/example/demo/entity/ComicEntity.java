package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "comic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String author;
    private String publisher;
    private String category;
    private Long price;
    private Integer quantity;
    private String position;
    // Lưu 1 ảnh bìa
    private String path;

}
