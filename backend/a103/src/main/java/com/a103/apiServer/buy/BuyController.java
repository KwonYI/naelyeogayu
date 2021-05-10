package com.a103.apiServer.buy;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.member.MemberDao;
import com.a103.apiServer.model.Buy;
import com.a103.apiServer.model.Member;
import com.a103.apiServer.model.Product;
import com.a103.apiServer.product.ProductDao;

@RestController
@RequestMapping("/buy")
public class BuyController {

	@Autowired
	BuyDao buyDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	BuyService buyService;

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

	@PostMapping(value = "{product_id}")
	public ResponseEntity buyProduct(@PathVariable("product_id") long productId, @RequestBody Buy buy) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			int resultType = buyService.BuyProduct(productId, buy);
			if (resultType == 1) {
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				
				if(resultType == 3) {
					result.put("message", "재고가 부족합니다.");
					result.put("resultType", 3);
				}else if(resultType == 4) {
					result.put("message", "잔액이 부족합니다.");
					result.put("resultType", 4);
				}else {
					result.put("message", "상품 구매 중 오류가 발생했습니다.");
					result.put("resultType", 5);
				}
				
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
	
	@GetMapping(value = "verify/{member_id}/{product_id}")
	public ResponseEntity VerifyBuy(@PathVariable("member_id") int memberId, @PathVariable("product_id") int productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Buy buy = buyDao.findBuyByMemberIdAndProductId(memberId, productId);

			if (buy != null) {
				result.put("success", "success");
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