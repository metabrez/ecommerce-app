package com.edu.controller;

import com.edu.model.Order;
import com.edu.model.OrderRequest;
import com.edu.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to place a new order.
     * Accessible only to authenticated users with a valid JWT.
     */
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        // Business logic and Kafka event publishing happen inside the service layer
        Order createdOrder = orderService.placeOrder(orderRequest);

        // Return 201 Created status with the order details
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    /**
     * Example of a Role-Based endpoint.
     * Accessible only to users with the 'ADMIN' role.
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}