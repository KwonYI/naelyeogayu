package com.a103.apiServer.kakaopay;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.a103.apiServer.member.MemberController;
import com.a103.apiServer.model.Kakaopay;

@Service
public class KakaoPayServiceImpl implements KakaoPayService {

	private KakaoPayReadyVO kakaoPayReadyVO;

	private KakaoPayApprovalVO kakaoPayApprovalVO;

//	private static final String BASE_URL = "http://localhost:8080";
	private static final String BASE_URL = "http://k4a103.p.ssafy.io";

	private static final String HOST = "https://kapi.kakao.com";

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Value("${kakao.admin-key}")
	private String ADMIN_KEY;

	@Autowired
	private KakaoPayDao kakaopayDao;

	public String kakaoPayReady(int point, long memberId) {
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
		params.add("cancel_url", BASE_URL + "/myPage?pg_token=cancel");
		params.add("fail_url", BASE_URL + "/myPage?pg_token=fail");
		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
			Kakaopay kakaopay = new Kakaopay();
			kakaopay.setMemberId(memberId);
			kakaopay.setPoint(point);
			kakaopay.setTid(kakaoPayReadyVO.getTid());
			kakaopayDao.save(kakaopay);
			path = kakaoPayReadyVO.getNext_redirect_pc_url();
		} catch (Exception e) {
			logger.error("error", e);
			path = "error";
		}

		return path;
	}

	public KakaoPayApprovalVO kakaoPayApprove(String pg_token) {
		String tid = kakaoPayReadyVO.getTid();

		try {
			Kakaopay kakaopay = kakaopayDao.findKakaopayByTid(tid);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "KakaoAK " + ADMIN_KEY);
			headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("cid", "TC0ONETIME");
			params.add("tid", tid);
			params.add("partner_order_id", "1001");
			params.add("partner_user_id", "naelyeogayu");
			params.add("pg_token", pg_token);
			params.add("total_amount", Integer.toString(kakaopay.getPoint()));
			HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

			try {
				kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
			} catch (Exception e) {
				logger.error("error", e);
				kakaoPayApprovalVO = null;
			}

		} catch (Exception e) {
			logger.error("error", e);
			kakaoPayApprovalVO = null;
		}

		return kakaoPayApprovalVO;
	}
}
