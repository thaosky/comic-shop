package com.example.demo.controller;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.ComicDetailRepository;
import com.example.demo.service.ComicDetailService;
import com.example.demo.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comic-details")
public class ComicDetailController {
    @Autowired
    ComicDetailService comicDetailService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ComicDetailEntity>> listComicDetailsByComicId(@Param("comicId") Long comicId, @Param("available") Boolean available) throws BusinessException {
        List<ComicDetailEntity> res = comicDetailService.listComicDetails(comicId, available);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ComicDetailEntity> create(@RequestBody ComicDetailEntity comicDetail) throws BusinessException {
        ComicDetailEntity res = comicDetailService.create(comicDetail);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }


    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ComicDetailEntity> update(@PathVariable Long id, @RequestBody ComicDetailEntity comicDetail) throws BusinessException {
        ComicDetailEntity comicDetailEntity = comicDetailService.update(id, comicDetail);
        return new ResponseEntity<>(comicDetailEntity, HttpStatus.NO_CONTENT);
    }

}



