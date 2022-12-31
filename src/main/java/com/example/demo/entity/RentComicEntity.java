package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "rent_comic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentComicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long rentId;

    Long comicId;

    int quantity;
}
