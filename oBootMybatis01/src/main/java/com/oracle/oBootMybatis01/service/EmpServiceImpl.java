package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.DeptDao;
import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	// EmpDao 연결
	private final EmpDao ed; // 생성자 자동 인젝션
	private final DeptDao dd;
	
	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl Start total...");
		int totEmpCnt = ed.totalEmp(); // totalEmp호출
		System.out.println("EmpServiceImpl totalEmp totEmpCnt->"+totEmpCnt);

		return totEmpCnt;
	}
	// 회원목록
	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listEmp Start...");
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()->"+empList.size());
		
		return empList;
	}
	// 회원정보상세조회
	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpServiceImpl detail...");
		Emp emp = null;
		emp = ed.detailEmp(empno);
		
		return emp;
	}
	
	// 회원정보수정
	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpServiceImpl update...");
		int updateCount = 0;
		updateCount = ed.updateEmp(emp);
		
		return updateCount;
	}
	
	// emp -> 관리자
	@Override
	public List<Emp> listManager() {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listManager...");
		// ed.listManager();
		empList = ed.listManager();
		return empList;
	}
	// dept 정보
	@Override
	public List<Dept> deptSelect() {
		List<Dept> deptList = null;
		System.out.println("EmpServiceImpl deptSelect...");
		// deptList = dd.deptSelect();
		deptList = dd.deptSelect();
		
		return deptList;
	}
	
	

}
