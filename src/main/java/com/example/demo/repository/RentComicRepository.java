package com.example.demo.repository;

import com.example.demo.entity.RentComicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentComicRepository extends JpaRepository<RentComicEntity, Long> {
}
