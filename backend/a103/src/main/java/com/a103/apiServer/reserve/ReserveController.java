package com.a103.apiServer.reserve;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.Reserve;
import com.a103.apiServer.model.ReserveDto;
import com.a103.apiServer.product.ProductDao;

@RestController
@RequestMapping("/reserve")
public class ReserveController {

	@Autowired
	ReserveDao reserveDao;

	@Autowired
	ProductDao productDao;

	private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);

	@GetMapping(value = "list/{member_id}")
	public ResponseEntity getReserveList(@PathVariable("member_id") int memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		boolean flag = true;

		try {
			List<Reserve> reserveList = reserveDao.findListReserveByMemberId(memberId);

			if (reserveList.size() != 0) {
				List<ReserveDto> returnList = new ArrayList();

				for (Reserve reserve : reserveList) {
					ReserveDto reserveDto = new ReserveDto();

					reserveDto.setCount(reserve.getCount());
					reserveDto.setDueDate(reserve.getDueDate());
					reserveDto.setId(reserve.getId());
					reserveDto.setMemberId(reserve.getMemberId());
					reserveDto.setPrice(reserve.getPrice());

					try {
						Product product = productDao.findProductById(reserve.getProductId());
						reserveDto.setProduct(product);
					} catch (Exception e) {
						logger.error("error", e);
						result.put("success", "error");
						flag = false;
						break;
					}

					returnList.add(reserveDto);
				}

				if (flag) {
					result.put("success", "success");
					result.put("data", returnList);
				}

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

	@PostMapping
	public ResponseEntity reserveProduct(@RequestBody Reserve reserve) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			reserveDao.save(reserve);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@DeleteMapping
	public ResponseEntity deleteReserve(@RequestHeader(value = "member_id") long member_id,
			@RequestHeader(value = "product_id") long product_id) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Reserve reserve = null;
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

}
