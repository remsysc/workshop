package com.sysc.workshop.cart.service;

import com.sysc.workshop.cart.dto.CartDTO;
import com.sysc.workshop.cart.model.Cart;
import java.math.BigDecimal;
import java.util.UUID;

public interface ICartService {
    //add, remove, update
    CartDTO getCart(UUID id);
    Cart getCartByEntity(UUID id);
    void clearCart(UUID id);
    BigDecimal getTotalPrice(UUID id);

    UUID initNewCart();

    Cart getCartByUserId(UUID userId);
}
