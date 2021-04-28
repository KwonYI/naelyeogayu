package com.a103.apiServer.reserve;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Reserve;

@Repository
public interface ReserveDao extends JpaRepository<Reserve, Long>{

	List<Reserve> findListReserveByMemberId(long memberId);
	Reserve findReserveByMemberIdAndProductId(long memberId, long productId);
}
