package com.oracle.oBootMybatis01.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {
	/*
	 * @Autowired 
	 * private SqlSession session; // ibatis == mybatis
	 */	
	
	// Mybatis DB 연동
	private final SqlSession session; // 생성자 주입
	
	@Override
	public int totalEmp() {
		int totEmpCount = 0;
		System.out.println("EmpDaoImpl Start total...");
		
		// 문제가 생기면 확인할 수 있도록
		try {
			totEmpCount = session.selectOne("empTotal");
			System.out.println("EmpDaoImpl totalEmp totEmpCount->"+totEmpCount);

		} catch (Exception e) {
			System.out.println("EmpDaoImpl totalEmp Exception->"+e.getMessage());
		}
		
		
		return totEmpCount;
	}

}
