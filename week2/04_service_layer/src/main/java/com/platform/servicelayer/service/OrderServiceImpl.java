package com.platform.servicelayer.service;

import com.platform.servicelayer.entity.OrderEntity;
import com.platform.servicelayer.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // Constructor Dependency Injection (Recommended pattern from Week 1)
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderEntity placeOrder(String customerEmail, BigDecimal amount) {
        System.out.println("⚡ [OrderService] Placing order for: " + customerEmail + " | Amount: $" + amount);
        
        // Business Logic Validation
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Order amount must be greater than zero");
        }

        OrderEntity order = new OrderEntity(customerEmail, amount, "CREATED");
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrdersByCustomer(String customerEmail) {
        return orderRepository.findByCustomerEmail(customerEmail);
    }

    @Override
    @Transactional
    public OrderEntity cancelOrder(Long orderId) {
        OrderEntity order = getOrderById(orderId);
        
        if ("CANCELLED".equals(order.getStatus())) {
            throw new IllegalStateException("Order is already cancelled");
        }

        order.setStatus("CANCELLED");
        // Due to JPA Managed state inside @Transactional, changes dirty check & persist automatically!
        return order;
    }
}
