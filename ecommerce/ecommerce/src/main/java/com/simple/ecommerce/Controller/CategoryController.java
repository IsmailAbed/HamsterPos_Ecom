package com.simple.ecommerce.Controller;

import com.simple.ecommerce.Entity.Category;
import com.simple.ecommerce.Entity.Product;
import com.simple.ecommerce.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Marks this class as rest controller that returns data instead of view
@RequestMapping("api/categories") //base URL path for all apis in this controller
public class CategoryController {

    @Autowired //Injects the ProductService dependency automatically, instead of creating constructor
    private CategoryService categoryService;

    @GetMapping // Public Access
    public ResponseEntity<List<Category>> getAllCategories() {
        // ResponseEntity gives us more control on what is returned (status)
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/admin") // Admin-only
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("/admin/{id}") // Admin-only
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/admin/{id}") // Admin-only
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/reorder")
    public ResponseEntity<Void> reorderCategories(@RequestBody List<Category> reordered) {
        categoryService.reorderCategories(reordered);
        return ResponseEntity.ok().build();
    }
}
