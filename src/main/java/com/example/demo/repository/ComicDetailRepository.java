package com.example.demo.repository;

import com.example.demo.entity.ComicDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicDetailRepository extends JpaRepository<ComicDetailEntity, Long> {
}
