package com.sysc.workshop.order.mapper;

import com.sysc.workshop.cart.model.CartItem;
import com.sysc.workshop.order.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemToOrderItem {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "unitPrice", target = "price")
    @Mapping(target = "order", ignore = true)
    @Mapping(source = "product.id", target = "product.id")
    OrderItem toOrderItem(CartItem item);
}
