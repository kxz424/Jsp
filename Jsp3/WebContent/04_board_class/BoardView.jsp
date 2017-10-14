<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.service.*, board.model.*" %>

<%-- jstl 설정 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

   
<!-- 이전화면에서 클릭한 페이지번호 저장 -->
<c:set var="id" value="${requestScope.id }"></c:set>
<!-- ViewService에서 받아온 값 저장(DB에서 넘어온값) -->
<c:set var="rec" value="${requestScope.read }"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 게시글 보기 </title>
</head>
<body>

	<h4> 게시판 글 보기 </h4><br/>
	<table border="1" bordercolor="red">
	<tr>
		<td> 제  목 : </td>
		<td>${rec.title }</td>
	</tr>
	<tr>
		<td> 작성자 : </td>
		<td>${rec.writerName }</td>
	</tr>
	<tr>
		<td> 작성일자 : </td>
		<td>${rec.postingDate }</td>
	</tr>
	<tr>
		<td> 내  용 : </td>
		<td>${rec.content }</td>
	</tr>
	<tr>
		<td colspan="2">
			<a href="BoardControl?service=list-page&pageNum=1">목록보기</a>
			<a href="BoardControl?service=reply-form&parentId=${rec.articleId }">답변하기</a>
			<a href="BoardControl?service=modify-form&id=${rec.articleId }">수정하기</a>
			<a href="BoardControl?service=delete-form&id=${rec.articleId }">삭제하기</a>	
		</td>
	</tr>
	</table>


</body>
</html>