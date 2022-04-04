package com.endava.school.supermarket.api.controller;

import com.endava.school.supermarket.api.dto.AddItemsToSupermarketDto;
import com.endava.school.supermarket.api.dto.SupermarketDto;
import com.endava.school.supermarket.api.model.Supermarket;
import com.endava.school.supermarket.api.service.SupermarketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/supermarkets")
public class SupermarketController {

    private final SupermarketService supermarketService;
    private final ModelMapper modelMapper;

    @Autowired
    public SupermarketController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
        this.modelMapper = new ModelMapper();
    }

    @PostMapping
    ResponseEntity<SupermarketDto> createSupermarket(@Valid @RequestBody SupermarketDto supermarketDto) {
        Supermarket supermarket = modelMapper.map(supermarketDto, Supermarket.class);
        Supermarket createdSupermarket = supermarketService.createSupermarket(supermarket);
        return new ResponseEntity(createdSupermarket, HttpStatus.CREATED);
    }

    @PostMapping("/addItems")
    public ResponseEntity<AddItemsToSupermarketDto> addItems(@RequestParam String supermarketId, @RequestParam List<String> itemId) {
        AddItemsToSupermarketDto addItemsToSupermarketDto = supermarketService.addItems(supermarketId, itemId);
        return new ResponseEntity<>(addItemsToSupermarketDto, HttpStatus.OK);
    }

    @GetMapping("/{supermarketId}")
    public ResponseEntity<SupermarketDto> getSupermarket(@PathVariable String supermarketId) {
        return new ResponseEntity(supermarketService.findSupermarketById(supermarketId), HttpStatus.OK);
    }
}
