package com.simple.ecommerce.Services;

import com.simple.ecommerce.Entity.CartItem;
import com.simple.ecommerce.Entity.Order;
import com.simple.ecommerce.Entity.OrderItem;
import com.simple.ecommerce.Entity.User;
import com.simple.ecommerce.Repository.CartItemRepository;
import com.simple.ecommerce.Repository.OrderRepository;
import com.simple.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order placeOrder(String username) {
        // Fetch the user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the user's cart items
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Create the order
        Order order = new Order();
        order.setUser(user);
        double totalAmount = cartItems.stream().mapToDouble(
                item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        order.setTotalAmount(totalAmount);

        // Create order items
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setItems(orderItems);

        // Clear cart after order is placed
        cartItemRepository.deleteAll(cartItems);

        // Save the order
        return orderRepository.save(order);
    }

    // Get orders for a specific user
    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

    // Get order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
