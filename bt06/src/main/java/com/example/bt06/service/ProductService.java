package com.example.bt06.service;

import com.example.bt06.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> listProducts(String keyword, Pageable pageable);
    Product getProduct(Long id);
    Product saveProduct(Product product);
    void deleteProduct(Long id);
	Product save(Product product);
	Optional<Product> findById(Long id);
	void deleteById(Long id);
	List<Product> findAll();
}