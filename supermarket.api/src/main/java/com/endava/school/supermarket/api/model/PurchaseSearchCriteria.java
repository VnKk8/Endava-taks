package com.endava.school.supermarket.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PurchaseSearchCriteria {

    private Double totalPrice;

    private Double changeAmount;

    private LocalDate timePayment;
}
