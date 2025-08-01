package com.sysc.workshop.cart.repository;

import com.sysc.workshop.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    void deleteAllByCartId(UUID id);

    void deleteAllByProductId(UUID id);
}