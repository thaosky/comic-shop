package com.example.demo.controller;

import com.example.demo.entity.ComicEntity;
import com.example.demo.model.request.receipt.Receipt;
import com.example.demo.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    ReceiptService receiptService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Receipt> createCustomer(@RequestBody Receipt receipt ) {
        Receipt res = receiptService.create(receipt);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
