package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {
	// EmpService 연결
	private final EmpService es;
	
	@RequestMapping(value = "listEmp") // get, post mapping
	public String empList(Emp emp , String currentPage , Model model) {
		System.out.println("EmpController Start listEmp...");
		int totalEmp = es.totalEmp();
		System.out.println("EmpController totalEmp=>"+totalEmp);
		
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(1);  // 시작시 1
		emp.setEnd(10);   // 종료시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()->"+listEmp.size());
		
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		
		return "list";
	}
}
