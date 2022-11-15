package com.oracle.oBootMybatis01.model;

import lombok.Data;

@Data
public class Emp {
	private int 	empno;			private String 	ename;
	private String 	job;			private int 	mgr;
	private String 	hiredate;		private int 	sal;
	private int 	comm;			private int 	deptno;
	private String 	loc;
	
	// 조회용
	private String search;			private String keyword;
	private String pageNum;
	private int start;				private int end;
}
