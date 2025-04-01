package com.simple.ecommerce.Services;

import com.simple.ecommerce.Entity.Category;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Repository.CategoryRepository;
import com.simple.ecommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }




    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }


    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category updatedCategory) {
        Category existing = getCategoryById(id);
        existing.setTitle(updatedCategory.getTitle());
        existing.setDescription(updatedCategory.getDescription()); // ← ensure this line exists

        return categoryRepository.save(existing);
    }



    @Override
    public void deleteCategory(Long id) {
        // Check if any products in this category are part of orders
        if (productRepository.existsByCategoryIdAndInOrders(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category cannot be deleted. Products in this category are part of existing orders.");
        }

        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

        categoryRepository.deleteById(id);
    }


    @Override
    public void reorderCategories(List<Category> reordered) {
        for (int i = 0; i < reordered.size(); i++) {
            Category category = categoryRepository.findById(reordered.get(i).getId()).orElseThrow();
            category.setSortOrder(i);
            categoryRepository.save(category);
        }
    }

}