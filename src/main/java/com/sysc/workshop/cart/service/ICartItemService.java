package com.sysc.workshop.cart.service;

import com.sysc.workshop.cart.dto.request.AddItemRequestDTO;
import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.cart.model.CartItem;
import java.util.UUID;

public interface ICartItemService {
    void addItemToCart(AddItemRequestDTO requestDT);
    void removeItemToCart(UUID cartId, UUID productId);
    CartItem getCartItem(UUID productId, Cart cart);
    void updateItemQuantity(AddItemRequestDTO requestDTO);
}
