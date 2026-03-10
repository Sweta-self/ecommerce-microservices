package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{productId}/{quantity}")
    public Order createOrders(
            @PathVariable Long productId,
            @PathVariable int quantity
    ){
       return orderService.createOrder(productId,quantity);
    }


}
