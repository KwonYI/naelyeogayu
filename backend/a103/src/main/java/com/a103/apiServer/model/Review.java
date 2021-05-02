package com.a103.apiServer.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "member_id")
	private long memberId;

	@Column(name = "product_id")
	private long productId;

	@Column(name = "date")
	private LocalDate date;

	@Column(length = 500)
	private String descript;

	@Column(length = 100)
	private String title;
}
