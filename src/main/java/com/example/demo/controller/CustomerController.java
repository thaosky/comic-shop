package com.example.demo.controller;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CustomerEntity>> getListCustomer(@Param("name") String name, @Param("phoneNumber") String phoneNumber) {
        List<CustomerEntity> list = customerService.findListCustomer(name, phoneNumber);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody @Valid CustomerEntity customer) throws BusinessException {
        CustomerEntity customerEntity = customerService.createCustomer(customer);
        return new ResponseEntity<>(customerEntity, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerEntity> createCustomer(@RequestBody @Valid CustomerEntity customer, @PathVariable Long id) throws BusinessException {
        CustomerEntity customerEntity = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(customerEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerEntity> deleteCustomer(@PathVariable Long id) throws BusinessException {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
