package com.example.demo.model.request.receipt;

import com.example.demo.entity.CustomerEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate; // Ngày thuê

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate; // Ngày trả
    private Integer rentDays;
    private Long deposit;
    private Long fine;
    private Long rentalFee;
    private CustomerEntity customerEntity;
    private Long customerId;
    private Boolean renting;
    private List<Comic> comicList;
}
