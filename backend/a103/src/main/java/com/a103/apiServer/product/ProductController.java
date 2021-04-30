package com.a103.apiServer.product;

import java.time.LocalDate;
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
import com.a103.apiServer.watchlog.WatchLogDao;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductDao productDao;

	@Autowired
	WatchLogDao watchLogDao;

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@PostMapping
	public ResponseEntity registerProduct(@RequestBody Product product) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			productDao.save(product);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "/expire")
	public ResponseEntity getExpireProductList() {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategory(1);
			List<Long> idList = productDao.findIdByCategory(1);

			if (productList.size() != 0) {

				try {
					List<Map> logList = watchLogDao.countWatchlogByProductId(idList);
					Map data = new HashMap<>();
					data.put("product", productList);
					data.put("watchlog", logList);
					result.put("success", "success");
					result.put("data", data);
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				} catch (Exception e) {
					logger.error("error", e);
					result.put("success", "error");
					entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
				}

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

	@GetMapping(value = "/ugly")
	public ResponseEntity getUglyProductList() {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategory(2);
			List<Long> idList = productDao.findIdByCategory(2);

			if (productList.size() != 0) {

				try {
					List<Map> logList = watchLogDao.countWatchlogByProductId(idList);
					Map data = new HashMap<>();
					data.put("product", productList);
					data.put("watchlog", logList);
					result.put("success", "success");
					result.put("data", data);
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				} catch (Exception e) {
					logger.error("error", e);
					result.put("success", "error");
					entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
				}

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

	@GetMapping(value = "/refurb")
	public ResponseEntity getRefurbProductList() {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategory(3);
			List<Long> idList = productDao.findIdByCategory(3);

			if (productList.size() != 0) {

				try {
					List<Map> logList = watchLogDao.countWatchlogByProductId(idList);
					Map data = new HashMap<>();
					data.put("product", productList);
					data.put("watchlog", logList);
					result.put("success", "success");
					result.put("data", data);
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				} catch (Exception e) {
					logger.error("error", e);
					result.put("success", "error");
					entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
				}

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

	@GetMapping(value = "/new")
	public ResponseEntity getNewProductList() {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByStartDate(LocalDate.now().plusDays(1));

			if (productList.size() != 0) {
				result.put("success", "success");
				result.put("data", productList);
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

	@DeleteMapping
	public ResponseEntity deleteProduct(@RequestHeader(value = "product_id") long productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Product product = productDao.findProductById(productId);
			productDao.delete(product);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "/detail/{product_id}")
	public ResponseEntity getDetailProduct(@PathVariable(value = "product_id") long productId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Product product = productDao.findProductById(productId);

			if (product != null) {
				result.put("data", product);
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
