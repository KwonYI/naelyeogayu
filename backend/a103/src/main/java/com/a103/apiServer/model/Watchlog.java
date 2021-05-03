package com.a103.apiServer.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Watchlog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "product_id")
	private long productId;
	
	@Column(name = "member_id")
	private long memberId;
	
	@Column(name = "date")
	private LocalDateTime date;
	
	@Column(name = "ip")
	private String ip;

	@Builder
	public Watchlog(long id, long productId, long memberId, LocalDateTime date, String ip) {
		super();
		this.id = id;
		this.productId = productId;
		this.memberId = memberId;
		this.date = date;
		this.ip = ip;
	}
	
	@PrePersist
	public void date() {
		this.date = LocalDateTime.now();
	}
}
