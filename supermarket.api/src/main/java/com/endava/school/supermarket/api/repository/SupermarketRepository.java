package com.endava.school.supermarket.api.repository;

import com.endava.school.supermarket.api.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, String> {

    Optional<Supermarket> findById(String supermarketId);
}
