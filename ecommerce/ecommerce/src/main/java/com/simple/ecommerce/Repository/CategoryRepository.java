package com.simple.ecommerce.Repository;

import com.simple.ecommerce.Entity.Category;
import com.simple.ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
