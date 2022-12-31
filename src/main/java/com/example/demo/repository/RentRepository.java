package com.example.demo.repository;

import com.example.demo.entity.RentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<RentEntity, Long> {
    List<RentEntity> findAllByCustomerId(Long customerId);

}
