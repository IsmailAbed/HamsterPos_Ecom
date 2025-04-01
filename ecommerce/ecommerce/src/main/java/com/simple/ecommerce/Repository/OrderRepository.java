package com.simple.ecommerce.Repository;

import com.simple.ecommerce.Entity.Order;
import com.simple.ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}