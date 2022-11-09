package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;

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

	@Override
	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listEmp Start...");
		try {
			//								Map ID		parameter
			empList = session.selectList("tkEmpListAll" , emp); // 여러개면 selectList, 하나면 selectOne
			System.out.println("EmpDaoImpl listEmp empList.size()->"+empList.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp e.getMessage()->"+e.getMessage());
		}		
		return empList;
	}
	// 회원상세조회
	@Override
	public Emp detailEmp(int empno) {
		System.out.println("EmpDaoImpl detail start...");
		Emp emp = new Emp();
		try {
			//						 mapperID    ,	Parameter
			emp = session.selectOne("tkEmpSelOne", empno);
			System.out.println("EmpDaoImpl detail getEname->"+emp.getEname());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl detail Exception->"+e.getMessage());
		}
		return emp;
	}
	// 회원정보수정
	@Override
	public int updateEmp(Emp emp) {
		System.out.println("EmpDaoImpl update Strat...");
		int updateCount = 0;
		try {
			updateCount = session.update("tkEmpUpdate",emp);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl updateEmp Exception->"+e.getMessage());
		}
		return updateCount;
	}

	@Override
	public List<Emp> listManager() {
		System.out.println("EmpDaoImpl listManager Start...");
		// emp 관리자만 Select           Naming Rule 
		// empList = session.selectList("tkSelectManager");
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listManager Start...");
		try {
			//								Map ID		parameter
			empList = session.selectList("tkEmpListAll"); // 여러개면 selectList, 하나면 selectOne
			System.out.println("EmpDaoImpl listManager empList.size()->"+empList.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listManager e.getMessage()->"+e.getMessage());
		}		
		return empList;
	}

}
