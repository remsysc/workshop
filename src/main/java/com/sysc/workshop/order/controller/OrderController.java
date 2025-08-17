package com.sysc.workshop.order.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.sysc.workshop.core.annotation.IsUser;
import com.sysc.workshop.core.response.ApiResponse;
import com.sysc.workshop.order.dto.OrderDto;
import com.sysc.workshop.order.service.Order.IOrderService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;

    //get order
    // delete order
    // get all orders
    @IsUser
    @GetMapping
    ResponseEntity<ApiResponse<OrderDto>> placeOrder(
        @Valid @RequestParam UUID userId
    ) {
        OrderDto order = orderService.placeOrder(userId);
        return ResponseEntity.status(CREATED).body(
            ApiResponse.success("Order successfully created!", order)
        );
    }

    // @GetMapping("/user/{userId}")
    // ResponseEntity<ApiResponse<OrderDto>> getOrderByUserId(
    //     @PathVariable UUID userId
    // ) {
    //     try {
    //         OrderDto order = orderService.getUserOrder(userId);
    //         return ResponseEntity.status(HttpStatus.OK).body(
    //             ApiResponse.success("Success", order)
    //         );
    //     } catch (OrderNotFound e) {
    //         return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
    //             ApiResponse.error(e.getMessage())
    //         );
    //     }
    // }
    // todo: add pagination
    @IsUser
    @GetMapping("/user/{userId}")
    ResponseEntity<ApiResponse<List<OrderDto>>> getOrdersByUserId(
        @Valid @PathVariable UUID userId
    ) {
        List<OrderDto> order = orderService.getUserOrders(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Success", order)
        );
    }
}
