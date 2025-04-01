package com.simple.ecommerce.Services;

import com.simple.ecommerce.Entity.CartItem;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Entity.User;
import com.simple.ecommerce.Repository.CartItemRepository;
import com.simple.ecommerce.Repository.ProductRepository;
import com.simple.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addToCart(String username, Long productId, int quantity) {
        // Fetch user and product
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the item is already in the cart
        Optional<CartItem> existingItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingItem.isPresent()) {
            // If the item exists, update the quantity
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // If it doesn't exist, create a new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItem> getUserCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return cartItemRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void removeFromCart(String username, Long productId) {
        // Fetch user and product
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Find and delete the item from the cart
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                cartItemRepository.delete(item);
                break;
            }
        }
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        List<CartItem> items = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(items);
    }

    @Override
    @Transactional
    public void clearCartByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        clearCart(user);
    }
}
