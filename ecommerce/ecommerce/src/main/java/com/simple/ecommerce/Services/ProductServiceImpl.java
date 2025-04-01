package com.simple.ecommerce.Services;

import com.simple.ecommerce.Entity.Category;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Repository.CategoryRepository;
import com.simple.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // marks the class as a service that deals with the logic
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;




    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Products from DB: " + products);
        return products;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }


    @Override
    public Product addProduct(Product product) {// Spring Boot with JPA/Hibernate only links to
        // existing entities it dosnt create them automatically unless we tell it to.

        //if the category is new(no ID yet in post api (addproduct)),
        // just save it before saving the product.
        if (product.getCategory().getId() == null) {
            Category savedCategory = categoryRepository.save(product.getCategory());
            product.setCategory(savedCategory);
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        // find existing product by its id
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        //updated its attributes from the new given data
        existing.setTitle(updatedProduct.getTitle());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());

        return productRepository.save(existing); // save to db
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);

    }

    @Override
    public void reorderProducts(List<Product> reordered) {
        for (int i = 0; i < reordered.size(); i++) {
            Product product = productRepository.findById(reordered.get(i).getId()).orElseThrow();
            product.setSortOrder(i); // update the setorder field according to the new index (i)
            productRepository.save(product);
        }
    }
}
