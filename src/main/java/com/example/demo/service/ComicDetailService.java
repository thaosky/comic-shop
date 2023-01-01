package com.example.demo.service;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.entity.CustomerEntity;
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

        // Tìm phần tử cuối cùng đã được thêm
        ComicDetailEntity oldest = comicDetailRepository.findTopByComicIdOrderByIdDesc(comicEntity.getId()).get();
        String[] oldestCodeArr = oldest.getComicDetailCode().split("_");
        int stt = Integer.parseInt(oldestCodeArr[1]) + 1;

        ComicDetailEntity comicDetailEntity = new ComicDetailEntity();
        BeanUtils.copyProperties(comicDetail, comicDetailEntity);
        comicDetailEntity.setComicDetailCode(oldestCodeArr[0] + "_" + stt);

        return comicDetailRepository.save(comicDetailEntity);
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
            return comicDetailRepository.findAllByComicIdOrderByIdDesc(comicId);
        } else {
            return comicDetailRepository.findAllByComicIdAndAvailableOrderByIdDesc(comicId, available);
        }
    }

    public Page<ComicDetailEntity> getComicDetailRenting(Integer pageSize, Integer pageNo, String sort, String sortName) {
        Sort sortable = Sort.by("id").descending();
        if(sortName != null && sort.equals("ASC")) {
            sortable = Sort.by(sortName).ascending();
        } else if (sortName != null && sort.equals("DESC")) {
            sortable = Sort.by(sortName).descending();
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortable);
        Page<ComicDetailEntity>  page = comicDetailRepository.findComicDetailRenting(pageable);
        return page;
    }
}
