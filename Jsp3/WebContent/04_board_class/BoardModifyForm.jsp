<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.service.*, board.model.*" %>

<%-- jstl 설정 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  

<!-- 목록에서 클릭한 게시물의 번호 저장 -->
<c:set var="id" value="${requestScope.id}"></c:set>
<!-- ModifyService에서 가져온 값 저장(DB에서 가져온) -->
<c:set var="rec" value="${requestScope.read}"></c:set>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정하기</title>
</head>
 <body>
	<h4> 게시판 글 수정하기 </h4><br/>
	<form name='frm' method='post' action="BoardControl?service=modify-confirm">
	<input type="hidden" name="articleId" value="${id}"/>
	제  목 : <input type='text' name="title" value="${rec.title }"><br/><br/>
	패스워드(수정/삭제시 필요) :
			<input type='password' name="password"><br/><br/>
	내  용 : <textarea name='content' rows='10' cols='40'>${rec.content }</textarea><br/><br/>

	<input type='submit' value='수정하기'>
	<input type='button' value='목록보기' onclick="window.location='BoardControl?service=list-page&pageNum=1'">
	</form>

</body>
</html>