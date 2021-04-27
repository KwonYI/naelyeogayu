package com.a103.apiServer.member;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Member;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberDao memberDao;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@GetMapping(value = "profile/{email}")
	public ResponseEntity getMember(@PathVariable("email") String email) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();
		
		try {
			Member member = memberDao.findMemberByEmail(email);
			
			if(member != null) {
				result.put("success","success");
				result.put("data", member);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}else {
				result.put("success","fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success","error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
}
