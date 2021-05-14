package com.a103.apiServer.kakaopay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Kakaopay;

@Repository
public interface KakaoPayDao extends JpaRepository<Kakaopay, Long> {
	Kakaopay findKakaopayByTid(String tid);
}
