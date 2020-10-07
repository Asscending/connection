<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setAttribute("member1", "김찬휘");
	session.setAttribute("member2", "이수인"); //창닫을때까지 유지
	application.setAttribute("member3", "홍수지"); // 서버종료될때까지
	
	RequestDispatcher dispatcher = request.getRequestDispatcher("getAttribute.jsp");
	dispatcher.forward(request, response); //리퀘스트를 담아서 가라는 것. 이것을 써줘야 멤버 1의 정보가 출력된다.
	
%>
값을 설정하였다.<br>
<a href="getAttribute.jsp">설정값 확인</a>
</body>
</html>