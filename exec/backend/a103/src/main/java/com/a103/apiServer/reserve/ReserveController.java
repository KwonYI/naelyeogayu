package com.a103.apiServer.reserve;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.ProductDetail;
import com.a103.apiServer.model.Reserve;
import com.a103.apiServer.product.ProductDao;
import com.a103.apiServer.product.ProductService;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

	@Autowired
	ReserveDao reserveDao;

	@Autowired
	ProductDao productDao;
	
	@Autowired
	private ProductService productService;

	private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);

	@GetMapping(value = "/list/{member_id}")
	public ResponseEntity getReserveList(@PathVariable("member_id") int memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Reserve> reserveList = reserveDao.findListReserveByMemberId(memberId);
			
			if (reserveList.size() != 0) {
				List<Map> data = new ArrayList<>();
				
				for (Reserve reserve : reserveList) {
					Map info = new HashMap<>();
					ProductDetail product = productService.getProductDetail(reserve.getProduct(), now);
					info.put("reserve", reserve);
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

	@PostMapping("/{product_id}")
	public ResponseEntity reserveProduct(@PathVariable(value = "product_id") long productId,
			@RequestBody Reserve reserve) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			long memberId = reserve.getMemberId();
			Reserve alreadyReserve = reserveDao.findReserveByMemberIdAndProductId(memberId, productId);
			
			if(alreadyReserve == null) {
				Product product = productDao.findProductById(productId);
				reserve.setProduct(product);
				reserveDao.save(reserve);
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
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

	@DeleteMapping(value = "{member_id}/{product_id}")
	public ResponseEntity deleteReserve(@PathVariable(value = "member_id") long memberId,
			@PathVariable(value = "product_id") long productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Reserve reserve = reserveDao.findReserveByMemberIdAndProductId(memberId, productId);
			reserveDao.delete(reserve);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "/check/{product_id}/{price}")
	public ResponseEntity checkReserve(@PathVariable(value = "product_id") long productId,
			@PathVariable(value = "price") int price) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Reserve> reserveList = reserveDao.findListReserveByProductIdAndPriceLessThanEqualAndDueDateGreaterThanEqualOrderByPriceDescIdAsc(productId, price, LocalDate.now());
			
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
	
	@GetMapping(value = "/my/{member_id}/{product_id}")
	public ResponseEntity checkMyReserve(@PathVariable(value = "member_id") long memberId,
			@PathVariable(value = "product_id") long productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Reserve reserved = reserveDao.findReserveByMemberIdAndProductId(memberId, productId);
			
			if (reserved != null) {
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
