package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

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
		// emp 관리자만 Select           		Naming Rule 
		// empList = session.selectList("tkSelectManager");
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listManager Start...");
		try {
			//								Map ID
			empList = session.selectList("tkSelectManager"); // 여러개면 selectList, 하나면 selectOne
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listManager e.getMessage()->"+e.getMessage());
		}		
		return empList;
	}
	
	// 회원등록
	@Override
	public int insertEmp(Emp emp) {
		int result = 0;
		System.out.println("EmpDaoImpl insertEmp Start...");
		try {
			result = session.insert("insertEmp", emp); 
		} catch (Exception e) {
			System.out.println("EmpDaoImpl insertEmp e.getMessage()->"+e.getMessage());
		}
		return result;
	}

	// 회원삭제
	@Override
	public int deleteEmp(int empno) {
		System.out.println("EmpDaoImpl delete Start...");
		int result = 0;
		System.out.println("EmpDaoImpl delete empno->"+empno);
		try {
			result = session.delete("deleteEmp", empno);
			System.out.println("EmpDaoImpl delete result->"+result);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl delete e.getMessage()->"+e.getMessage());
		}
		return result;
	}
	
	// 직원부서조회
	@Override
	public List<EmpDept> listEmpDept() {
		System.out.println("EmpDaoImpl listEmpDept Start...");
		List<EmpDept> empDept = null;
		try {
			empDept = session.selectList("tkListEmpDept");
		System.out.println("EmpDaoImpl empDept.size()->"+empDept.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmpDept e.getMessage()->"+e.getMessage());
		}
		return empDept;
	}

	@Override
	public String deptName(int deptno) {
		System.out.println("EmpDaoImpl deptName Start...");
		String resultStr = "";
		try {
			resultStr = session.selectOne("tkDeptName",deptno);
			System.out.println("EmpDaoImpl deptName resultStr->"+resultStr);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl deptName Exception->"+e.getMessage());
		}
		return resultStr;
	}

	// 조회 (업무,이름)
	@Override
	public List<Emp> empSearchList3(Emp emp) {
		List<Emp> empSearchList3 = null;
		System.out.println("EmpDaoImpl empSearchList3 Strat...");
		try {
			// keyword검색
			// NamingRule							Map ID		   parameter
			empSearchList3 = session.selectList("tkEmpSearchList3", emp);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl empSearchList3 Exception->"+e.getMessage());
		}
		return empSearchList3;
	}

}
