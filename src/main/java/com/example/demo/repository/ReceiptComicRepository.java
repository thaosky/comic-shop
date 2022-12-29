package com.example.demo.repository;

import com.example.demo.entity.ReceiptComicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptComicRepository extends JpaRepository<ReceiptComicEntity, Long> {
}
