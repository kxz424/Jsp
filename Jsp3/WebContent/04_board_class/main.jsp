<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <% String projectName = "/Jsp3"; %>    --%>
<c:set var="projectName" value="/Jsp3"></c:set>
  

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 메인화면</title>
</head>
<body>

	게시판 메인화면
	<a href="${projectName }/BoardControl?service=list-page&pageNum=1"> 게시판으로 이동 </a><br/><br/>
	<img src="imgs/image.gif"><br/>
	
	안녕하세요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

</body>
</html>