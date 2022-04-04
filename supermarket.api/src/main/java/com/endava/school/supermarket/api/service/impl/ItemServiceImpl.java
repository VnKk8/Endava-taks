package com.endava.school.supermarket.api.service.impl;

import com.endava.school.supermarket.api.model.Item;
import com.endava.school.supermarket.api.repository.ItemRepository;
import com.endava.school.supermarket.api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item createItem(Item item) {
        return itemRepository.save(item);
    }
}
