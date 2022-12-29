package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "receipt_comic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReceiptComicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    ReceiptEntity receiptEntity;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    ComicEntity comicEntity;

    int quantity;
}
