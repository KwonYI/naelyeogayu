package com.a103.apiServer.buy;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.a103.apiServer.Jwt.JwtService;
import com.a103.apiServer.member.MemberDao;
import com.a103.apiServer.model.Buy;
import com.a103.apiServer.model.Member;
import com.a103.apiServer.model.ProductDetail;
import com.a103.apiServer.product.ProductDao;
import com.a103.apiServer.product.ProductService;

@RestController
@RequestMapping("/buy")
public class BuyController {

	@Autowired
	BuyDao buyDao;
	
	@Autowired
	MemberDao memberDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	BuyService buyService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	private ProductService productService;

	private static final Logger logger = LoggerFactory.getLogger(BuyController.class);

	@GetMapping(value = "list/{member_id}")
	public ResponseEntity getBuyList(@PathVariable("member_id") int memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Buy> buyList = buyDao.findListBuyByMemberId(memberId);

			if (buyList.size() != 0) {
				List<Map> data = new ArrayList<>();
				
				for (Buy buy : buyList) {
					Map info = new HashMap<>();
					info.put("id", buy.getId());
					info.put("buyDate", buy.getBuyDate());
					info.put("price", buy.getPrice());
					info.put("count", buy.getCount());
					info.put("memberId", buy.getMemberId());
					info.put("product", buy.getProduct());
					ProductDetail product = productService.getProductDetail(buy.getProduct(), now);
					info.put("productCurPrice", product.getCurPrice());
					info.put("productCurDiscountRate", product.getDiscountRate());
					info.put("productCurDday", product.getDDay());
					data.add(info);
				}
				
				result.put("success", "success");
				result.put("data", data);
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
	
	@GetMapping(value = "productlist/{product_id}")
	public ResponseEntity getProductSaleList(@PathVariable("product_id") int productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Buy> buyList = buyDao.findListBuyByProductId(productId);

			if (buyList.size() != 0) {
				List<Map> data = new ArrayList<>();
				
				for (Buy buy : buyList) {
					Map info = new HashMap<>();
					info.put("id", buy.getId());
					info.put("buyDate", buy.getBuyDate());
					info.put("price", buy.getPrice());
					info.put("count", buy.getCount());
					info.put("memberId", buy.getMemberId());
					info.put("product", buy.getProduct());
					ProductDetail product = productService.getProductDetail(buy.getProduct(), now);
					info.put("productCurPrice", product.getCurPrice());
					info.put("productCurDiscountRate", product.getDiscountRate());
					info.put("productCurDday", product.getDDay());
					data.add(info);
				}
				
				result.put("success", "success");
				result.put("data", data);
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
				Member member = memberDao.findMemberById(buy.getMemberId());
				String token = jwtService.create(member);
				logger.trace("token", token);
				result.put("success", "success");
				result.put("x-access-token", token);
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
	public ResponseEntity BuyVerify(@PathVariable("member_id") int memberId, @PathVariable("product_id") int productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			long cnt = buyDao.countByMemberIdAndProductId(memberId, productId);
			
			if (cnt >= 1) {
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