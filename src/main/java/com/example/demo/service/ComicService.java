package com.example.demo.service;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.ComicDetailRepository;
import com.example.demo.repository.ComicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

@Service
public class ComicService {
    @Autowired
    ComicRepository comicRepository;
    @Autowired
    ComicDetailRepository comicDetailRepository;

    public Page<ComicEntity> getList(String name, String category, String author,
                                     String publisher, Integer pageNo, Integer pageSize,
                                     String sort, String sortName) {
        Sort sortable = Sort.by("id").ascending();

        if (sortName != null && sort.equals("ASC")) {
            sortable = Sort.by(sortName).ascending();
        } else if (sortName != null && sort.equals("DESC")) {
            sortable = Sort.by(sortName).descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortable);
        if (name != null) {
            name = name.toUpperCase();
        }
        if (category != null) {
            category = category.toUpperCase();
        }
        if (publisher != null) {
            publisher = publisher.toUpperCase();
        }
        if (author != null) {
            author = author.toUpperCase();
        }

        return comicRepository.listComic(name, category, author, publisher, pageable);
    }

    public ComicEntity create(ComicEntity comic) {
        // gen comic code
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("ddMMyy");
        String time = ft.format(now);
        String[] words = comic.getName().split(" ");
        String comicCode = "";
        for (String s : words) {
            comicCode += s.charAt(0);
        }
        comicCode = comicCode.toUpperCase() + time;

        ComicEntity comicEntity = comicRepository.save(comic);
        comicEntity.setComicCode(comicCode);
        Integer quantity = comicEntity.getQuantity();
        List<ComicDetailEntity> list = new ArrayList<>();
        if (quantity != null && quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                ComicDetailEntity comicDetailEntity = new ComicDetailEntity();
                comicDetailEntity.setComicId(comicEntity.getId());
                comicDetailEntity.setStatus("Sách mới");
                comicDetailEntity.setComicDetailCode(comicEntity.getComicCode() + "_" + (i + 1));
                list.add(comicDetailEntity);
            }
        }
        comicDetailRepository.saveAll(list);
        return comicEntity;
    }

    public void delete(Long id) throws BusinessException {
        Optional<ComicEntity> optional = comicRepository.findById(id);
        ComicEntity comicEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy đầu truyện"));
        comicRepository.deleteById(id);
    }

    public ComicEntity update(Long id, ComicEntity comic) throws BusinessException {
        Optional<ComicEntity> optional = comicRepository.findById(id);
        ComicEntity customerEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy đầu truyện"));
        BeanUtils.copyProperties(comic, customerEntity, "id");
        return comicRepository.save(customerEntity);
    }

    public ComicEntity getById(Long id) throws BusinessException {
        Optional<ComicEntity> optional = comicRepository.findById(id);
        return optional.orElseThrow(() -> new BusinessException("Không tìm thấy đầu truyện"));
    }
}
