package com.example.demo.model.request;

import lombok.Data;

@Data
public class ChangePassword {
    private String oldPass;
    private String newPass;

}
