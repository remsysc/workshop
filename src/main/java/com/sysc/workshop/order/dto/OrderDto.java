package com.sysc.workshop.order.dto;

import com.sysc.workshop.core.constant.OrderStatus;
import com.sysc.workshop.order.model.OrderItem;
import com.sysc.workshop.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Data
public class OrderDto {

    UUID user;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Set<OrderItem> orderItems = new HashSet<>();



}
