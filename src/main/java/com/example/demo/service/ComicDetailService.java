package com.example.demo.service;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.ComicDetailRepository;
import com.example.demo.repository.ComicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComicDetailService {

    @Autowired
    ComicRepository comicRepository;
    @Autowired
    ComicDetailRepository comicDetailRepository;

    public ComicDetailEntity create(ComicDetailEntity comicDetail) throws BusinessException {
        Optional<ComicEntity> optional = comicRepository.findById(comicDetail.getComicId());
        ComicEntity comicEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy truyện"));

        // Khi add thêm comic detail thì quantity tăng lên 1
        comicEntity.setQuantity(comicEntity.getQuantity() + 1);
        comicRepository.save(comicEntity);

        ComicDetailEntity comicDetailEntity = new ComicDetailEntity();
        BeanUtils.copyProperties(comicDetail, comicDetailEntity);

        return comicDetailRepository.save(comicDetail);
    }

    public void softDelete(Long id) {
        ComicDetailEntity comicDetailEntity = comicDetailRepository.getById(id);
        comicDetailEntity.setAvailable(false);
    }
}
