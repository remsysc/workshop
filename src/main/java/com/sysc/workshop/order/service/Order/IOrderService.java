package com.sysc.workshop.order.service.Order;

import com.sysc.workshop.order.dto.OrderDto;
import java.util.List;
import java.util.UUID;

public interface IOrderService {
    //read
    //add order
    // delete
    List<OrderDto> getOrders();
    OrderDto getOrderById(UUID orderId);
    OrderDto placeOrder(UUID userId);
    void deleteOrderById(UUID orderId);
    List<OrderDto> getUserOrders(UUID userId);
    OrderDto getUserOrder(UUID userId);
}
