package com.example.bt06.controller.api;

import com.example.bt06.entity.Product;
import com.example.bt06.entity.Category;
import com.example.bt06.service.ProductService;
import com.example.bt06.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // GET: Lấy tất cả products
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        try {
            List<Product> products = productService.findAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET: Lấy product theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        try {
            Optional<Product> product = productService.findById(id);
            return product.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // POST: Tạo product mới
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            // Validate dữ liệu
            if (product.getName() == null || product.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Validate category
            if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Kiểm tra category có tồn tại không
            Optional<Category> category = categoryService.findById(product.getCategory().getCategoryId());
            if (category.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Set category và lưu product
            product.setCategory(category.get());
            Product savedProduct = productService.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // PUT: Cập nhật product
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            // Kiểm tra product có tồn tại không
            Optional<Product> existingProduct = productService.findById(id);
            if (existingProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            // Validate dữ liệu
            if (product.getName() == null || product.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Validate category
            if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Kiểm tra category có tồn tại không
            Optional<Category> category = categoryService.findById(product.getCategory().getCategoryId());
            if (category.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            
            // Set ID, category và lưu
            product.setProductId(id);
            product.setCategory(category.get());
            Product updatedProduct = productService.save(product);
            return ResponseEntity.ok(updatedProduct);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DELETE: Xóa product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            // Kiểm tra product có tồn tại không
            Optional<Product> existingProduct = productService.findById(id);
            if (existingProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}