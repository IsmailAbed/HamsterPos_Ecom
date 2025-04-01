package com.simple.ecommerce.Controller;

import com.simple.ecommerce.Entity.Order;
import com.simple.ecommerce.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place an order
    @PostMapping("/place")
    // AuthenticationPrincipal: injects the currently authenticated user details
    public Order placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        return orderService.placeOrder(userDetails.getUsername());
    }

    // Get all orders for a user
    @GetMapping
    public List<Order> getOrders(@AuthenticationPrincipal UserDetails userDetails) {
        return orderService.getUserOrders(userDetails.getUsername());
    }

    // Get order details by order ID
    @GetMapping("/{id}")
    public Order getOrderDetails(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
