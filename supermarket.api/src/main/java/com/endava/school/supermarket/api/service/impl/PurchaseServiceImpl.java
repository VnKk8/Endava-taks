package com.endava.school.supermarket.api.service.impl;

import com.endava.school.supermarket.api.common.PaymentTypeEnum;
import com.endava.school.supermarket.api.dto.PurchaseDto;
import com.endava.school.supermarket.api.exception.CsvFileIOException;
import com.endava.school.supermarket.api.exception.SupermarketNotFoundException;
import com.endava.school.supermarket.api.model.*;
import com.endava.school.supermarket.api.repository.PurchaseCriteriaRepository;
import com.endava.school.supermarket.api.repository.PurchaseRepository;
import com.endava.school.supermarket.api.service.PurchaseService;
import com.endava.school.supermarket.api.service.SupermarketService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.endava.school.supermarket.api.common.ExceptionMessages.CANNOT_EXPORT_FILE;
import static com.endava.school.supermarket.api.common.ExceptionMessages.SUPERMARKET_NOT_FOUND;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final SupermarketService supermarketService;
    private final PurchaseRepository purchaseRepository;
    private final ModelMapper modelMapper;
    private final PurchaseCriteriaRepository purchaseCriteriaRepository;
    private final String[] csvHeader = {"ItemID", "ChangeAmount", "TimePayment", "TotalPrice"};

    @Autowired
    public PurchaseServiceImpl(SupermarketService supermarketService, PurchaseRepository purchaseRepository, PurchaseCriteriaRepository purchaseCriteriaRepository) {
        this.supermarketService = supermarketService;
        this.purchaseRepository = purchaseRepository;
        this.modelMapper = new ModelMapper();
        this.purchaseCriteriaRepository = purchaseCriteriaRepository;
    }

    @Override
    public Purchase buyItemsFromSupermarket(String supermarketId, List<String> itemIds, PaymentTypeEnum typeEnum, Double cashAmount) {
        Optional<Supermarket> supermarketDto = Optional.ofNullable(supermarketService.findSupermarketById(supermarketId).orElseThrow(() -> {
            throw new SupermarketNotFoundException(SUPERMARKET_NOT_FOUND + " " + supermarketId);
        }));
        ;
        double totalPrice = 0;

        for (String id : itemIds) {
            for (Item item : supermarketDto.get().getItems()) {
                if (id.equals(item.getId())) {
                    totalPrice += item.getItemPrice();
                }
            }
        }
        double changeAmount = 0;
        if (typeEnum == PaymentTypeEnum.CASH) {
            changeAmount = cashAmount - totalPrice;
        }
        Purchase purchase = new Purchase();
        purchase.setTotalPrice(totalPrice);
        purchase.setChangeAmount(changeAmount);
        purchase.setTimePayment(LocalDate.now());
        return purchaseRepository.save(purchase);
    }

    @Override
    public Page<PurchaseDto> getAll(PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria) {
        Page<PurchaseDto> purchaseDtos = new PageImpl<>(purchaseCriteriaRepository.findAllWithFilters(purchasePage, purchaseSearchCriteria).stream().map(purchase1 -> modelMapper.map(purchase1, PurchaseDto.class)).collect(Collectors.toList()));
        return purchaseDtos;
    }

    @Override
    public void writePurchasesToCSV(PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria, Writer writer) throws IOException {
        Page<Purchase> purchases = purchaseCriteriaRepository.findAllWithFilters(purchasePage, purchaseSearchCriteria);
        File file = new File("purchases.csv");
        file.createNewFile();
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(csvHeader);
            for (Purchase purchase : purchases) {
                csvPrinter.printRecord(purchase.getId(), purchase.getChangeAmount(), purchase.getTimePayment(), purchase.getTotalPrice());
            }
        } catch (IOException exception) {
            throw new CsvFileIOException(CANNOT_EXPORT_FILE);
        }
    }
}
