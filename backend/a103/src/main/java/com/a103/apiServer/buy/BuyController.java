package com.a103.apiServer.buy;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Buy;

@RestController
@RequestMapping("/buy")
public class BuyController {

	@Autowired
	BuyDao buyDao;

	private static final Logger logger = LoggerFactory.getLogger(BuyController.class);

	@GetMapping(value = "list/{member_id}")
	public ResponseEntity getBuyList(@PathVariable("member_id") int memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Buy> buyList = buyDao.findListBuyByMemberId(memberId);
			
			if (buyList.size() != 0) {
				result.put("success", "success");
				result.put("data", buyList);
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