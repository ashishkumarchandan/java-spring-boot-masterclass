package com.platform.servicelayer.service;

import com.platform.servicelayer.entity.OrderEntity;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    OrderEntity placeOrder(String customerEmail, BigDecimal amount);
    OrderEntity getOrderById(Long orderId);
    List<OrderEntity> getOrdersByCustomer(String customerEmail);
    OrderEntity cancelOrder(Long orderId);
}
