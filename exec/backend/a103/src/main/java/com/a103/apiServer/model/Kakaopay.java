package com.a103.apiServer.model;

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
public class Kakaopay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int point;

	@Column(length = 30)
	private String tid;

	@Column(name = "member_id")
	private long memberId;
}
