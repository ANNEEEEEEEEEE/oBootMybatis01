package com.oracle.oBootMybatis01.service;

import org.springframework.stereotype.Service;

import com.oracle.oBootMybatis01.dao.EmpDao;

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

}
