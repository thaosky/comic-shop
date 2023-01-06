package com.example.demo.service;

import com.example.demo.common.RoleEnum;
import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.request.ChangePassword;
import com.example.demo.model.response.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

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
        for (RoleEntity item : role) {
            if (item.getName().equals(RoleEnum.ROLE_ADMIN)) {
                throw new BusinessException("Không thể xóa tài khoản Admin");
            }
        }
        userRepository.deleteById(id);
    }

    public UserEntity getById(Long id) throws BusinessException {
        return userRepository.findById(id).orElseThrow(() -> new BusinessException("Không tìm thấy user"));
    }

    public void changePassword(ChangePassword request) throws BusinessException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        UserEntity userEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy user"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String existingPassword =request.getOldPass();
        String dbPassword       = userEntity.getPassword();

        if (passwordEncoder.matches(existingPassword, dbPassword)) {
            String save = encoder.encode(request.getNewPass());
            userEntity.setPassword(save);
            userRepository.save(userEntity);
        } else {
           throw  new BusinessException("Mật khẩu không chính xác");
        }
    }

    public UserEntity getUserById(Long id) {
        return userRepository.getById(id);
    }

    public void changePass(ChangePassword changePassword) throws BusinessException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        UserEntity userEntity = optional.orElseThrow(() -> new BusinessException("Không tìm thấy user"));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String existingPassword = changePassword.getOldPassword();
        String dbPassword = userEntity.getPassword(); // Load hashed DB password

        if (passwordEncoder.matches(existingPassword, dbPassword)) {
            System.out.println(changePassword.getNewPassword());
            String encode = encoder.encode(changePassword.getNewPassword());
            userEntity.setPassword(encode);
            userRepository.save(userEntity);
        } else {
            throw new BusinessException("Sai mật khẩu");
        }
    }
}
