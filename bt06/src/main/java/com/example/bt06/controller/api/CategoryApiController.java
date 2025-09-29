package com.example.bt06.controller.api;

import com.example.bt06.entity.Category;
import com.example.bt06.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    // GET: Lấy tất cả categories
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        try {
            List<Category> categories = categoryService.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET: Lấy category theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getOne(@PathVariable Long id) {
        try {
            Optional<Category> category = categoryService.findById(id);
            return category.map(ResponseEntity::ok)
                          .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST: Tạo category mới
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        try {
            // Validate dữ liệu
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Category savedCategory = categoryService.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT: Cập nhật category
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        try {
            // Kiểm tra category có tồn tại không
            Optional<Category> existingCategory = categoryService.findById(id);
            if (existingCategory.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            // Validate dữ liệu
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            // Set ID và lưu
            category.setCategoryId(id);
            Category updatedCategory = categoryService.save(category);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE: Xóa category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            // Kiểm tra category có tồn tại không
            Optional<Category> existingCategory = categoryService.findById(id);
            if (existingCategory.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}