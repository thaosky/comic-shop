package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "comic_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComicDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // Cu moi rach nat
    private String comicDetailCode;
    private Long comicId;
    private boolean available = true;
}
