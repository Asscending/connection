<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 몇번째 방문인지 쿠키를 사용한 exam 이다 -->
<%
	int n=0;
	Cookie []cookies=request.getCookies();
	if(cookies != null){
	for(Cookie co:cookies){
		if( co.getName().equals("visitCount")){
			n=Integer.parseInt(co.getValue());
		}
	}
}

	n=n+1;
	out.println(n+"번째 방문입니다.");

	
	Cookie c = new Cookie("visitCount",n+"");
	response.addCookie(c);
	
	
%>
</body>
</html>