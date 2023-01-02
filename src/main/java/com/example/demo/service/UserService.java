package com.example.demo.service;

import com.example.demo.common.RoleEnum;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.response.User;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getListUser(String userName, Integer pageSize, Integer pageNo, String sort, String sortName) {
        Sort sortable = Sort.by("id").descending();
        if (sortName != null && sort.equals("ASC")) {
            sortable = Sort.by(sortName).ascending();
        } else if (sortName != null && sort.equals("DESC")) {
            sortable = Sort.by(sortName).descending();
        }
        if (userName != null) {
            userName = userName.toUpperCase();
        }
        List<User> users = new ArrayList<>();
        List<UserEntity> userEntities;
        if (userName == null || "".equals(userName)) {
            userEntities = userRepository.findAll(sortable);
        } else {
            userEntities = userRepository.findAllByUsername(userName);
        }
        for (UserEntity entity : userEntities) {
            User user = new User();
            BeanUtils.copyProperties(entity, user);
            users.add(user);
        }

        return users;
    }

    public void deleteUser(Long id) throws BusinessException {
        UserEntity user = userRepository.getById(id);
        Set<RoleEntity> role = user.getRoles();
        for (RoleEntity item: role) {
            if (item.getName().equals(RoleEnum.ROLE_ADMIN)) {
                throw new BusinessException("Không thể xóa tài khoản Admin");
            }
        }
        userRepository.deleteById(id);
    }

    public void editUser(Long id) {
    }
}
