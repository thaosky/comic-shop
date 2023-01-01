package com.example.demo.service;

import com.example.demo.entity.RentComicEntity;
import com.example.demo.entity.RentEntity;
import com.example.demo.model.request.receipt.ComicDetail;
import com.example.demo.model.request.receipt.Rent;
import com.example.demo.repository.RentComicRepository;
import com.example.demo.repository.RentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentService {
    final RentRepository rentRepository;
    final RentComicRepository rentComicRepository;

    public RentService(RentRepository rentRepository, RentComicRepository rentComicRepository) {
        this.rentRepository = rentRepository;
        this.rentComicRepository = rentComicRepository;
    }


    public Rent create(Rent rent) {
        // Lưu vào bảng Receipt sau khi lưu lấy đc ID
        RentEntity rentEntity = new RentEntity();
        BeanUtils.copyProperties(rent, rentEntity);
        RentEntity saved = rentRepository.save(rentEntity);

        // Lưu vào bảng Rent
        List<RentComicEntity> list = new ArrayList<>();
        RentComicEntity receiptComic;
        for (ComicDetail item : rent.getList()) {
            receiptComic = new RentComicEntity();

            receiptComic.setRentId(saved.getId());
            list.add(receiptComic);
        }
        rentComicRepository.saveAll(list);

        return rent;
    }

    public List<RentEntity> getReceiptByCustomerId(Long id) {
       return rentRepository.findAllByCustomerId(id);
    }

    public RentEntity getReceiptById(Long id) {
        return rentRepository.findById(id).get();
    }
}
