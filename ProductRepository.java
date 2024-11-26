package com.mydata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mydata.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	List<Product> findByCategory(String category);
}
