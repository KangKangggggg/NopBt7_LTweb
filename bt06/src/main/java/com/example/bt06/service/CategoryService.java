package com.example.bt06.service;

import com.example.bt06.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    // CRUD methods
    List<Category> findAll();
    Page<Category> findAll(Pageable pageable);
    Optional<Category> findById(Long id);
    Category save(Category category);
    void deleteById(Long id);
    
    // Search methods
    Page<Category> findByNameContaining(String name, Pageable pageable);
    List<Category> findByNameContaining(String name);
}