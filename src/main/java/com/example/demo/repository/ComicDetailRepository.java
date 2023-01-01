package com.example.demo.repository;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComicDetailRepository extends JpaRepository<ComicDetailEntity, Long> {
    List<ComicDetailEntity> findAllByComicIdAndAvailableOrderByIdDesc(Long comicId, boolean available);
    List<ComicDetailEntity> findAllByComicIdOrderByIdDesc(Long comicId);
    Optional<ComicDetailEntity> findTopByComicIdOrderByIdDesc(Long comicId);
    @Query(nativeQuery = true,
            value = "SELECT cd.*\n" +
                    "FROM comic_detail cd\n" +
                    "         left join rent_comic_detail rcd on cd.id = rcd.comic_detail_id\n" +
                    "         left join rent r on rcd.rent_id = r.id\n" +
                    "where r.renting = true",
            countQuery = "SELECT count(*)\n" +
                    "FROM comic_detail cd\n" +
                    "         left join rent_comic_detail rcd on cd.id = rcd.comic_detail_id\n" +
                    "         left join rent r on rcd.rent_id = r.id\n" +
                    "where r.renting = true")
    Page<ComicDetailEntity> findComicDetailRenting(Pageable pageable);
}
