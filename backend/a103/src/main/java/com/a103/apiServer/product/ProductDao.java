package com.a103.apiServer.product;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

	Product findProductById(long id);
	long countByCategory(int category);
	long countByCategoryAndStatus(int category, int status);
	List<Product> findListProductBySellerId(int memberId);
	List<Product> findListProudctByStatusAndStartDate(int status, LocalDate startDate);
	List<Product> findListProductByStatusAndCategory(int status, int category);

	@Query(value = "SELECT * FROM product WHERE CATEGORY = :category ORDER BY STATUS, ID LIMIT :limit, :CONTENT_CNT", nativeQuery = true)
	List<Product> findListProductByCategory(@Param("category") int category, @Param("limit") int limit, @Param("CONTENT_CNT") int contentCnt);

	@Query(value = "SELECT * FROM product WHERE START_DATE >= :date ORDER BY STATUS, ID LIMIT :limit, :CONTENT_CNT", nativeQuery = true)
	List<Product> findListProductByStartDate(@Param("date") LocalDate date, @Param("limit") int limit, @Param("CONTENT_CNT") int contentCnt);

	@Query(value = "SELECT * FROM product WHERE CATEGORY = :category AND NAME Like :word ORDER BY STATUS, ID LIMIT :limit, :CONTENT_CNT", nativeQuery = true)
	List<Product> findListProductByCategoryNameContaining(@Param("category") int category, @Param("word") String word, @Param("limit") int limit, @Param("CONTENT_CNT") int contentCnt);

	@Query(value = "SELECT P.* FROM product P LEFT JOIN watchlog W ON P.ID = W.PRODUCT_ID WHERE STATUS = 0 GROUP BY P.ID HAVING COUNT(W.ID) > 0 ORDER BY COUNT(P.ID) DESC", nativeQuery = true)
	List<Product> findListProductOrderByWatchCount();

	@Query(value = "SELECT * FROM product WHERE CATEGORY = :category ORDER BY STATUS, END_DATE ASC, ID LIMIT :limit, :CONTENT_CNT", nativeQuery = true)
	List<Product> findListProductByCategoryOrderByEndDateAsc(@Param("category") int category, @Param("limit") int limit, @Param("CONTENT_CNT") int contentCnt);

	@Query(value = "SELECT * FROM product WHERE CATEGORY = :category ORDER BY STATUS, END_DATE DESC, ID LIMIT :limit, :CONTENT_CNT", nativeQuery = true)
	List<Product> findListProductByCategoryOrderByEndDateDesc(@Param("category") int category, @Param("limit") int limit, @Param("CONTENT_CNT") int contentCnt);

	@Query(value = "SELECT P.* FROM product P LEFT JOIN watchlog W ON P.ID = W.PRODUCT_ID WHERE CATEGORY = :category GROUP BY P.ID ORDER BY STATUS, COUNT(P.ID) DESC, ID LIMIT :limit, :CONTENT_CNT", nativeQuery = true)
	List<Product> findListProductByCategoryOrderByWatchCount(@Param("category") int category, @Param("limit") int limit, @Param("CONTENT_CNT") int contentCnt);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE product p SET p.status = :newStatus WHERE p.status = :oldStatus AND p.start_date = :startDate", nativeQuery = true)
	int updateStatusByDate(@Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus, @Param("startDate") LocalDate startDate);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE product p SET p.status = :newStatus WHERE p.status = :oldStatus AND p.end_date < :endDate", nativeQuery = true)
	int updateStatusUnderDate(@Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus, @Param("endDate") LocalDate endDate);
}