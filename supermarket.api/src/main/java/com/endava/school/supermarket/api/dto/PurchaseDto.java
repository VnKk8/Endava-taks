package com.endava.school.supermarket.api.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseDto {

    private Double totalPrice;

    private Double changeAmount;

    private LocalDate timePayment;
}
