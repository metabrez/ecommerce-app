package com.edu.service;

import com.edu.model.Order;
import com.edu.model.OrderEvent;
import com.edu.model.OrderRequest;
import com.edu.repo.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        // 1. save to database
        Order order = new Order();
        order.setProductIds(request.getProductIds());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("PLACED");
        Order saveOrder = orderRepository.save(order);

        // publish to kafka

        OrderEvent orderEvent = new OrderEvent(saveOrder.getId(), "ORDER_CREATED", saveOrder.getProductIds());
        kafkaTemplate.send("ORDER_CREATED", orderEvent);

        return saveOrder;

    }

   public List<Order> getAllOrders() {
        return orderRepository.findAll();
   }


}
