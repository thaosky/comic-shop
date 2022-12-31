package com.example.demo.service;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.ComicDetailRepository;
import com.example.demo.repository.ComicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ComicDetailEntity update(Long id, ComicDetailEntity comicDetail) throws BusinessException {
        // Update lại bảng comic
        if (!comicDetail.isAvailable()) {
            Optional<ComicEntity> optional = comicRepository.findById(comicDetail.getComicId());
            ComicEntity comicEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy truyện"));
            comicEntity.setQuantity(comicEntity.getQuantity() - 1);
            comicRepository.save(comicEntity);
        }

        // Update bảng comic detail
        ComicDetailEntity comicDetailEntity = comicDetailRepository.getById(id);
        BeanUtils.copyProperties(comicDetail, comicDetailEntity, "id");
        return comicDetailRepository.save(comicDetailEntity);
    }

    public List<ComicDetailEntity> listComicDetails(Long comicId, Boolean available) {
        if (available == null) {
            return comicDetailRepository.findAllByComicId(comicId);
        } else {
            return comicDetailRepository.findAllByComicIdAndAvailable(comicId, available);
        }
    }
}
