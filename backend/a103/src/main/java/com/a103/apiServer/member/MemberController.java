package com.a103.apiServer.member;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.Jwt.JwtService;
import com.a103.apiServer.model.Member;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberDao memberDao;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@DeleteMapping(value = "delete")
	public ResponseEntity delete(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			memberDao.deleteById(member.getId());

			result.put("success", "success");

			entity = new ResponseEntity(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@PostMapping(value = "/social")
	public ResponseEntity socialLogin(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			Member socialLoginUser = memberDao.findMemberByEmail(member.getEmail());

			if (socialLoginUser == null) {
				// 해당 이메일의 맴버가 없으면 회원가입 하기
				member.setPoint(0);
				socialLoginUser = memberDao.save(member);

				result.put("first", "first");
			}

			String token = jwtService.create(socialLoginUser);
			logger.trace("token", token);

			result.put("success", "success");
			result.put("x-access-token", token);
			result.put("data", socialLoginUser);

			entity = new ResponseEntity(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@PostMapping(value = "/signup")
	public ResponseEntity signup(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			member.setPoint(0);
			Member newUser = memberDao.save(member);

			result.put("success", "success");

			entity = new ResponseEntity(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@PostMapping(value = "/login")
	public ResponseEntity login(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			Member loginUser = memberDao.findMemberByEmailAndPassword(member.getEmail(), member.getPassword());

			if (loginUser != null) {
				String token = jwtService.create(loginUser);
				logger.trace("token", token);

				result.put("success", "success");
				result.put("x-access-token", token);
				result.put("data", loginUser);

				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "profile/{email}")
	public ResponseEntity getMember(@PathVariable("email") String email) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			Member member = memberDao.findMemberByEmail(email);

			if (member != null) {
				result.put("success", "success");
				result.put("data", member);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

}
