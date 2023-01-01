package com.example.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentResponse {

    private Integer rentDays;
    private Long deposit;
    private Long fine;
    private Long rentalFee;
    private Long customerName;
    private List<Comic> comicResponse;
}
