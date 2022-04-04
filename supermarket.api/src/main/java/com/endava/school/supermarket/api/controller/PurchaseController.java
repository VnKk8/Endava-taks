package com.endava.school.supermarket.api.controller;

import com.endava.school.supermarket.api.common.PaymentTypeEnum;
import com.endava.school.supermarket.api.dto.PurchaseDto;
import com.endava.school.supermarket.api.exception.CashCanNotBeNull;
import com.endava.school.supermarket.api.exception.PaymentMethodNotValid;
import com.endava.school.supermarket.api.model.Purchase;
import com.endava.school.supermarket.api.model.PurchasePage;
import com.endava.school.supermarket.api.model.PurchaseSearchCriteria;
import com.endava.school.supermarket.api.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.endava.school.supermarket.api.common.ExceptionMessages.CASH_CAN_NOT_BE_NULL;
import static com.endava.school.supermarket.api.common.ExceptionMessages.PAYMENT_METHOD_IS_NOT_VALID;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public ResponseEntity<PurchaseDto> buyItemsFromSupermarket(@RequestParam String supermarketId, @RequestParam List<String> itemsIds, @RequestParam String typeEnum, @RequestParam(required = false) Double cashAmount) {

        if (!typeEnum.equals("CASH") && !typeEnum.equals("CARD")) {
            throw new PaymentMethodNotValid(PAYMENT_METHOD_IS_NOT_VALID);
        }
        PaymentTypeEnum paymentTypeEnum = PaymentTypeEnum.valueOf(typeEnum);
        if (paymentTypeEnum == PaymentTypeEnum.CASH && cashAmount == null) {
            throw new CashCanNotBeNull(CASH_CAN_NOT_BE_NULL);
        }
        Purchase purchase = purchaseService.buyItemsFromSupermarket(supermarketId, itemsIds, paymentTypeEnum, cashAmount);
        PurchaseDto purchaseDto = new PurchaseDto(purchase.getTotalPrice(), purchase.getChangeAmount(), purchase.getTimePayment());
        return new ResponseEntity<>(purchaseDto, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Page<PurchaseDto>> getAll(PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria) {
        return ResponseEntity.ok(purchaseService.getAll(purchasePage, purchaseSearchCriteria));
    }

    @GetMapping("/csv")
    public void getAllPurchasesInCsv(HttpServletResponse servletResponse, PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"purchases.csv\"");
        this.purchaseService.writePurchasesToCSV(purchasePage, purchaseSearchCriteria, servletResponse.getWriter());
    }
}
