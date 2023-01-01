package com.example.demo.controller;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.model.request.receipt.Rent;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private CustomerService customerService;

    // Thống kê các truyện được thuê nhiều nhất từ trc đến nay

    // Thống kê các truyện được thuê nhiều nhất trong tháng

    // Thống kê danh sách các truyện chưa trả


    // Thống kê danh sách các khách hàng chưa trả truyện
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CustomerEntity>> getCustomerRenting( @Param("pageSize") Integer pageSize,
                                                                    @Param("pageNo") Integer pageNo,
                                                                    @Param("sort") String sort,
                                                                    @Param("sortName") String sortName) {
        Page<CustomerEntity> res = customerService.getCustomerRenting(pageSize, pageNo, sort, sortName);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
