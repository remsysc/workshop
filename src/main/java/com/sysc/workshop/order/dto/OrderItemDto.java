package com.sysc.workshop.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemDto {

    private UUID productId;
    private int quantity;
    private BigDecimal price;
}
