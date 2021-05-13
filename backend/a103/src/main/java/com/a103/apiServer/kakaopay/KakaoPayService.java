package com.a103.apiServer.kakaopay;

public interface KakaoPayService {
	public String kakaoPayReady(int point, long memberId);

	public KakaoPayApprovalVO kakaoPayApprove(String pg_token);
}
