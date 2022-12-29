package com.example.demo.controller;

import com.example.demo.entity.ComicEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/comics")
public class ComicController {
    @Autowired
    ComicService comicService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ComicEntity>> getList(@Param("name") String name) {
        List<ComicEntity> list = comicService.getList(name);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ComicEntity> create(@RequestBody ComicEntity comic) {
        ComicEntity customerEntity = comicService.create(comic);
        return new ResponseEntity<>(customerEntity, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ComicEntity> update(@RequestBody ComicEntity comic, @PathVariable Long id) throws BusinessException {
         ComicEntity res = comicService.update(id, comic);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws BusinessException {
        comicService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

