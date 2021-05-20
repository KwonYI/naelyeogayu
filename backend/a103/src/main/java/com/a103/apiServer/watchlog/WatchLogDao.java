package com.a103.apiServer.watchlog;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Watchlog;

@Repository
public interface WatchLogDao extends JpaRepository<Watchlog, Long> {
	List<Watchlog> findListWatchlogByProductId(long productId);
	long countByProductId(long productId);
	
	@Query(value = "SELECT product_id, COUNT(PRODUCT_ID) cnt FROM watchlog WHERE PRODUCT_ID IN :IDS GROUP BY PRODUCT_ID", nativeQuery = true)
	List<Map> countWatchlogByProductId(@Param("IDS") List<Long> ids);
	
	@Query(value = "SELECT COUNT(DISTINCT(MEMBER_ID)) FROM watchlog WHERE PRODUCT_ID = :PRODUCT_ID", nativeQuery = true)
	long countWatchLogIdByProductId(@Param("PRODUCT_ID") long productId);
	long countByProductIdAndIp(long productId, String ip);
}
