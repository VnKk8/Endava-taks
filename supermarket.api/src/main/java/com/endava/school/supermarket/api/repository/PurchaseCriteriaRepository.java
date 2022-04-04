package com.endava.school.supermarket.api.repository;

import com.endava.school.supermarket.api.exception.ItemNotFoundException;
import com.endava.school.supermarket.api.model.Purchase;
import com.endava.school.supermarket.api.model.PurchasePage;
import com.endava.school.supermarket.api.model.PurchaseSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.endava.school.supermarket.api.common.ExceptionMessages.PURCHASES_NOT_FOUND_WITH_CRITERIA;

@Repository
public class PurchaseCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public PurchaseCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Purchase> findAllWithFilters(PurchasePage purchasePage, PurchaseSearchCriteria purchaseSearchCriteria) {

        CriteriaQuery<Purchase> criteriaQuery = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> purchaseRoot = criteriaQuery.from(Purchase.class);
        Predicate predicate = getPredicate(purchaseSearchCriteria, purchaseRoot);
        criteriaQuery.where(predicate);
        setOrder(purchasePage, criteriaQuery, purchaseRoot);
        TypedQuery<Purchase> typedQuery = entityManager.createQuery(criteriaQuery);
        int totalRows = typedQuery.getResultList().size();
        if (totalRows == 0) {
            throw new ItemNotFoundException(PURCHASES_NOT_FOUND_WITH_CRITERIA);
        }
        typedQuery.setFirstResult(purchasePage.getPageNumber() * purchasePage.getPageSize());
        typedQuery.setMaxResults(purchasePage.getPageSize());

        Pageable pageable = getPageable(purchasePage);

        return new PageImpl<>(typedQuery.getResultList(), pageable, totalRows);
    }

    private Predicate getPredicate(PurchaseSearchCriteria purchaseSearchCriteria, Root<Purchase> purchaseRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(purchaseSearchCriteria.getTotalPrice())) {
            predicates.add(criteriaBuilder.like(purchaseRoot.get("totalPrice").as(String.class), "%" + purchaseSearchCriteria.getTotalPrice() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PurchasePage purchasePage, CriteriaQuery<Purchase> criteriaQuery, Root<Purchase> bookRoot) {
        if (purchasePage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(bookRoot.get(purchasePage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get(purchasePage.getSortBy())));
        }
    }

    private Pageable getPageable(PurchasePage purchasePage) {
        Sort sort = Sort.by(purchasePage.getSortDirection(), purchasePage.getSortBy());
        return PageRequest.of(purchasePage.getPageNumber(), purchasePage.getPageSize(), sort);
    }
}
