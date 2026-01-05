package com.edu.service;

import com.edu.model.InventoryResponse;
import com.edu.model.Order;
import com.edu.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderResponseConsumer {

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "inventory_response_topic", groupId = "order-group")
    public void handleInventoryResponse(InventoryResponse response) {
        if ("FAILED".equals(response.getStatus())) {
            Order order = orderRepository.findById(response.getOrderId()).orElseThrow();
            order.setStatus("CANCELED");
            orderRepository.save(order);
            System.out.println("Order #" + response.getOrderId() + " has been CANCELED due to inventory failure.");
        }
    }
}
