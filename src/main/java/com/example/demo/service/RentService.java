package com.example.demo.service;

import com.example.demo.entity.ComicDetailEntity;
import com.example.demo.entity.ComicEntity;
import com.example.demo.entity.RentComicDetailEntity;
import com.example.demo.entity.RentEntity;
import com.example.demo.model.request.receipt.Comic;
import com.example.demo.model.request.receipt.ComicDetail;
import com.example.demo.model.request.receipt.Rent;
import com.example.demo.repository.ComicDetailRepository;
import com.example.demo.repository.ComicRepository;
import com.example.demo.repository.RentComicDetailRepository;
import com.example.demo.repository.RentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RentService {
    final RentRepository rentRepository;
    final RentComicDetailRepository rentComicDetailRepository;
    final ComicDetailRepository comicDetailRepository;
    final ComicRepository comicRepository;

    public RentService(RentRepository rentRepository, RentComicDetailRepository rentComicDetailRepository, ComicDetailRepository comicDetailRepository, ComicRepository comicRepository) {
        this.rentRepository = rentRepository;
        this.rentComicDetailRepository = rentComicDetailRepository;
        this.comicDetailRepository = comicDetailRepository;
        this.comicRepository = comicRepository;
    }

    public Rent create(Rent rent) {
        // Lưu vào bảng Rent
        RentEntity rentEntity = new RentEntity();
        BeanUtils.copyProperties(rent, rentEntity);
        Date now = new Date();
        rentEntity.setStartDate(now);
        rentEntity.setRenting(true);
        RentEntity saved = rentRepository.save(rentEntity);

        // Lưu vào bảng comic
        List<Comic> comicList = rent.getComicList();
        List<ComicEntity> comicListToSave = new ArrayList<>();
        ComicEntity comicEntity;
        for (Comic comic : comicList) {
            comicEntity = comicRepository.getById(comic.getComicId());
            comicEntity.setQuantity(comicEntity.getQuantity() - comic.getComicDetailList().size());
            comicListToSave.add(comicEntity);
        }
        comicRepository.saveAll(comicListToSave);

        ComicDetailEntity comicDetailEntity;
        RentComicDetailEntity rentComicDetailEntity;
        List<ComicDetailEntity> comicDetailEntityListToSave = new ArrayList<>();
        List<RentComicDetailEntity> rentComicDetailEntityList = new ArrayList<>();
        for (Comic comic : comicList) {
            for (ComicDetail comicDetail : comic.getComicDetailList()) {
                // Set lại bảng comic detail là đang thuê
                comicDetailEntity = comicDetailRepository.getById(comicDetail.getComicDetailId());
                comicDetailEntity.setAvailable(false);
                comicDetailEntityListToSave.add(comicDetailEntity);

                // Lưu vào bảng rent_comic_detail
                rentComicDetailEntity = new RentComicDetailEntity();
                rentComicDetailEntity.setRentId(saved.getId());
                rentComicDetailEntity.setComicDetailId(comicDetail.getComicDetailId());
                rentComicDetailEntityList.add(rentComicDetailEntity);
            }
        }
        comicDetailRepository.saveAll(comicDetailEntityListToSave);
        rentComicDetailRepository.saveAll(rentComicDetailEntityList);

        return rent;
    }

    public List<RentEntity> getReceiptByCustomerId(Long id) {
        return rentRepository.findAllByCustomerId(id);
    }

    public RentEntity getReceiptById(Long id) {
        return rentRepository.findById(id).get();
    }
}
