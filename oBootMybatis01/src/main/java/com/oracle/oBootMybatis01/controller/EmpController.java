package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

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
		// Emp 전체 Count      25
		int totalEmp = es.totalEmp();
		System.out.println("EmpController totalEmp=>"+totalEmp);
		
		// Paging 작업  -> 모듈화를 시켜놓음으로써 어디서든 paging 처리를 할 수 있음
		Paging page = new Paging(totalEmp, currentPage);
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(page.getStart());  // 시작시 1
		emp.setEnd(page.getEnd());  	// 종료시 10
		
		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()->"+listEmp.size());
		
		model.addAttribute("totalEmp"	, totalEmp);
		model.addAttribute("listEmp"	, listEmp);
		model.addAttribute("page"		, page);
		
		return "list";
	}
	
	// 정보 상세조회
	@GetMapping(value = "detailEmp")
	public String detailEmp(int empno , Model model) {
		System.out.println("EmpController detailEmp Start...");
		
//		1. EmpService안에 detailEmp method 선언
//		   1) parameter : empno
//		   2) Return      Emp
//
//		2. EmpDao   detailEmp method 선언 
////		                    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
		
		System.out.println("EmpController detail Start...");
		Emp emp = es.detailEmp(empno);		
		System.out.println("EmpController detailEmp emp.getEname()->"+emp.getEname());
     	
		model.addAttribute("emp" , emp);
		
		return "detailEmp";
	}
	
	// 정보 수정 조회
	@GetMapping(value = "updateFormEmp")
	public String updateFormEmp(int empno , Model model) {
		System.out.println("EmpController updateFormEmp Start...");
		
		Emp emp = es.detailEmp(empno);		
		System.out.println("EmpController updateFormEmp emp.getEname()->"+emp.getEname());
     	// 문제
		// 1. DTO  : String hiredate
		// 2. View : 단순조회 OK , JSP에서 input type="date" 문제 발생
		// 3. 해결책 : 년월일만 짤라 넣어 주어야 함
		String hiredate ="";
		if (emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10); // 10byte
			emp.setHiredate(hiredate);
		}
		System.out.println("hiredate->"+hiredate);
		
		model.addAttribute("emp" , emp);
		
		return "updateFormEmp";
	}
	
	@PostMapping(value = "updateEmp")
	public String updateEmp(Emp emp , Model model) {
		log.info("Start..."); // log는 관등성명 나옴.
		
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//   	2. EmpDao updateEmp method 선언
//                              	  mapper ID   ,    Parameter
//   	updateCount = session.update("tkEmpUpdate",	emp);
		
		int updateCount = es.updateEmp(emp);
		System.out.println("EmpController es.updateEmp updateCount->"+updateCount);
		model.addAttribute("uptCnt", updateCount);	// Test Controller간 Data 전달
		model.addAttribute("kk3" , "Message Test"); // Test Controller간 Data 전달
		
//		return "redirect:listEmp"; url만이동
		return "forward:listEmp";	// 모델을 데리고 와서 저장
	}
	
	@RequestMapping(value = "writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("EmpController writeFormEmp Start...");
		// 관리자 사번 만 Get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController list empList.size()->"+empList.size());
		
		// 부서(코드, 부서명)
		List<Dept> deptList = es.deptSelect();
		System.out.println("EmpController list deptList.size()->"+deptList.size());
		
		return "writeFormEmp";
	}
}
