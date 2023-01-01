package com.example.demo.repository;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {
    List<RentEntity> findAllByCustomerId(Long customerId);
    @Query(nativeQuery = true,
            value = "select r.* from customer c\n" +
                    "left join rent r on c.id = r.customer_id\n" +
                    "where customer_id = :customerId\n")
    List<RentEntity> findRentByCustomerId(Long customerId);
}
