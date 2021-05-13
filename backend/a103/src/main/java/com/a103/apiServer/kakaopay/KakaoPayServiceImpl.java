package com.a103.apiServer.kakaopay;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.a103.apiServer.member.MemberController;
import com.a103.apiServer.model.kakaopay.KakaoPayReadyVO;

@Service
public class KakaoPayServiceImpl implements KakaoPayService {

	private KakaoPayReadyVO kakaoPayReadyVO;

	private static final String BASE_URL = "http://localhost:8080";
//	private static final String BASE_URL = "http://k4a103.p.ssafy.io";

	private static final String HOST = "https://kapi.kakao.com";

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Value("${kakao.admin-key}")
	private String ADMIN_KEY;

	public String kakaoPayReady(int point) {
		String path = null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "naelyeogayu");
		params.add("item_name", "포인트 충전");
		params.add("quantity", "1");
		params.add("total_amount", Integer.toString(point));
		params.add("tax_free_amount", "0");
		params.add("approval_url", BASE_URL + "/myPage");
		params.add("cancel_url", BASE_URL + "/cancel");
		params.add("fail_url", BASE_URL + "/fail");
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
			logger.info("", kakaoPayReadyVO);
			path = kakaoPayReadyVO.getNext_redirect_pc_url();
		} catch (Exception e) {
			logger.error("error", e);
			path = "error";
		}

		return path;
	}
	
	public String kakaoPayApprove(String pg_token, String email) {
		String path = null;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("tid", "1001");
		params.add("partner_order_id", "1001");
		params.add("partner_user_id", "naelyeogayu");
		params.add("item_name", "포인트 충전");
		params.add("quantity", "1");
		params.add("tax_free_amount", "0");
		params.add("approval_url", BASE_URL + "/myPage");
		params.add("cancel_url", BASE_URL + "/cancel");
		params.add("fail_url", BASE_URL + "/fail");
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayReadyVO.class);
			logger.info("", kakaoPayReadyVO);
			path = kakaoPayReadyVO.getNext_redirect_pc_url();
		} catch (Exception e) {
			logger.error("error", e);
			path = "error";
		}

		return path;
	}

}
