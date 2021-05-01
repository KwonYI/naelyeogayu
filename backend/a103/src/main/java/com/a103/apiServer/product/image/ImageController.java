package com.a103.apiServer.product.image;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

	public static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ImageService imageService;

	@PostMapping("/uploadImage")
	public ResponseEntity boardImageUpload(@RequestBody MultipartFile files) throws IOException {
		Map result = new HashMap<>();
		ResponseEntity entity = null;

		try {
			String imgPath = imageService.upload(files);
			result.put("success", "success");
			result.put("data", imgPath);
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
}
