package com.sysc.workshop.cart.service;

import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.cart.model.CartItem;

import java.util.UUID;

public interface ICartItemService {
    void addItemToCart(UUID cartId, UUID productId, int quantity);
    void removeItemToCart(UUID cartId, UUID productId);
    CartItem getCartItem(UUID productId, Cart cart);
    void updateItemQuantity(UUID cartId, UUID productId, int quantity);
}
