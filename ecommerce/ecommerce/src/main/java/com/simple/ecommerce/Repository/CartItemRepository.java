package com.simple.ecommerce.Repository;

import com.simple.ecommerce.Entity.CartItem;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
