package com.oracle.oBootMybatis01.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootMybatis01.domain.Member;
import com.oracle.oBootMybatis01.service.MemberJpaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberJpaController {
	// MemberJpaService 연동
	private final MemberJpaService memberJpaService;
	
	// member 생성
	@GetMapping(value = "/memberJpa/new")
	public String createForm() {
		System.out.println("MemberJpaController/memberJpa/new Start...");
		
		return "memberJpa/createMemberForm";
	}
	
	// member 저장
	@PostMapping(value = "/memberJpa/save")
	public String create(Member member) {
		System.out.println("MemberJpaController create Start...");
		System.out.println("member.getId()->"+member.getId());
		System.out.println("member.getName()->"+member.getName());
		memberJpaService.join(member);
		
		return "memberJpa/createMemberForm";
	}
	
	// members 조회
	@GetMapping(value = "/members")
	public String listMember(Model model) {
		System.out.println("MemberJpaController listMember Start...");
		List<Member> memberList = memberJpaService.getListAllMember();
		model.addAttribute("members", memberList);
		
		return "memberJpa/memberList";
	}
	
	// 수정폼으로 이동
	@GetMapping(value = "/memberJpa/memberUpdateForm")
	public String memberUpdateForm(Long id, Model model) {
		Member member = null;
		String rtnJsp = "";		
		System.out.println("MemberJpaController memberUpdateForm id->"+id);
		// Optional 목적 : 객체가 NULL Check 용이 , JAVA8버전 새롭게 출시
		Optional<Member> maybeMember = memberJpaService.findById(id);
		if (maybeMember.isPresent()) { // present -> 존재하다
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is Not NULL");
			member = maybeMember.get();
			model.addAttribute("member", member);
			rtnJsp = "memberJpa/memberModify";			
		} else { // 존재하지 않는다면
			System.out.println("MemberJpaController memberUpdateForm maybeMember Is NULL");
			model.addAttribute("message", "member가 존재하지 않으니, 입력부터 수행해주세요");
			rtnJsp = "forward:/members";
		}
		return rtnJsp;
	}
	// 수정하기
	@GetMapping(value = "/memberJpa/memberUpdate")
	public String memberUpdate(Member member, Model model) {
		System.out.println("MemberJpaController memberUpdate id->"+member.getId());
		System.out.println("MemberJpaController memberUpdate member.getName->"+member.getName());
		memberJpaService.memberUpdate(member);
		
		return "redirect:/members";
	}
}
