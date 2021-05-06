package com.a103.apiServer.product;

import java.time.LocalDateTime;

import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.ProductDetail;

public interface ProductService {
	public ProductDetail getProductDetail(Product product, LocalDateTime now);
}