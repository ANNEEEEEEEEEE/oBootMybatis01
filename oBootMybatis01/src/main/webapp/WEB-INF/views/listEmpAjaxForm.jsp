<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<%
	String context = request.getContextPath();
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	function getDeptName(vDeptno) {
		console.log(vDeptno);
		alert("vDeptno->"+vDeptno);
		// 목적 : Ajax로 부서번호 보내고 부서명 받음
		$.ajax(
				{
					url:"<%=context%>/getDeptName",
					data:{deptno : vDeptno},
					dataType:'text',
					success:function(data) {
						alert("success ajax Data->"+data); // 성공하면 가져오는 값까지  alert처리
						$('#deptName').val(data);	/* input Tag */
						$('#msg').html(data);		/* span id Tag */
					}
				}		
		);
	}

</script>
</head>
<body>
	<h2>회원 정보</h2>
<table>
	<tr>
		<th>사번</th>
		<th>이름</th>
		<th>업무</th>
		<th>부서</th>
		<th>근무지</th>
	</tr>
	<c:forEach var="emp" items="${listEmp}">
		<tr>
			<td>${emp.empno }</td>
			<td>${emp.ename }</td>
			<td>${emp.job }</td>
			<td>${emp.deptno} 
				<input type="button" id="btn_idCheck" value="부서명" onmouseover="getDeptName(${emp.deptno })">
			</td>
			<%-- <td>${emp.loc }</td> --%>
		</tr>
	</c:forEach>
</table>

	deptName:  <input type="text" id="deptName"  readonly="readonly"><p>
    Message :  <span id="msg"></span><p>

</body>
</html>