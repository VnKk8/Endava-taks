package com.endava.school.supermarket.api.service;

import com.endava.school.supermarket.api.dto.AddItemsToSupermarketDto;
import com.endava.school.supermarket.api.model.Supermarket;

import java.util.List;
import java.util.Optional;

public interface SupermarketService {
    Supermarket createSupermarket(Supermarket supermarket);

    AddItemsToSupermarketDto addItems(String supermarketId, List<String> itemIds);

    Optional<Supermarket> findSupermarketById(String supermarketId);
}
