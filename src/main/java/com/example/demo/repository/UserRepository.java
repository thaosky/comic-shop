package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Page<UserEntity> findAllByUsername(String username, Pageable pageable);
    List<UserEntity> findAllByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}