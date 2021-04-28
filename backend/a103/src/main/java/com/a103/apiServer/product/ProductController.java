package com.a103.apiServer.product;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductDao productDao;

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
}
