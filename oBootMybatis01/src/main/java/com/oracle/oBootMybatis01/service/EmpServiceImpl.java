package com.oracle.oBootMybatis01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.EmpDao;
import com.oracle.oBootMybatis01.model.Emp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {
	// EmpDao 연결
	private final EmpDao ed; // 생성자 자동 인젝션
	
	@Override
	public int totalEmp() {
		System.out.println("EmpServiceImpl Start total...");
		int totEmpCnt = ed.totalEmp(); // totalEmp호출
		System.out.println("EmpServiceImpl totalEmp totEmpCnt->"+totEmpCnt);

		return totEmpCnt;
	}

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpServiceImpl listEmp Start...");
		empList = ed.listEmp(emp);
		System.out.println("EmpServiceImpl listEmp empList.size()->"+empList.size());
		
		return empList;
	}

}
