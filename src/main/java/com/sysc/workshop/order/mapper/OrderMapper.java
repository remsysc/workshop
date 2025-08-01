package com.sysc.workshop.order.mapper;


import com.sysc.workshop.order.dto.OrderDto;
import com.sysc.workshop.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "user.id", target = "user")
    OrderDto toDto(Order order);
    List<OrderDto> toDtoList(List<Order> order);
}
