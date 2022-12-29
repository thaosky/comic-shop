package com.example.demo.repository;

import com.example.demo.entity.ComicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository<ComicEntity, Long> {
    List<ComicEntity> findAllByName(String name);
}
