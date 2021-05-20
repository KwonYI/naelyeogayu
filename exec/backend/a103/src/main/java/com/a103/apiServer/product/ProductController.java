package com.a103.apiServer.product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductDao productDao;

	@Autowired
	private ProductService productService;

	private static final int CONTENT_CNT = 6;
	
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

	@GetMapping(value = "/sell/{member_id}")
	public ResponseEntity getSellList(@PathVariable(value = "member_id") int memberId) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductBySellerId(memberId);

			if (productList.size() != 0) {
				List<Map> data = new ArrayList<>();
				
				for (Product product  : productList) {
					Map productDetail = productService.getSalesRecord(product);
					data.add(productDetail);
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

	@GetMapping(value = "/size/{category}")
	public ResponseEntity getProductSizeByCategory(@PathVariable(value = "category") int category) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			long cnt = productDao.countByCategory(category);

			if (cnt != 0) {
				result.put("success", "success");
				result.put("data", cnt);
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

	@GetMapping(value = "/expire/{limit}")
	public ResponseEntity getExpireProductList(@PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByCategory(1, limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
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

	@GetMapping(value = "/ugly/{limit}")
	public ResponseEntity getUglyProductList(@PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByCategory(2, limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
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

	@GetMapping(value = "/refurb/{limit}")
	public ResponseEntity getRefurbProductList(@PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByCategory(3, limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
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

	@GetMapping(value = "/new/{limit}")
	public ResponseEntity getNewProductList(@PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByStartDate(LocalDate.now().plusDays(1), limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
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

	@DeleteMapping(value = "{product_id}")
	public ResponseEntity deleteProduct(@PathVariable(value = "product_id") long productId) {
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
		LocalDateTime now = LocalDateTime.now();

		try {
			Product product = productDao.findProductById(productId);

			if (product != null) {
				ProductDetail data = productService.getProductDetail(product, now);
				result.put("data", data);
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

	@GetMapping(value = "/search/{category}/{word}/{limit}")
	public ResponseEntity getSearchProduct(@PathVariable("category") int category, @PathVariable("word") String word,
			@PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByCategoryNameContaining(category, "%" + word + "%", limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				result.put("data", data);
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
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductOrderByWatchCount();

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				result.put("data", data);
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

	@GetMapping(value = "/sort/enddate/{option}/{category}/{limit}")
	public ResponseEntity sortByEndDate(@PathVariable("category") int category,
			@PathVariable(value = "option") int option, @PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = new ArrayList<Product>();

			if (option == 1) {
				productList = productDao.findListProductByCategoryOrderByEndDateAsc(category, limit, CONTENT_CNT);
			} else if (option == 2) {
				productList = productDao.findListProductByCategoryOrderByEndDateDesc(category, limit, CONTENT_CNT);
			}

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				result.put("data", data);
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

	@GetMapping(value = "/sort/discount/{option}/{category}/{limit}")
	public ResponseEntity sortByDiscount(@PathVariable("category") int category,
			@PathVariable(value = "option") int option, @PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			int sortType = option == 1 ? 1 : -1;
			List<Product> productList = productDao.findListProductByCategory(category, limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				Collections.sort(data, new Comparator<ProductDetail>() {
					public int compare(ProductDetail o1, ProductDetail o2) {
						int first = Integer.compare(o1.getProduct().getStatus(), o2.getProduct().getStatus());

						if (first == 0) {
							return sortType * Float.compare(o1.getDiscountRate(), o2.getDiscountRate());
						} else {
							return first;
						}

					}
				});
				result.put("data", data);
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

	@GetMapping(value = "/sort/price/{option}/{category}/{limit}")
	public ResponseEntity sortByPrice(@PathVariable("category") int category,
			@PathVariable(value = "option") int option, @PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			int sortType = option == 1 ? 1 : -1;
			List<Product> productList = productDao.findListProductByCategory(category, limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				Collections.sort(data, new Comparator<ProductDetail>() {
					public int compare(ProductDetail o1, ProductDetail o2) {
						int first = Integer.compare(o1.getProduct().getStatus(), o2.getProduct().getStatus());

						if (first == 0) {
							return sortType * Integer.compare(o1.getCurPrice(), o2.getCurPrice());
						} else {
							return first;
						}
					}
				});
				result.put("data", data);
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

	@GetMapping(value = "/sort/hot/{category}/{limit}")
	public ResponseEntity sortByHot(@PathVariable("category") int category, @PathVariable(value = "limit") int limit) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByCategoryOrderByWatchCount(category, limit, CONTENT_CNT);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				result.put("data", data);
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
	
	@GetMapping(value = "/sort/discount/onsale/{category}")
	public ResponseEntity sortOnsaleByDiscount(@PathVariable("category") int category) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByStatusAndCategory(0, category);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				Collections.sort(data, new Comparator<ProductDetail>() {
					public int compare(ProductDetail o1, ProductDetail o2) {
						return Float.compare(o2.getDiscountRate(), o1.getDiscountRate());
					}
				});
				data = data.subList(0, Math.min(data.size(), 10));
				result.put("data", data);
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

	@GetMapping(value = "/sort/price/onsale/{category}")
	public ResponseEntity sortOnsaleByPrice(@PathVariable("category") int category) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		LocalDateTime now = LocalDateTime.now();

		try {
			List<Product> productList = productDao.findListProductByStatusAndCategory(0, category);

			if (productList.size() != 0) {
				List<ProductDetail> data = new ArrayList<>();
				for (Product product : productList) {
					data.add(productService.getProductDetail(product, now));
				}
				Collections.sort(data, new Comparator<ProductDetail>() {
					public int compare(ProductDetail o1, ProductDetail o2) {
						return Integer.compare(o1.getCurPrice(), o2.getCurPrice());
					}
				});
				data = data.subList(0, Math.min(data.size(), 10));
				result.put("data", data);
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