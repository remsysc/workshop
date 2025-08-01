package com.sysc.workshop.cart.service;

import com.sysc.workshop.cart.exception.CartNotFoundException;
import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.cart.repository.CartItemRepository;
import com.sysc.workshop.cart.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public Cart getCart(UUID id) {

        Cart cart = cartRepository.findById(id).orElseThrow(()-> new CartNotFoundException("Cart Not Found!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);



    }

    @Override
    public void clearCart(UUID id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cart.updateTotalAmount();

    }

    @Override
    public BigDecimal getTotalPrice(UUID id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public UUID initNewCart(){
        Cart cart = new Cart();
        return cartRepository.save(cart).getId();
    }

    @Override
    public Cart getCartByUserId(UUID userId) {

        return cartRepository.findByUserId(userId);
    }
}
