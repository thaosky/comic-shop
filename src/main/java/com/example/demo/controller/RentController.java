package com.example.demo.controller;

import com.example.demo.model.request.receipt.Rent;
import com.example.demo.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rent")
public class RentController {

    @Autowired
    RentService rentService;


    // Thuê sách
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Rent> createReceipt(@RequestBody Rent rent) {
        Rent res = rentService.create(rent);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // Trả sách
//    @PostMapping
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public ResponseEntity<Rent> createReceipt(@RequestBody Rent rent) {
//        Rent res = rentService.create(rent);
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }


    //  Sửa hóa đơn
}
