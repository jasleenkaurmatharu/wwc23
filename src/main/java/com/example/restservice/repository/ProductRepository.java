package com.example.restservice.repository;

import com.example.restservice.entity.Product;
import com.example.restservice.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

	void save(Product product);

	List<Product> findByCategory(ProductCategory category);

	Optional<Product> findByUserId(Long id);

//	void updateOne(Long userId, Long price);

	void deleteByUserId(Long id);

//	List<Product> findAll();

}
