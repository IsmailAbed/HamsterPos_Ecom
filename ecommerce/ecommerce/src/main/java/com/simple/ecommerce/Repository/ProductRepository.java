package com.simple.ecommerce.Repository;

import com.simple.ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM Product p
        JOIN OrderItem oi ON oi.product.id = p.id
        WHERE p.category.id = :categoryId
    """)
    boolean existsByCategoryIdAndInOrders(@Param("categoryId") Long categoryId);
}
