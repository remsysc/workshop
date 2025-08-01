package com.sysc.workshop.cart.controller;

import com.sysc.workshop.cart.exception.CartNotFoundException;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.cart.service.ICartItemService;
import com.sysc.workshop.cart.service.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService iCartItemService;
    private final ICartService iCartService;

    //add item
    @PostMapping("item/add")
    public ResponseEntity<ApiResponse> addItem(@RequestParam(required = false) UUID cartId, @RequestParam UUID productId, @RequestParam int quantity){
        try {
            if(cartId == null){
               cartId =  iCartService.initNewCart();
            }
            iCartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok().body(new ApiResponse("Add item successfully",null));
        } catch ( CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    //delete item
    @DeleteMapping("cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItem(@PathVariable UUID cartId, @PathVariable UUID itemId){
        try {
            iCartItemService.removeItemToCart(cartId, itemId);
            return ResponseEntity.ok().body(new ApiResponse(" Remove item successfully",null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }

    }

    @PutMapping("cart/{cartId}/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable UUID cartId, @PathVariable UUID productId, @RequestParam int quantity){
        try {
            iCartItemService.updateItemQuantity(cartId,productId, quantity);
            return ResponseEntity.ok().body(new ApiResponse("Updated quantity successfully",null));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }
}
