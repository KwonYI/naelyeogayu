package com.a103.apiServer.watchlog;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.buy.BuyController;
import com.a103.apiServer.model.Watchlog;

@RestController
@RequestMapping("/watch")
public class WatchLogController {

	@Autowired
	WatchLogDao watchlogDao;

	private static final Logger logger = LoggerFactory.getLogger(BuyController.class);

	// ip headers
	private static final List<String> IP_HEADERS = Arrays.asList("X-Forwarded-For", "Proxy-Client-IP",
			"WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR");

	@PostMapping(value = "")
	public ResponseEntity saveWatch(@RequestBody Watchlog watchlog, HttpServletRequest request) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		watchlog.setIp(getClientIpAddr(request));

		try {
			long cnt = watchlogDao.countByProductIdAndIp(watchlog.getProductId(), watchlog.getIp());
			System.out.println(cnt);
			if(cnt == 0) {
				watchlogDao.save(watchlog);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				result.put("success", "success");				
			}
			
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//ip header 검증
	public static String getClientIpAddr(HttpServletRequest request) {
		return IP_HEADERS.stream().map(request::getHeader).filter(Objects::nonNull)
				.filter(ip -> !ip.isEmpty() && !ip.equalsIgnoreCase("unknown")).findFirst()
				.orElseGet(request::getRemoteAddr);
	}
}
