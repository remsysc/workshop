package com.sysc.workshop.order.service.Order;

import com.sysc.workshop.cart.model.Cart;
import com.sysc.workshop.cart.service.ICartService;
import com.sysc.workshop.core.constant.OrderStatus;
import com.sysc.workshop.order.dto.OrderDto;
import com.sysc.workshop.order.exception.OrderNotFound;
import com.sysc.workshop.order.mapper.CartToOrder;
import com.sysc.workshop.order.mapper.OrderMapper;
import com.sysc.workshop.order.model.Order;
import com.sysc.workshop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private  final OrderRepository orderRepository;
    private  final ICartService cartService;
    private  final CartToOrder mapper;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getOrders() {
        return orderMapper.toDtoList(orderRepository.findAll());
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        return orderMapper.toDto(orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFound("Order Not Found!")));
    }

    @Override
    public OrderDto placeOrder(UUID userId) {

        Cart cart = cartService.getCartByUserId(userId);
        Order order = mapper.toOrder(cart);
        order.setStatus(OrderStatus.PENDING);
        order.setUser(cart.getUser());
        cartService.clearCart(cart.getId());
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public void deleteOrderById(UUID orderId) {
        orderRepository.findById(orderId).ifPresentOrElse(orderRepository::delete, () ->{
            throw new OrderNotFound("Order Not Found!");
        });
    }

    @Override
    public List<OrderDto> getUserOrders(UUID userId) {
        return Optional.ofNullable(orderMapper.toDtoList( orderRepository.findALlByUserId(userId))).
                orElseThrow(()-> new OrderNotFound("Orders Not Found!"));
    }

    @Override
    public OrderDto getUserOrder(UUID userId) {
      return Optional.ofNullable(orderMapper.toDto(orderRepository.findByUserId(userId))).
               orElseThrow(()-> new OrderNotFound("Order Not Found!"));
    }
}

