<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>회원목록</h2>
<table border="1" width="80%">
	<tr>
		<td>아이디</td>
		<td>이름</td>
		<td>주소</td>
		<td>번호</td>
	</tr>
	
	<c:forEach var="m" items="${list }">
	<tr>
		<td>${m.custid }</td>
		<td>${m.name }</td>
		<td>${m.address }</td>
		<td>${m.phone }</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>