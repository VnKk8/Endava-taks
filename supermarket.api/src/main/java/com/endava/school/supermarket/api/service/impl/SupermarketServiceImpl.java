package com.endava.school.supermarket.api.service.impl;

import com.endava.school.supermarket.api.dto.AddItemsToSupermarketDto;
import com.endava.school.supermarket.api.exception.ItemNotFoundException;
import com.endava.school.supermarket.api.exception.SupermarketNotFoundException;
import com.endava.school.supermarket.api.model.Item;
import com.endava.school.supermarket.api.model.Supermarket;
import com.endava.school.supermarket.api.repository.ItemRepository;
import com.endava.school.supermarket.api.repository.SupermarketRepository;
import com.endava.school.supermarket.api.service.SupermarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.endava.school.supermarket.api.common.ExceptionMessages.ITEM_NOT_FOUND;
import static com.endava.school.supermarket.api.common.ExceptionMessages.SUPERMARKET_NOT_FOUND;

@Service
public class SupermarketServiceImpl implements SupermarketService {

    private final SupermarketRepository supermarketRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public SupermarketServiceImpl(SupermarketRepository supermarketRepository, ItemRepository itemRepository) {
        this.supermarketRepository = supermarketRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Supermarket createSupermarket(Supermarket supermarket) {
        return supermarketRepository.save(supermarket);
    }

    @Override
    public AddItemsToSupermarketDto addItems(String supermarketId, List<String> itemIds) {
        Supermarket supermarket = supermarketRepository.findById(supermarketId).orElseThrow(() -> {
            throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND + " " + supermarketId);
        });
        List<String> addedItems = new ArrayList<>();
        Set<Item> supermarketItem = supermarket.getItems();

        for (String itemId : itemIds) {
            Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId).orElseThrow(() -> {
                throw new ItemNotFoundException(ITEM_NOT_FOUND + " " + itemIds);
            }));
            if (item.isPresent()) {
                Item item1 = item.get();
                supermarketItem.add(item1);
                addedItems.add(item1.getItemName());
            }
        }
        supermarketRepository.save(supermarket);
        return new AddItemsToSupermarketDto(supermarket.getId(), addedItems);
    }

    @Override
    public Optional<Supermarket> findSupermarketById(String supermarketId) {
        return Optional.ofNullable(supermarketRepository.findById(supermarketId).orElseThrow(() -> {
            throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND + " " + supermarketId);
        }));
    }
}
