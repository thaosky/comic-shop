package com.example.demo.repository;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComicDetailRepository extends JpaRepository<ComicDetailEntity, Long> {
    List<ComicDetailEntity> findAllByComicIdAndAvailable(Long comicId, boolean available);
    List<ComicDetailEntity> findAllByComicId(Long comicId);
    Optional<ComicDetailEntity> findTopByComicIdOrderByIdDesc(Long comicId);
}
