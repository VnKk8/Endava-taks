package com.endava.school.supermarket.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddItemsToSupermarketDto {

    @NotNull
    private String supermarketId;

    @NotEmpty
    private List<String> addedItems;
}
