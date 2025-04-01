package com.simple.ecommerce.Services;


import com.simple.ecommerce.Entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();


    Product addProduct(Product product);

    Product updateProduct(Long id, Product updatedProduct);

    void deleteProduct(Long id);

    void reorderProducts(List<Product> reordered);

    Product getProductById(Long id);
}
