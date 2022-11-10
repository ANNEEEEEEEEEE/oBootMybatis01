package com.oracle.oBootMybatis01.controller;

import java.util.List;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
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
	private final JavaMailSender mailSender;  // 메일전송
	
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
	
	// 일반 정보 수정
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
	
	// 관리자 수정
	@RequestMapping(value = "writeFormEmp")
	public String writeFormEmp(Model model) {
		System.out.println("EmpController writeFormEmp Start...");
		// 관리자 사번 만 Get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeForm empList.size()->"+empList.size());
		model.addAttribute("empMngList",empList); // emp Manager List
		
		// 부서(코드, 부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList); // dept
 		System.out.println("EmpController writeForm deptList.size()->"+deptList.size());
		
		return "writeFormEmp";
	}
	
	// 직원 등록 (저장은 Post)
	@PostMapping(value = "writeEmp")
	public String writeEmp(Emp emp , Model model) {
		System.out.println("EmpController writeEmp Start...");
		// Service , DAO , Mapper명[insertEmp] 까지 -> insert
		// 실패시 --> writeFormEmp
		int result = es.insertEmp(emp);
		if (result > 0) return "redirect:listEmp";
		else {
			model.addAttribute("msg","입력 실패 확인해 보세요" );
			
			return "forward:writeFormEmp";
		}
	}
	
	// 사번 중복확인
	@GetMapping(value = "confirm")
	public String confirm(int empno, Model model) {
		Emp emp = es.detailEmp(empno);
		model.addAttribute("empno", empno);
		if (emp != null) {
			System.out.println("EmpController confirm 중복된 사번...");
			model.addAttribute("msg", "중복된 사번입니다");
			return "forward:writeFormEmp";
		} else {
			System.out.println("EmpController confirm 사용 가능한 사번...");
			model.addAttribute("msg", "사용 가능한 사번입니다");
			return "forward:writeFormEmp";
		}
	}
	
	// 정보 삭제
	@GetMapping(value = "deleteEmp")
	public String deleteEmp(int empno , Model model) {
		// Controller -->  deleteEmp    1.parameter : empno
		//     name -> Service, dao , mapper
		// return -> listEmp
		System.out.println("EmpController deleteEmp Strat...");
		int result = es.deleteEmp(empno);
		
		return "redirect:listEmp";
	}
	
	// 직원부서조회
	@GetMapping(value = "listEmpDept")
	public String listEmpDept(Model model) {
		System.out.println("EmpController listEmpDept Strat...");
		// Service ,DAO -> listEmpDept
		// Mapper만 ->tkListEmpDept
		List<EmpDept> listEmpDept = es.listEmpDept();
		System.out.println("EmpController writeForm listEmpDept.size()->"+listEmpDept.size());
		
		model.addAttribute("listEmpDept" , listEmpDept);
		
		return "listEmpDept";
	}
	
	// mail 전송
	@RequestMapping(value = "mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		System.out.println("mailSending...");
		String tomail = "jmyjj1215@gmail.com";		// 받는사람 이메일
		System.out.println(tomail);
		String setfrom = "rlawlgus8649@naver.com";	// 보내는사람 이메일, yml에 등록된 사람으로 메일전송
		String title = "mailTransport 입니다";		// 제목
		
		try {
			// Mime -> 전자우편 Internet 표준 Format
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom);	// 보내는사람 생략하거나 하면 정상작동을 안함
			messageHelper.setTo(tomail); 	// 받는사람 이메일
			messageHelper.setSubject(title);// 메일제목은 생략이 가능하다
			String tempPassword = (int) (Math.random() * 999999) + 1 + "";
			messageHelper.setText("임시 비밀번호입니다 : " + tempPassword); // 메일내용
			System.out.println("임시 비밀번호입니다 : " + tempPassword);
			DataSource dataSource = new FileDataSource("c:\\log\\hwa.png");
			messageHelper.addAttachment(MimeUtility.encodeText("hwa3.png", "UTF-8", "B"), dataSource); // 이름을 바꿀수 있음. B->기본
			mailSender.send(message);
			model.addAttribute("check", 1); // 정상 전달
			// DB tempPassword Logic 구성
			
		} catch (Exception e) {
			System.out.println("mailTransport e.getMessage()->"+e.getMessage());
			model.addAttribute("check", 2); // 메일 전달 실패
		}
		
		return "mailResult";
	}
	
	// Procedure Test 입력화면
	@RequestMapping(value = "writeDeptIn")
	public String writeDeptIn(Model model) {
		System.out.println("writeDeptIn Start...");
		
		return "writeDept3";
	}
	
	// Procedure를 통한 Dept 입력후 VO 전달
	public String writeDept(DeptVO deptVO , Model model) {
		System.out.println("writeDept Strat...");
		es.insertDept(deptVO); // procedure로 집어넣을예정
		if(deptVO == null) {
			System.out.println("deptVO NULL");
		} else {
			System.out.println("deptVO.getOdeptno()->"+deptVO.getOdeptno());
			System.out.println("deptVO.getOdname()->"+deptVO.getOdname());
			System.out.println("deptVO.getOloc()->"+deptVO.getOloc());
			model.addAttribute("msg" , "정상 입력 되었습니다 ^^");
			model.addAttribute("dept", deptVO);
		}
				
		return "writeDept3";
	}

}
