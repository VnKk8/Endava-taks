package com.endava.school.supermarket.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SupermarketDto {

    @NotBlank
    @Size(min = 2, max = 64)
    private String name;

    @NotBlank
    @Size(min = 2, max = 128)
    private String address;

    private String phoneNumber;

    private String workHours;
}
