package com.sysc.workshop.cart.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemDTO {

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String productId;
    private String cartId;
    private String userId;
}
