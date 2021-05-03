package com.a103.apiServer.product;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
	
	@GetMapping(value = "/size/{category}")
	public ResponseEntity getProductSizeByCategory(@PathVariable(value = "category") int category) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		
		try {
			long cnt = productDao.countByCategory(category);
			
			if(cnt != 0) {
				result.put("success", "success");
				result.put("data", cnt);
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

	@GetMapping(value = "/expire")
	public ResponseEntity getExpireProductList(@RequestHeader(value = "limit") int limit,
			@RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategory(1, limit, offset);
			List<Long> idList = productDao.findIdByCategory(1, limit, offset);

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
	public ResponseEntity getUglyProductList(@RequestHeader(value = "limit") int limit,
			@RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategory(2, limit, offset);
			List<Long> idList = productDao.findIdByCategory(2, limit, offset);

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
	public ResponseEntity getRefurbProductList(@RequestHeader(value = "limit") int limit,
			@RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategory(3, limit, offset);
			List<Long> idList = productDao.findIdByCategory(3, limit, offset);

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
	public ResponseEntity getNewProductList(@RequestHeader(value = "limit") int limit,
			@RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByStartDate(LocalDate.now().plusDays(1), limit,
					offset);

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

	@GetMapping(value = "/search/{option}/{word}")
	public ResponseEntity getSearchProduct(@PathVariable("option") int option, @PathVariable("word") String word,
			@RequestHeader(value = "limit") int limit, @RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = new ArrayList<Product>();

			if (option == 1) {
				productList = productDao.findListProductByNameContaining("%" + word + "%", limit, offset);
			} else if (option == 2) {
				productList = productDao.findListProductByDescriptContaining("%" + word + "%", limit, offset);
			}

			if (productList != null) {
				result.put("data", productList);
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

	@GetMapping(value = "/hot")
	public ResponseEntity getHotProductList() {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductOrderByWatchCount();

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

	@GetMapping(value = "/sort/end/{category}")
	public ResponseEntity sortByEndDate(@PathVariable("category") int category,
			@RequestHeader(value = "limit") int limit, @RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategoryOrderByEndDateDesc(category, limit, offset);
			List<Long> idList = productDao.findIdByCategory(category, limit, offset);

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

	@GetMapping(value = "/sort/discount/{category}")
	public ResponseEntity sortByDiscount(@PathVariable("category") int category) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		return entity;
	}

	@GetMapping(value = "/sort/hot/{category}")
	public ResponseEntity sortByHot(@PathVariable("category") int category, @RequestHeader(value = "limit") int limit,
			@RequestHeader(value = "offset") int offset) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			List<Product> productList = productDao.findListProductByCategoryOrderByWatchCount(category, limit, offset);
			List<Long> idList = productDao.findIdByCategory(category, limit, offset);

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

	@GetMapping("/test")
	public void getCurPrice(int id) {
		Product product = productDao.findProductById(id);
		int startPrice = product.getStartPrice();
		int endPrice = product.getMinPrice();
		LocalDate startDate = product.getStartDate();
		LocalDate endDate = product.getEndDate();

		float daydiff = ChronoUnit.DAYS.between(startDate, endDate);
		float priceDiff = startPrice - endPrice;

		float discountRate = priceDiff / daydiff;

		System.out.println(id);
		System.out.println(startPrice);
		System.out.println(endPrice);
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(daydiff);
		System.out.println(discountRate);
		System.out.println(priceDiff);
		System.out.println(discountRate * (daydiff - 1));
	}

}
