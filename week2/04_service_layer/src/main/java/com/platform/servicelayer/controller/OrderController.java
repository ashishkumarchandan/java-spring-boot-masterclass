package com.platform.servicelayer.controller;

import com.platform.servicelayer.entity.OrderEntity;
import com.platform.servicelayer.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // Constructor Injection of Service Layer
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(@RequestParam String email, @RequestParam BigDecimal amount) {
        OrderEntity order = orderService.placeOrder(email, amount);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getOrdersByCustomer(@RequestParam String email) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(email));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
