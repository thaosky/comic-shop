package com.example.demo.model.request.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
//    @JsonFormat(pattern ="dd/MM/yyyy")
//    private Date startDate;
//
//    @JsonFormat(pattern ="dd/MM/yyyy")
//    private Date endDate;

    private Integer rentDays;
    private Long deposit;
    private Long fine;
    private Long rentalFee;
    private Long customerId;
    private List<ComicDetail> list;
}
