package com.example.bt06.service;

import com.example.bt06.entity.Category;
import com.example.bt06.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
    
    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
    
    @Override
    public Page<Category> findByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable);
    }
    
    @Override
    public List<Category> findByNameContaining(String name) {
        return categoryRepository.findByNameContaining(name);
    }
}