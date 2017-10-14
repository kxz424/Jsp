<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.model.*, board.service.*" %>
<%@ page import="java.util.*" %>

<%-- jstl 설정 --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%  //웹브라우저가 게시글 목록을 캐싱할 경우 새로운 글이 추가되더라도 새글이 목록에 안 보일 수 있기 때문에 설정
	response.setHeader("Pragma","No-cache");		// HTTP 1.0 version
	response.setHeader("Cache-Control","no-cache");	// HTTP 1.1 version
	response.setHeader("Cache-Control","no-store"); // 일부 파이어폭스 버스 관련
	response.setDateHeader("Expires", 1L);			// 현재 시간 이전으로 만료일을 지정함으로써 응답결과가 캐쉬되지 않도록 설정
%>

<!-- 이전화면에서 클릭한 페이지번호 저장 -->
<c:set var="pageNum" value="${param.pageNum }"></c:set>
<!-- ListService에서 받아온 값 저장(DB에서 넘어온값) -->
<c:set var="mList" value="${requestScope.mList }"></c:set>
<c:set var="totalPage" value="${requestScope.totalPage }"></c:set>
<c:set var="map" value="${requestScope.group }"></c:set>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 게시글 목록 </title>
</head>

<BODY>

	<h3> 게시판 목록 </h3>
	
	<table border="1" bordercolor="darkblue">
	<tr>
		<td> 글번호 </td>
		<td> 제 목 </td>
		<td> 작성자 </td>
		<td> 작성일 </td>
		<td> 조회수 </td>
	</tr>
	
	<c:choose>
		<c:when test="${empty mList }">
			<tr><td colspan="5"> 등록된 게시물이 없습니다. </td></tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="rec" items="${mList }">
				<tr>
					<td>${rec.articleId }</td>
					<td>
						<c:forEach var="i" begin="1" end="${rec.getLevel() }">
							&nbsp;
						</c:forEach>
						
						<c:if test="${rec.getLevel() ne 0 }">
							<img alt="" src="04_board_class/imgs/board_re.gif">
						</c:if>
						
						<a href="BoardControl?service=read-page&id=${rec.articleId }">
							${rec.title}
						</a>
						
						<jsp:useBean id="today" class="java.util.Date"></jsp:useBean>
						<fmt:formatDate var="nowDay" value="${today }" pattern="yyyy-MM-dd"/>
						
						<c:if test="${nowDay eq fn:substring(rec.postingDate, 0, 10) }">
							<img alt="" src="04_board_class/imgs/new.gif">
						</c:if>
					</td>
					<td>${rec.writerName }</td>
					<td>${rec.postingDate }</td>
					<td>${rec.readCount }</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
	<tr>
		<td colspan="4" >
			<a href="BoardControl?service=list-page&pageNum=1">◀◀</a>
			<a href="BoardControl?service=list-page&pageNum=${map.prev }">◀</a>
			<c:forEach var="i" begin="${map.start }" end="${map.end }">
				<a href="BoardControl?service=list-page&pageNum=${i }">[${i }]</a>
			</c:forEach>
			<a href="BoardControl?service=list-page&pageNum=${map.next }">▶</a>
			<a href="BoardControl?service=list-page&pageNum=${totalPage }">▶▶</a>
		</td>
		<td>
			<a href="BoardControl?service=input-form">글쓰기</a>
		</td>
	</tr>

	</table>
	
	
</BODY>
</HTML>
