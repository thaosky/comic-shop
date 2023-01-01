package com.example.demo.service;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Page<CustomerEntity> findListCustomer(String name, String phoneNumber, Integer pageSize, Integer pageNo, String sortName, String sort) {
        Sort sortable = Sort.by("id").descending();;
        if(sortName != null && sort.equals("ASC")) {
            sortable = Sort.by(sortName).ascending();;
        } else if (sortName != null && sort.equals("DESC")) {
            sortable = Sort.by(sortName).descending();;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortable);
        if (name != null) {
            name = name.toUpperCase();
        }
        Page<CustomerEntity> k = customerRepository.listCustomer(phoneNumber, name, pageable);
        return k;
    }

    public CustomerEntity createCustomer(CustomerEntity customer) throws BusinessException {
        boolean existed = customerRepository.existsByPhoneNumber(customer.getPhoneNumber());
        if (existed) {
            throw new BusinessException("Số điện thoại đã tồn tại");
        }
        return customerRepository.save(customer);
    }

    public CustomerEntity updateCustomer(Long id, CustomerEntity customer) throws BusinessException {
        Optional<CustomerEntity> optional = customerRepository.findById(id);
        CustomerEntity customerEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
        BeanUtils.copyProperties(customer, customerEntity, "id");
        return customerRepository.save(customerEntity);
    }

    public void deleteCustomer(Long id) throws BusinessException {
        Optional<CustomerEntity> optional = customerRepository.findById(id);
        CustomerEntity customerEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
         customerRepository.deleteById(id);
    }

    public CustomerEntity getCustomerById(Long id) throws BusinessException {
        Optional<CustomerEntity> optional = customerRepository.findById(id);
        CustomerEntity customerEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
        return customerEntity;
    }

    public Page<CustomerEntity> getCustomerRenting(Integer pageSize, Integer pageNo, String sort, String sortName) {
        Sort sortable = Sort.by("id").descending();;
        if(sortName != null && sort.equals("ASC")) {
            sortable = Sort.by(sortName).ascending();;
        } else if (sortName != null && sort.equals("DESC")) {
            sortable = Sort.by(sortName).descending();;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortable);
        Page<CustomerEntity>  page = customerRepository.findCustomerRenting(pageable);
        return page;
    }
}
