package com.simple.ecommerce.Controller;

import com.simple.ecommerce.Entity.CartItem;
import com.simple.ecommerce.Services.CartService;
import com.simple.ecommerce.dto.AddToCartdto;
import com.simple.ecommerce.dto.RemoveFromCartdto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {

    @Autowired
    private CartService cartService;

    // Add item to cart
    @PostMapping("/add")
    public String addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddToCartdto request) {

        cartService.addToCart(userDetails.getUsername(), request.getProductId(), request.getQuantity());
        return "Item added to cart";
    }

    // Get all items in the user's cart
    @GetMapping
    public List<CartItem> getCartItems(@AuthenticationPrincipal UserDetails userDetails) {
        return cartService.getUserCart(userDetails.getUsername());
    }

    // Remove specific item from cart
    @DeleteMapping("/remove")
    public String removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RemoveFromCartdto request) {

        cartService.removeFromCart(userDetails.getUsername(), request.getProductId());
        return "Item removed from cart";
    }

    // Clear all items from cart
    @DeleteMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.clearCartByUsername(userDetails.getUsername());
        return "Cart cleared";
    }
}
