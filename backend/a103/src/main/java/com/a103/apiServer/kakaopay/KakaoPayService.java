package com.a103.apiServer.kakaopay;

public interface KakaoPayService {
	public String kakaoPayReady(int point);

	public String kakaoPayApprove(String pg_token, String email);
}
