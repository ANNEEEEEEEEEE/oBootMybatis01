package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;

// @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
public class EmpRestController {
	// EmpService연동
	private final EmpService es;
	
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("EmpRestController helloText Start...");
		String hello = "안녕";
		
		return hello;
	}
	
	// 객체호출
	// http://jsonviewer.stack.hu  -> JSON을 보기편하게 바꿔주는 사이트
	@RequestMapping("/sample/sendV02")
	public SampleVO sendV02(int deptno) {
		System.out.println("@RestController deptno->" +deptno);
		SampleVO vo = new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(deptno);
		
		return vo;
	}
	
	@RequestMapping("/sendV03")
	public List<Dept> sendV03() {
		System.out.println("@RestController sendV03 Start...");
		List<Dept> deptList = es.deptSelect();
		
		return deptList;
	}
	
}
