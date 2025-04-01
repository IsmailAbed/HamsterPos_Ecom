package com.simple.ecommerce.Services;

import com.simple.ecommerce.Entity.CartItem;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Entity.User;

import java.util.List;

public interface CartService {
    void removeFromCart(String username, Long productId);

    void clearCart(User user);

    void clearCartByUsername(String username);
    List<CartItem> getUserCart(String username);
    void addToCart(String username, Long productId, int quantity);


}

