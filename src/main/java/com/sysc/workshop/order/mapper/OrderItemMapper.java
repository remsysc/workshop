package com.sysc.workshop.order.mapper;

import com.sysc.workshop.order.dto.OrderDto;
import com.sysc.workshop.order.dto.OrderItemDto;
import com.sysc.workshop.order.model.OrderItem;
import org.mapstruct.Mapper;
import com.sysc.workshop.order.model.Order;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    OrderItemDto toDto(OrderItem orderItem);
    List<OrderItemDto> toDtoList(List<OrderItem> orderItemList);


}
