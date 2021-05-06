package com.a103.apiServer.reserve;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Reserve;

@Repository
public interface ReserveDao extends JpaRepository<Reserve, Long> {

	List<Reserve> findListReserveByMemberId(long memberId);
	Reserve findReserveByMemberIdAndProductId(long memberId, long productId);
	List<Reserve> findListReserveByProductIdAndPriceLessThanEqualAndDueDateGreaterThanEqualOrderByPriceDescIdAsc(long productId, int price, LocalDate date);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE reserve r SET r.status = :newStatus WHERE r.status = :oldStatus AND r.due_date < :dueDate", nativeQuery = true)
	int updateStatusByDate(@Param("newStatus") int newStatus, @Param("oldStatus") int oldStatus, @Param("dueDate") LocalDate dueDate);
	
}
