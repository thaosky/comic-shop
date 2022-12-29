package com.example.demo.service;

import com.example.demo.entity.ReceiptComicEntity;
import com.example.demo.entity.ReceiptEntity;
import com.example.demo.model.request.receipt.ComicOrder;
import com.example.demo.model.request.receipt.Receipt;
import com.example.demo.repository.ReceiptComicRepository;
import com.example.demo.repository.ReceiptRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiptService {
    final ReceiptRepository receiptRepository;
    final ReceiptComicRepository receiptComicRepository;

    public ReceiptService(ReceiptRepository receiptRepository, ReceiptComicRepository receiptComicRepository) {
        this.receiptRepository = receiptRepository;
        this.receiptComicRepository = receiptComicRepository;
    }


    public Receipt create(Receipt receipt) {
        // Lưu vào bảng Receipt sau khi lưu lấy đc ID
        ReceiptEntity receiptEntity = new ReceiptEntity();
        BeanUtils.copyProperties(receipt, receiptEntity);
        ReceiptEntity saved = receiptRepository.save(receiptEntity);

        // Lưu vào bảng ReceiptComic
        List<ReceiptComicEntity> list = new ArrayList<>();
        ReceiptComicEntity receiptComic;
        for (ComicOrder item : receipt.getList()) {
            receiptComic = new ReceiptComicEntity();

            receiptComic.setReceipt_id(saved.getId());
            receiptComic.setComic_id(item.getComicId());
            receiptComic.setQuantity(item.getQuantity());

            list.add(receiptComic);
        }
        receiptComicRepository.saveAll(list);

        return receipt;
    }

    public List<ReceiptEntity> getReceiptByCustomerId(Long id) {
       return receiptRepository.findAllByCustomerId(id);
    }

    public ReceiptEntity getReceiptById(Long id) {
        return receiptRepository.findById(id).get();
    }
}
