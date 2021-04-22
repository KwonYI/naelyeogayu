package com.a103.apiServer.test;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a103.apiServer.model.Test;

@RestController
@RequestMapping("/test")
public class TestController {
	@Autowired
	TestDao testDao;
	
	@GetMapping(value="getTest")
	public void test(){
		System.out.println("ASDASD");
	}
	
	@GetMapping(value="getId/{id}")
	public Object getTestById(@PathVariable("id") int id){
		try {
			
			System.out.println(id);
			Optional<Test> testOpt = testDao.findTestById(id);
			
			
			testOpt.ifPresent(selectTest -> {
				System.out.println(selectTest);
	        });
			
		} catch (Exception e) {
			System.out.println("에러");
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getName/{name}")
	public Object getTestByName(@PathVariable("name") String name){
		try {
			Optional<Test> testOpt = testDao.findTestByName(name);
			
			testOpt.ifPresent(selectTest -> {
				System.out.println(selectTest);
	        });
			
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping(value="/getAll")
	public Object getAllTest(){
		List<Test> list = testDao.findAll();
		
		if(list == null) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		for (Test testDto : list) {
			System.out.println(testDto);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/delete/{id}")
	public Object deleteById(@PathVariable("id") int id) {		
		try {
			Optional<Test> testOpt = testDao.findTestById(id);
			
			testOpt.ifPresent(selectTest -> {
				testDao.delete(selectTest);
	        });
			
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PostMapping("/save/{name}")
	public Object save(@PathVariable("name") String name) {		
		Test dto = new Test();
		
		dto.setName(name);
		
		testDao.save(dto);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/modify/{name}/{newName}")
	public Object modify(@PathVariable("name") String name, @PathVariable("newName") String newName) {		
		try {
			Optional<Test> testOpt = testDao.findTestByName(name);
			
			testOpt.ifPresent(selectTest -> {
				
				selectTest.setName(newName);
				
				testDao.save(selectTest);
	        });
			
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
