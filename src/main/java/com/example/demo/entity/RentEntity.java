package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "rent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate; // Ngày thuê
    private Date endDate; // Ngày trả

    private Integer rentDays;// số ngày thuê
    private Long deposit; // Tiền cọc
    private Long fine; // Tiền phạt
    private Long rentalFee; // Phí thuê
    private Long customerId;
    private Boolean renting; // true: đang thuê, false: đã trả

}
