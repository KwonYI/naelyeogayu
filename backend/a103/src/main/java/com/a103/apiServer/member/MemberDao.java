package com.a103.apiServer.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a103.apiServer.model.Member;

@Repository
public interface MemberDao extends JpaRepository<Member, Long>{
	Member findMemberByEmail(String email);
	Member findMemberByEmailAndPassword(String email, String password);
	Member findMemberById(long id);
}
