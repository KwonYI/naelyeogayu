package com.a103.apiServer.member;

import lombok.Data;

@Data
public class MemberDto {
	private String email;
	
	private String nickname;
	
	private String password;
	
	private String address;
	
	private String phone;
}
