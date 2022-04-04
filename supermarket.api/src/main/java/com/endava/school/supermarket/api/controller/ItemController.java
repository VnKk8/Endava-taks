package com.endava.school.supermarket.api.controller;

import com.endava.school.supermarket.api.dto.ItemDto;
import com.endava.school.supermarket.api.model.Item;
import com.endava.school.supermarket.api.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto) {
        Item item = modelMapper.map(itemDto, Item.class);
        Item createdItem = itemService.createItem(item);
        return new ResponseEntity(createdItem, HttpStatus.CREATED);
    }
}
