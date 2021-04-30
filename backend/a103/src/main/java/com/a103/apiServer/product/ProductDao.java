package com.a103.apiServer.product;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
	
	Product findProductById(long id);
	List<Product> findListProductByCategory(int category);
	List<Product> findListProductByStartDate(LocalDate date);
	
	@Query(value = "SELECT ID FROM product WHERE CATEGORY = :category", nativeQuery=true)
	List<Long> findIdByCategory(@Param("category") int category);
}