package com.sysc.workshop.order.repository;

import com.sysc.workshop.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    boolean existsByProductId(UUID id);
}