package com.a103.apiServer.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReserveDto {
	private long id;

	private long memberId;

	private Product product;

	private LocalDate dueDate;

	private int price;

	private int count;
	
}