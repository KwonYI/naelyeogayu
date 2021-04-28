package com.a103.apiServer.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long>{
	
}