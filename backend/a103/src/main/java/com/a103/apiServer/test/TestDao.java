package com.a103.apiServer.test;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Test;

@Repository
public interface TestDao extends JpaRepository<Test, Long>{
	
	Optional<Test> findTestById(int id);
	Optional<Test> findTestByName(String name);
}