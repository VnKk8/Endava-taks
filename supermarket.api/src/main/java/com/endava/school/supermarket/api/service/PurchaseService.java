package com.endava.school.supermarket.api.service;

import com.endava.school.supermarket.api.common.PaymentTypeEnum;
import com.endava.school.supermarket.api.dto.PurchaseDto;
import com.endava.school.supermarket.api.model.Purchase;
import com.endava.school.supermarket.api.model.PurchasePage;
import com.endava.school.supermarket.api.model.PurchaseSearchCriteria;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface PurchaseService {

    Purchase buyItemsFromSupermarket(String supermarketId, List<String> itemIds, PaymentTypeEnum typeEnum, Double cashAmount);

    Page<PurchaseDto> getAll(PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria);

    void writePurchasesToCSV(PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria, Writer writer) throws IOException;
}
