package com.example.demo.controller;

import com.example.demo.entity.CustomerEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.BusinessException;
import com.example.demo.model.request.ChangePassword;
import com.example.demo.model.response.MessageResponse;
import com.example.demo.model.response.User;
import com.example.demo.service.CustomerService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getListUser(@Param("userName") String userName,
                                                  @Param("pageSize") Integer pageSize,
                                                  @Param("pageNo") Integer pageNo,
                                                  @Param("sort") String sort,
                                                  @Param("sortName") String sortName
    ) {
        List<User> list = userService.getListUser(userName, pageSize, pageNo, sort, sortName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // Chỉ xóa user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws BusinessException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Chỉ xóa user
    @PostMapping("/change-password")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> changePass(@RequestBody ChangePassword changePassword) throws BusinessException {
        userService.changePass(changePassword);
        return ResponseEntity.ok(new MessageResponse("Thay đổi password thành công!"));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) throws BusinessException {
        UserEntity res = userService.getById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> editUser(@RequestBody UserEntity user) throws BusinessException {
        UserEntity res = userService.editUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
