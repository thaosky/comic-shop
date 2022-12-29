package com.example.demo.model.request.receipt;

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
public class Receipt {
    private Date startDate;
    private Date endDate;

    private Integer rentDays;
    private Long deposit;
    private Long fine;
    private Long rentalFee;
    private String customer_id;
    private List<ComicOrder> list;
}
