package com.a103.apiServer.product;

import java.time.LocalDateTime;
import java.util.Map;

import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.ProductDetail;

public interface ProductService {
	public ProductDetail getProductDetail(Product product, LocalDateTime now);
	public Map getSalesRecord(Product product);
}