package com.example.demo.service;

import com.example.demo.entity.RentComicEntity;
import com.example.demo.entity.RentEntity;
import com.example.demo.model.request.receipt.ComicOrder;
import com.example.demo.model.request.receipt.Receipt;
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


    public Receipt create(Receipt receipt) {
        // Lưu vào bảng Receipt sau khi lưu lấy đc ID
        RentEntity rentEntity = new RentEntity();
        BeanUtils.copyProperties(receipt, rentEntity);
        RentEntity saved = rentRepository.save(rentEntity);

        // Lưu vào bảng ReceiptComic
        List<RentComicEntity> list = new ArrayList<>();
        RentComicEntity receiptComic;
        for (ComicOrder item : receipt.getList()) {
            receiptComic = new RentComicEntity();

            receiptComic.setRentId(saved.getId());
            receiptComic.setComicId(item.getComicId());
            receiptComic.setQuantity(item.getQuantity());

            list.add(receiptComic);
        }
        rentComicRepository.saveAll(list);

        return receipt;
    }

    public List<RentEntity> getReceiptByCustomerId(Long id) {
       return rentRepository.findAllByCustomerId(id);
    }

    public RentEntity getReceiptById(Long id) {
        return rentRepository.findById(id).get();
    }
}
