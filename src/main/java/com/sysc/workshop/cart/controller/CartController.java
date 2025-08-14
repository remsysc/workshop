package com.sysc.workshop.cart.controller;

import com.sysc.workshop.cart.dto.CartDTO;
import com.sysc.workshop.cart.service.ICartService;
import com.sysc.workshop.core.response.ApiResponse;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final ICartService iCartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse<CartDTO>> getCart(
        @Valid @PathVariable UUID cartId
    ) {
        CartDTO cart = iCartService.getCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Success", cart)
        );
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<CartDTO>> clearCart(
        @Valid @PathVariable UUID cartId
    ) {
        iCartService.clearCart(cartId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            ApiResponse.success("success", null)
        );
    }

    @GetMapping("/{cartId}/total-price")
    public ResponseEntity<ApiResponse<BigDecimal>> getTotalAmount(
        @Valid @PathVariable UUID cartId
    ) {
        BigDecimal totalPrice = iCartService.getTotalPrice(cartId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Success", totalPrice)
        );
    }
}
