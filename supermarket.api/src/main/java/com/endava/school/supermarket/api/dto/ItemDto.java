package com.endava.school.supermarket.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemDto {

    @NotBlank
    private String itemName;

    @NotNull
    private Double itemPrice;

    @Enumerated(EnumType.STRING)
    @NotBlank
    private String itemType;
}
