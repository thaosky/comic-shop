package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.model.request.receipt.Comic;
import com.example.demo.model.request.receipt.ComicDetail;
import com.example.demo.model.request.receipt.Rent;
import com.example.demo.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentService {
    final RentRepository rentRepository;
    final CustomerRepository customerRepository;
    final RentComicDetailRepository rentComicDetailRepository;
    final ComicDetailRepository comicDetailRepository;
    final ComicRepository comicRepository;
    final CustomerService customerService;

    public RentService(RentRepository rentRepository, CustomerRepository customerRepository, RentComicDetailRepository rentComicDetailRepository, ComicDetailRepository comicDetailRepository, ComicRepository comicRepository, CustomerService customerService) {
        this.rentRepository = rentRepository;
        this.customerRepository = customerRepository;
        this.rentComicDetailRepository = rentComicDetailRepository;
        this.comicDetailRepository = comicDetailRepository;
        this.comicRepository = comicRepository;
        this.customerService = customerService;
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
        rent.setId(saved.getId());
        return rent;
    }

    public Rent updateRent(Rent rent, Long id) {
        // Update ngày trả, trạng thái thuê vào bảng Rent
        RentEntity rentEntity = rentRepository.getById(id);
        BeanUtils.copyProperties(rent, rentEntity);
        if (!rent.getRenting()) {
            Date now = new Date();
            rentEntity.setEndDate(now);
            rentEntity.setRenting(false);
        }
        RentEntity saved = rentRepository.save(rentEntity);

        // Update
        // Lưu lại quantity vào bảng comic
        List<Comic> comicList = rent.getComicList();
        List<ComicEntity> comicListToSave = new ArrayList<>();
        ComicEntity comicEntity;
        for (Comic comic : comicList) {
            comicEntity = comicRepository.getById(comic.getComicId());
            comicEntity.setQuantity(comicEntity.getQuantity() + comic.getComicDetailList().size());
            comicListToSave.add(comicEntity);
        }
        comicRepository.saveAll(comicListToSave);

        // Update bảng detail comic đang thuê => Không thuê
        ComicDetailEntity comicDetailEntity;
        List<ComicDetailEntity> comicDetailEntityListToSave = new ArrayList<>();
        for (Comic comic : comicList) {
            for (ComicDetail comicDetail : comic.getComicDetailList()) {
                comicDetailEntity = comicDetailRepository.getById(comicDetail.getComicDetailId());
                comicDetailEntity.setAvailable(true);
                comicDetailEntityListToSave.add(comicDetailEntity);
            }
        }
        comicDetailRepository.saveAll(comicDetailEntityListToSave);

        return rent;
    }

    public List<Rent> getReceiptByCustomerId(Long customerId) {
        List<Rent> rentList = new ArrayList<>();
        Optional<CustomerEntity> optional = customerRepository.findById(customerId);
        List<RentEntity> rentEntityList = rentRepository.findRentByCustomerId(customerId);
        for (RentEntity rentEntity : rentEntityList) {
            Rent res = new Rent();
            BeanUtils.copyProperties(rentEntity, res);
            res.setCustomerEntity(optional.get());
            List<Comic> comicList = new ArrayList<>();
            List<ComicEntity> comicEntityList = comicRepository.listComicByRentId(rentEntity.getId());
            for (ComicEntity comicEntity : comicEntityList) {
                Comic comic = new Comic();
                comic.setComicId(comicEntity.getId());
                comic.setComicName(comicEntity.getName());
                List<ComicDetail> comicDetailList = new ArrayList<>();
                List<ComicDetailEntity> comicDetailEntityList = comicDetailRepository.findComicDetailByRentIdAndComicId(rentEntity.getId(), comicEntity.getId());
                for (ComicDetailEntity item : comicDetailEntityList) {
                    ComicDetail comicDetail = new ComicDetail();
                    comicDetail.setComicDetailId(item.getId());
                    comicDetail.setComicDetailCode(item.getComicDetailCode());
                    comicDetailList.add(comicDetail);
                }
                comic.setComicDetailList(comicDetailList);
                comicList.add(comic);
            }
            res.setComicList(comicList);
            rentList.add(res);
        }

        return rentList;
    }

    public Rent getReceiptById(Long rentId) {
        Rent res = new Rent();
        RentEntity rentEntity = rentRepository.findById(rentId).get();
        BeanUtils.copyProperties(rentEntity, res);
        Optional<CustomerEntity> optional = customerRepository.findById(rentEntity.getCustomerId());
        res.setCustomerEntity(optional.get());
        List<Comic> comicList = new ArrayList<>();
        List<ComicEntity> comicEntityList = comicRepository.listComicByRentId(rentId);
        for (ComicEntity comicEntity : comicEntityList) {
            Comic comic = new Comic();
            comic.setComicId(comicEntity.getId());
            comic.setComicName(comicEntity.getName());
            List<ComicDetail> comicDetailList = new ArrayList<>();
            List<ComicDetailEntity> comicDetailEntityList = comicDetailRepository.findComicDetailByRentIdAndComicId(rentId, comicEntity.getId());
            for (ComicDetailEntity item : comicDetailEntityList) {
                ComicDetail comicDetail = new ComicDetail();
                comicDetail.setComicDetailId(item.getId());
                comicDetailList.add(comicDetail);
            }
            comic.setComicDetailList(comicDetailList);
            comicList.add(comic);
        }
        res.setComicList(comicList);
        return res;
    }

    public List<Rent> getAllReceipt() {
        List<Rent> rentList = new ArrayList<>();
        List<RentEntity> rentEntityList = rentRepository.findAll();
        for (RentEntity rentEntity : rentEntityList) {
            Rent res = new Rent();
            BeanUtils.copyProperties(rentEntity, res);
            Optional<CustomerEntity> optional = customerRepository.findById(rentEntity.getCustomerId());
            res.setCustomerEntity(optional.get());
            List<Comic> comicList = new ArrayList<>();
            List<ComicEntity> comicEntityList = comicRepository.listComicByRentId(rentEntity.getId());
            for (ComicEntity comicEntity : comicEntityList) {
                Comic comic = new Comic();
                comic.setComicId(comicEntity.getId());
                comic.setComicName(comicEntity.getName());
                List<ComicDetail> comicDetailList = new ArrayList<>();
                List<ComicDetailEntity> comicDetailEntityList = comicDetailRepository.findComicDetailByRentIdAndComicId(rentEntity.getId(), comicEntity.getId());
                for (ComicDetailEntity item : comicDetailEntityList) {
                    ComicDetail comicDetail = new ComicDetail();
                    comicDetail.setComicDetailId(item.getId());
                    comicDetail.setComicDetailCode(item.getComicDetailCode());
                    comicDetailList.add(comicDetail);
                }
                comic.setComicDetailList(comicDetailList);
                comicList.add(comic);
            }
            res.setComicList(comicList);
            rentList.add(res);
        }

        return rentList;
    }
}
