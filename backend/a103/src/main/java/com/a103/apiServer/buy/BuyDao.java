package com.a103.apiServer.buy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Buy;

@Repository
public interface BuyDao extends JpaRepository<Buy, Long>{
	
	List<Buy> findListBuyByMemberId(long memberId);
}