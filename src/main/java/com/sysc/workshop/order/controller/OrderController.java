package com.sysc.workshop.order.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.order.dto.OrderDto;
import com.sysc.workshop.order.exception.OrderNotFound;
import com.sysc.workshop.order.service.Order.IOrderService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    //get order
    // delete order
    // get all orders

    @PostMapping("/")
    ResponseEntity<ApiResponse> placeOrder(@RequestParam UUID userId) {
        try {
            OrderDto order = orderService.placeOrder(userId);
            return ResponseEntity.status(CREATED).body(
                new ApiResponse("Order successfully created!", order)
            );
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/{orderId}")
    ResponseEntity<ApiResponse> getOrderById(@PathVariable UUID orderId) {
        try {
            OrderDto order = orderService.getOrderById(orderId);
            return ResponseEntity.status(200).body(
                new ApiResponse("Success", order)
            );
        } catch (OrderNotFound e) {
            return ResponseEntity.status(404).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<ApiResponse> getOrderByUserId(@PathVariable UUID userId) {
        try {
            OrderDto order = orderService.getUserOrder(userId);
            return ResponseEntity.status(200).body(
                new ApiResponse("Success", order)
            );
        } catch (OrderNotFound e) {
            return ResponseEntity.status(404).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/user/orders/{userId}")
    ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable UUID userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.status(200).body(
                new ApiResponse("Success", order)
            );
        } catch (OrderNotFound e) {
            return ResponseEntity.status(404).body(
                new ApiResponse(e.getMessage(), null)
            );
        }
    }
}
