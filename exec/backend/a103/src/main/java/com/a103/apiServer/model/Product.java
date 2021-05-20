package com.a103.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 45)
	private String name;

	@Column(name = "start_price")
	private int startPrice;

	private int category;

	@Column(name = "min_price")
	private int minPrice;

	@Column(name = "image_url", length = 200)
	private String imageUrl;

	@Column(length = 500)
	private String descript;

	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	private int stock;

	private float unit;

	@Column(name = "release_date")
	private LocalDate releaseDate;

	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	
	private int status;
	
	@Column(name = "seller_id")
	private int sellerId;
}