package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {
	
	// Mybatis DB 연동
		private final SqlSession session; // 생성자 주입
	
	@Override
	public List<Dept> deptSelect() {
		// deptList =  session.selectList("tkSelectDept");
		List<Dept> deptList = null;
		System.out.println("DeptDaoImpl deptSelect Start...");
		//								Map ID		
		deptList = session.selectList("tkSelectDept"); // 여러개면 selectList, 하나면 selectOne
		System.out.println("DeptDaoImpl deptList empList.size()->"+deptList.size());
			
		return deptList;
	}

	@Override
	public void insertDept(DeptVO deptVO) {
		System.out.println("DeptDaoImpl insertDept Start...");
		session.selectOne("ProcDeptInsert" , deptVO);
		
	}
}
