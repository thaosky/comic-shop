package com.example.demo.repository;

import com.example.demo.entity.ComicEntity;
import com.example.demo.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository<ComicEntity, Long> {
    List<ComicEntity> findAllByName(String name);
    @Query(nativeQuery = true, value = "select *\n" +
            "from comic c\n" +
            "where (UPPER(c.name) like CONCAT('%',:name,'%') or :name is null)\n" +
            "  and (UPPER(c.author) like CONCAT('%',:author,'%') or :author is null)\n" +
            "  and (UPPER(c.category) like CONCAT('%',:category,'%') or :category is null)\n" +
            "  and (UPPER(c.comic_code) like CONCAT('%',:comicCode,'%') or :comicCode is null)\n" +
            "  and (UPPER(c.publisher) like CONCAT('%',:publisher,'%') or :publisher is null)",
    countQuery = "select count(*)\n" +
            "from comic c\n" +
            "where (UPPER(c.name) like CONCAT('%',:name,'%') or :name is null)\n" +
            "  and (UPPER(c.author) like CONCAT('%',:author,'%') or :author is null)\n" +
            "  and (UPPER(c.category) like CONCAT('%',:category,'%') or :category is null)\n" +
            "  and (UPPER(c.comic_code) like CONCAT('%',:comicCode,'%') or :comicCode is null)\n" +
            "  and (UPPER(c.publisher) like CONCAT('%',:publisher,'%') or :publisher is null)")
    Page<ComicEntity> listComic(String name, String comicCode, String category, String author, String publisher, Pageable pageable);
}
