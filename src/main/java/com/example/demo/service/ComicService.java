package com.example.demo.service;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.ComicDetailRepository;
import com.example.demo.repository.ComicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComicService {
    @Autowired
    ComicRepository comicRepository;
    @Autowired
    ComicDetailRepository comicDetailRepository;

    public List<ComicEntity> getList(String name) {
        if ("".equals(name) || name == null) {
            return comicRepository.findAll();
        }
        return comicRepository.findAllByName(name);
    }

    public ComicEntity create(ComicEntity comic) {
        ComicEntity comicEntity = comicRepository.save(comic);
        Integer quantity = comicEntity.getQuantity();
        List<ComicDetailEntity> list = new ArrayList<>();
        if (quantity != null && quantity > 0) {
            for (int i = 0; i < quantity; i++) {
                ComicDetailEntity comicDetailEntity = new ComicDetailEntity();
                comicDetailEntity.setComicId(comicEntity.getId());
                comicDetailEntity.setStatus("Sách mới");

                list.add(comicDetailEntity);
            }
        }
        comicDetailRepository.saveAll(list);
        return comicEntity;
    }

    public void delete(Long id) throws BusinessException {
        Optional<ComicEntity> optional = comicRepository.findById(id);
        ComicEntity comicEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy truyện"));
        comicRepository.deleteById(id);
    }

    public ComicEntity update(Long id, ComicEntity comic) throws BusinessException {
        Optional<ComicEntity> optional = comicRepository.findById(id);
        ComicEntity customerEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy truyện"));
        BeanUtils.copyProperties(comic, customerEntity, "id");
        return comicRepository.save(customerEntity);
    }
}
