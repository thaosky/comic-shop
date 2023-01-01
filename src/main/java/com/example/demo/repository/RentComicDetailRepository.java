package com.example.demo.repository;

import com.example.demo.entity.RentComicDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentComicDetailRepository extends JpaRepository<RentComicDetailEntity, Long> {
}
