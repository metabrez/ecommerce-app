package com.edu.repo;

import com.edu.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStatusAndProductIdsIn(String status, List<Long> productIds);


    List<Order> findByStatus(String status);

    // If you want to find orders containing a specific ID within the list
    List<Order> findByProductIdsContaining(Long productId);

}
