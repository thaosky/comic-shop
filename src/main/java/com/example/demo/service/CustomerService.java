package com.example.demo.service;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerEntity> findListCustomer(String name, String phoneNumber) {
        return customerRepository.findAll();
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
}
