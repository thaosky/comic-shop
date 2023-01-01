package com.example.demo.controller;

import com.example.demo.entity.RentEntity;
import com.example.demo.model.request.receipt.Rent;
import com.example.demo.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Update phiếu thuê sách
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Rent> createReceipt(@RequestBody Rent rent, @PathVariable Long id) {
        Rent res = rentService.updateRent(rent, id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    // Get all hóa đơn
    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Rent>> getAllReceipt() {
        List<Rent> res = rentService.getAllReceipt();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Get hóa đơn by rent id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Rent> getReceiptById(@PathVariable Long id) {
        Rent res = rentService.getReceiptById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Get hóa đơn by customer id
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Rent>> getReceiptByCustomerId(@PathVariable Long customerId) {
        List<Rent> res = rentService.getReceiptByCustomerId(customerId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
