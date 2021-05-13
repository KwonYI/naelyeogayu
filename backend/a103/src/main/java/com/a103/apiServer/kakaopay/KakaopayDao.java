package com.a103.apiServer.kakaopay;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a103.apiServer.model.Kakaopay;

public interface KakaopayDao extends JpaRepository<Kakaopay, Long> {
	Kakaopay findKakaopayByTid(String tid);
}
