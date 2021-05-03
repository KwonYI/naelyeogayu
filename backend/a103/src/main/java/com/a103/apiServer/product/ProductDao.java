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

	@Query(value = "SELECT * FROM product WHERE CATEGORY = :category LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Product> findListProductByCategory(@Param("category") int category, @Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT * FROM product WHERE START_DATE = :date LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Product> findListProductByStartDate(@Param("date") LocalDate date, @Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT * FROM product WHERE NAME Like :word LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Product> findListProductByNameContaining(@Param("word") String word, @Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT * FROM product WHERE DESCRIPT Like :word LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Product> findListProductByDescriptContaining(@Param("word") String word, @Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT P.* FROM product P LEFT JOIN watchlog W ON P.ID = W.PRODUCT_ID GROUP BY P.ID HAVING COUNT(W.ID) > 0 ORDER BY COUNT(P.ID) DESC", nativeQuery = true)
	List<Product> findListProductOrderByWatchCount();

	@Query(value = "SELECT * FROM product WHERE CATEGORY = :category ORDER BY END_DATE LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Product> findListProductByCategoryOrderByEndDateDesc(@Param("category") int category, @Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT ID FROM product WHERE CATEGORY = :category LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Long> findIdByCategory(@Param("category") int category, @Param("limit") int limit, @Param("offset") int offset);

	@Query(value = "SELECT P.* FROM product P LEFT JOIN watchlog W ON P.ID = W.PRODUCT_ID WHERE CATEGORY = :category GROUP BY P.ID ORDER BY COUNT(P.ID) DESC LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<Product> findListProductByCategoryOrderByWatchCount(@Param("category") int category, @Param("limit") int limit, @Param("offset") int offset);

}