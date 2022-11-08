<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>list.jsp 입성 성공</h1>
	<h3>사원수 : ${totalEmp}</h3>
	
	<table>
		<tr>
			<th>번호</th>
			<th>사번</th>
			<th>이름</th>
			<th>업무</th>
			<th>급여</th>
		</tr>
		<c:forEach var="emp" items="${listEmp }">
			<tr>
				<td>${num }</td>
				<td>${emp.empno }</td>
				<td><a href="detail?empno=${emp.empno }">${emp.ename }</a></td>
				<td>${emp.job }</td>
				<td>${emp.sal }</td>
			</tr>
			<c:set var="num" value="${num - 1 }"></c:set>
		</c:forEach>
	</table>
</body>
</html>