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

    Long receipt_id;

    Long comic_id;

    int quantity;
}
