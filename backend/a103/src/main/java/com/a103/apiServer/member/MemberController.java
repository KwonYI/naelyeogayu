package com.a103.apiServer.member;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.Jwt.JwtService;
import com.a103.apiServer.kakaopay.KakaoPayApprovalVO;
import com.a103.apiServer.kakaopay.KakaoPayService;
import com.a103.apiServer.model.Member;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private KakaoPayService kakaoPayService;

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private String matchPassword(String email, String password) throws Exception {
		Member member = memberDao.findMemberByEmail(email);

		if (passwordEncoder.matches(password, member.getPassword())) {
			return member.getPassword();
		} else {
			return null;
		}

	}

	private boolean checkPassword(String email, String password) throws Exception {
		Member member = memberDao.findMemberByEmail(email);

		if (passwordEncoder.matches(password, member.getPassword())) {
			return true;
		}

		return false;
	}

	@PostMapping(value = "/password")
	public ResponseEntity checkPassword(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {

			if (checkPassword(member.getEmail(), member.getPassword())) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@PutMapping(value = "/password")
	public ResponseEntity changePassword(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			Member modifyUser = memberDao.findMemberByEmail(member.getEmail());
			String securePw = passwordEncoder.encode(member.getPassword());
			modifyUser.setPassword(securePw);
			memberDao.save(modifyUser);
			result.put("success", "success");
			entity = new ResponseEntity(result, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "/certify/{email}")
	public ResponseEntity emailCheck(@PathVariable("email") String email) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			Member checkEmail = memberDao.findMemberByEmail(email);

			if (checkEmail == null) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.error("error", e);
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

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

	@PutMapping(value = "/modify")
	public ResponseEntity modify(@RequestBody Member member) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			// 변경 가능한 값 => 주소, 닉네임, 비밀번호, 전화번호
			Member modifyUser = memberDao.findMemberByEmail(member.getEmail());
			modifyUser.setAddress(member.getAddress());
			modifyUser.setNickname(member.getNickname());
			modifyUser.setPhone(member.getPhone());
			memberDao.save(modifyUser);
			String token = jwtService.create(modifyUser);
			logger.trace("token", token);
			result.put("success", "success");
			result.put("x-access-token", token);
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
			String securePw = passwordEncoder.encode(member.getPassword());
			member.setPassword(securePw);
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
			String securePw = matchPassword(member.getEmail(), member.getPassword());

			if (securePw != null) {
				member.setPassword(securePw);
				Member loginUser = memberDao.findMemberByEmailAndPassword(member.getEmail(), member.getPassword());

				if (loginUser != null) {
					String token = jwtService.create(loginUser);
					logger.trace("token", token);
					result.put("success", "success");
					result.put("x-access-token", token);
					entity = new ResponseEntity(result, HttpStatus.OK);
				} else {
					result.put("success", "fail");
					entity = new ResponseEntity(result, HttpStatus.OK);
				}

			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
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

	@PostMapping(value = "/ready")
	public ResponseEntity paymentReady(@RequestBody Map<String, String> data) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			int point = Integer.valueOf(data.get("point"));
			String email = data.get("email");
			Member member = memberDao.findMemberByEmail(email);

			if (member != null) {
				String nextUrl = kakaoPayService.kakaoPayReady(point, member.getId());

				if (!nextUrl.equals("error")) {
					result.put("success", "success");
					result.put("path", nextUrl);
					entity = new ResponseEntity(result, HttpStatus.OK);
				} else {
					result.put("success", "fail");
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				}

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

	@PostMapping(value = "/approve")
	public ResponseEntity paymentApprove(@RequestBody Map<String, String> data) {
		ResponseEntity entity = null;
		Map result = new HashMap<>();

		try {
			String pg_token = data.get("pg_token");
			String email = data.get("email");
			KakaoPayApprovalVO paySuccess = kakaoPayService.kakaoPayApprove(pg_token);

			if (paySuccess != null) {
				int point = paySuccess.getAmount().getTotal();
				Member member = memberDao.findMemberByEmail(email);
				
				if(member != null) {
					member.setPoint(member.getPoint() + point);
					memberDao.save(member);
					String token = jwtService.create(member);
					logger.trace("token", token);
					result.put("success", "success");
					result.put("x-access-token", token);
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				}else {
					result.put("success", "fail");
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				}
				
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
