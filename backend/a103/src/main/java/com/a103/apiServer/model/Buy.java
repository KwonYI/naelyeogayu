package com.a103.apiServer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "buy_date")
	private LocalDateTime buyDate;

	private int price;

	private int count;
	
	@Column(name = "member_id")
	private long memberId;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@PrePersist
	public void buyDate() {
		this.buyDate = LocalDateTime.now();
	}
}
