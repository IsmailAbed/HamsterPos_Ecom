package com.simple.ecommerce.Services;

import com.simple.ecommerce.Entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category addCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);

    void reorderCategories(List<Category> categories);
}
