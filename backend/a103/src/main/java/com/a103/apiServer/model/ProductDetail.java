package com.a103.apiServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
	private Product product;
	
	private int curPrice;
	
	private float discountRate;
	
	private long dDay;
}
