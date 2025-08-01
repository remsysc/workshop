package com.sysc.workshop.cart.controller;


import com.sysc.workshop.cart.exception.CartNotFoundException;
import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.cart.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final ICartService iCartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable UUID cartId){
        try {
            Cart cart = iCartService.getCart(cartId);
            return ResponseEntity.ok().body(new ApiResponse("Success", cart));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not Found!",null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable UUID cartId){
        try {
            iCartService.clearCart(cartId);
            return ResponseEntity.ok().body(new ApiResponse("success",null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable UUID cartId){
        try {
            BigDecimal totalPrice = iCartService.getTotalPrice(cartId);
            return ResponseEntity.ok().body(new ApiResponse("Total Price", totalPrice));
        } catch (Exception e) {
        return ResponseEntity.ok().body(new ApiResponse(e.getMessage(), null));
        }
    }


}
