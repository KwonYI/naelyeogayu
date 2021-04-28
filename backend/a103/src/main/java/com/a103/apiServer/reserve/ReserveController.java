package com.a103.apiServer.reserve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.buy.BuyController;
import com.a103.apiServer.model.Reserve;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

	@Autowired
	ReserveDao reserveDao;

	private static final Logger logger = LoggerFactory.getLogger(BuyController.class);

	@GetMapping(value = "list/{member_id}")
	public ResponseEntity getReserveList(@PathVariable("member_id") int memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Reserve> reserveList = reserveDao.findListReserveByMemberId(memberId);

			if (reserveList.size() != 0) {
				result.put("success", "success");
				result.put("data", reserveList);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

}
