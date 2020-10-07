<%@page import="com.bit.vo.BoardVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.bit.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
  
</head>
<body>
	<h2>게시물 목록</h2>
	<hr>
	
	<a href="insertBoard.do">새글등록</a><br>
	<table border="1" width="100%" >
		<tr>
			<td>글번호</td>
			<td>글제목</td>
			<td>작성자</td>
			<td>조회수</td>
			<td>등록일</td>
			<td>ip주소</td>
		</tr>
		
		<c:forEach var="b" items="${list }">
			<tr>
				<td>${b.no }</td>
				<td>
					<a href="detailBoard.do?no=${b.no }">${b.title }</a> 
				</td>
				<td>${b.writer }</td>
				<td>${b.hit }</td>
				<td>${b.regdate }</td>
				<td>${b.ip }</td>
			</tr>
		</c:forEach>
		
	</table>
	
	<c:if test="${startPage > 1  }">
	<a href="listBoard.do?pageNUM=${startPage-1 }">이전<a>
	</c:if>
	
	<c:forEach var="i" begin="${startPage }" end="${endPage }">
	<a href="listBoard.do?pageNUM=${i }">${i }</a>
	</c:forEach>
	
	<c:if test="${endPage < totalPage  }">
	<a href="listBoard.do?pageNUM=${endPage+1 }">다음<a>
	</c:if>
</body>
</html>