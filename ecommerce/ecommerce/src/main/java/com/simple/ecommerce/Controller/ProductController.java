package com.simple.ecommerce.Controller;

import com.simple.ecommerce.Entity.Category;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController// Marks this class as rest controller that returns data instead of view
@RequestMapping("api/products") //base URL path for all apis in this controller
public class ProductController {

    @Autowired// Injects the ProductService dependency automatically, instead of creating constructor
    private ProductService productService;

    @GetMapping // Public Access
    // ResponseEntity gives us more control on what is returned (status)
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // will take the id from path variable:
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/admin") // Admin-only
    //will take data from body
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }


    @PutMapping("/admin/{id}") // Admin-only
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return ResponseEntity.ok(productService.updateProduct(id, updatedProduct));
    }

    @DeleteMapping("/admin/{id}") // Admin-only
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/reorder")
    public ResponseEntity<Void> reorderCategories(@RequestBody List<Product > reordered) {
        productService.reorderProducts(reordered);
        return ResponseEntity.ok().build();
    }


}


