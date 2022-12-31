package com.example.demo.controller;

import com.example.demo.model.request.receipt.Receipt;
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

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Receipt> createReceipt(@RequestBody Receipt receipt ) {
        Receipt res = rentService.create(receipt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
