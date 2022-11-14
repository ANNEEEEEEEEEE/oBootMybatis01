package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {
	
	private final EntityManager em;
	
	@Override
	public Member Save(Member member) {
		System.out.println("MemberJpaRepositoryImpl Save Start...");
		em.persist(member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpl findAll Start...");
		List<Member> memberList = em.createQuery("select m from Member m", Member.class)
									.getResultList();
		return memberList;
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		System.out.println("MemberJpaRepositoryImpl findById Start...");
		Member member = em.find(Member.class, memberId);
		
		return Optional.ofNullable(member);
	}

	@Override
	public void updateByMember(Member member) {
		System.out.println("MemberJpaRepositoryImpl updateByMember Start...");
		// 1. 영송석 관리 X --> Setter 저장 불가
		// 2. merge,     --> 현재 Setting된것만 수정 , 나머지는 NULL
		em.merge(member);
		
		return;		
	}

}
