package com.sysc.workshop.cart.controller;

import com.sysc.workshop.cart.dto.CartItemDTO;
import com.sysc.workshop.cart.dto.request.AddItemRequestDTO;
import com.sysc.workshop.cart.service.ICartItemService;
import com.sysc.workshop.core.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {

    private final ICartItemService iCartItemService;

    //add item
    @PostMapping
    public ResponseEntity<ApiResponse<CartItemDTO>> addItem(
        @Valid AddItemRequestDTO requestDTO
    ) {
        iCartItemService.addItemToCart(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("Add item successfully", null)
        );
    }

    //delete item
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
        @Valid @PathVariable UUID cartId,
        @Valid @RequestParam UUID itemId
    ) {
        iCartItemService.removeItemToCart(cartId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success(" Remove item successfully", null)
        );
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<Void>> updateItemQuantity(
        @Valid AddItemRequestDTO requestDTO
    ) {
        iCartItemService.updateItemQuantity(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Updated quantity successfully", null)
        );
    }
}
