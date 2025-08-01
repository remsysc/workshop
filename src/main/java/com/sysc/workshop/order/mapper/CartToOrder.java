package com.sysc.workshop.order.mapper;

import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.order.model.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = CartItemToOrderItem.class)
public interface CartToOrder {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "cartItems",      target = "orderItems")
    Order toOrder(Cart cart);

    @AfterMapping
    default void calculateTotal(Cart cart, @MappingTarget Order order){
        BigDecimal total = cart.getCartItems().stream().map(i -> i.getUnitPrice().
                multiply(BigDecimal.valueOf(i.getQuantity()))).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
    }

    @AfterMapping
    default void linkItems(@MappingTarget Order order) {
        order.getOrderItems().forEach(item -> item.setOrder(order));
    }
}
