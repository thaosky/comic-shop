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


    @Query(nativeQuery = true,
            value = "SELECT c.name, c.comic_code, cd.comic_detail_code, DATE_FORMAT( r.start_date,'%d/%m/%Y') as startDate\n" +
                    "FROM comic_detail cd\n" +
                    "         inner join rent_comic_detail rcd on cd.id = rcd.comic_detail_id\n" +
                    "         inner join rent r on rcd.rent_id = r.id\n" +
                    "         inner join comic c on cd.comic_id = c.id\n" +
                    "where r.renting = true",
            countQuery = "SELECT count(*) \n" +
                    "FROM comic_detail cd\n" +
                    "         inner join rent_comic_detail rcd on cd.id = rcd.comic_detail_id\n" +
                    "         inner join rent r on rcd.rent_id = r.id\n" +
                    "         inner join comic c on cd.comic_id = c.id\n" +
                    "where r.renting = true")
    Page<?> test(Pageable pageable);


    @Query(nativeQuery = true,
            value = "SELECT count(rcd.id) as totalRent, c.name, c.comic_code\n" +
                    "FROM comic_detail cd\n" +
                    "         inner join rent_comic_detail rcd on cd.id = rcd.comic_detail_id\n" +
                    "         inner join comic c on cd.comic_id = c.id\n" +
                    "group by c.id\n" +
                    "order by totalRent desc\n" +
                    "LIMIT 10")
    List<?> topComic();

    @Query(nativeQuery = true,
            value = "select distinct cd.* from rent r\n" +
                    "left join rent_comic_detail rcd on r.id = rcd.rent_id\n" +
                    "left join comic_detail cd on rcd.comic_detail_id = cd.id\n" +
                    "left join  comic c on cd.comic_id = c.id\n" +
                    "where r.id = :rentId\n" +
                    "and c.id = :comicId")
    List<ComicDetailEntity> findComicDetailByRentIdAndComicId(Long rentId, Long comicId);
}
