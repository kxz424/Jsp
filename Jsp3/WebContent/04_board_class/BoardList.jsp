<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="board.model.*, board.service.*" %>
<%@ page import="java.util.*" %>

<%  //웹브라우저가 게시글 목록을 캐싱할 경우 새로운 글이 추가되더라도 새글이 목록에 안 보일 수 있기 때문에 설정
	response.setHeader("Pragma","No-cache");		// HTTP 1.0 version
	response.setHeader("Cache-Control","no-cache");	// HTTP 1.1 version
	response.setHeader("Cache-Control","no-store"); // 일부 파이어폭스 버스 관련
	response.setDateHeader("Expires", 1L);			// 현재 시간 이전으로 만료일을 지정함으로써 응답결과가 캐쉬되지 않도록 설정
%>

<%
	String pageNum = request.getParameter("pageNum");
// Service에 getArticleList()함수를 호출하여 전체 메세지 레코드 검색 
//  ListArticleService service = ListArticleService.getInstance();
//  List <BoardRec> mList =  service.getArticleList(pageNum); 
 

	
	List<BoardRec> mList =(List) request.getAttribute("mList");
 	int totalPage = (int)request.getAttribute("totalPage");
 	HashMap<String, Integer> map = (HashMap)request.getAttribute("group");

%>

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
	

	<% if( mList.isEmpty() ) { %>
		<tr><td colspan="5"> 등록된 게시물이 없습니다. </td></tr>
	<% } else { %>
	
		<% for(BoardRec rec : mList){ %>
			<tr>
				<td><%= rec.getArticleId() %></td>
				<td>
				
					<% for(int i = 0; i < rec.getLevel(); i++){ %>
						&nbsp;
					<% } %>
				
					<% if(rec.getLevel() != 0){ %>
						<img src="04_board_class/imgs/board_re.gif"/>
					<% } %>
					<a href="BoardControl?service=read-page&id=<%=rec.getArticleId()%>">
						<%= rec.getTitle() %>
					</a>
					<% 
						Date d = new Date();
						String day = d.toString();
						SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
					%>
					<% if(s.format(d).equals(rec.getPostingDate().substring(0, 10))){ %>
						<img src="04_board_class/imgs/new.gif" />
					<% } %>
				</td>
				<td><%= rec.getWriterName() %></td>		
				<td><%= rec.getPostingDate() %></td>
				<td><%= rec.getReadCount() %></td>
			</tr>
		<% } //end for %>

	<% } // end else %>
	
		<tr>
			<td colspan="4">
				<a href="BoardControl?service=list-page&pageNum=1">◀◀</a>
				<a href="BoardControl?service=list-page&pageNum=<%=map.get("prev") %>">◀</a>
				<% for(int i = map.get("start"); i <= map.get("end"); i++){ %>
					<a href="BoardControl?service=list-page&pageNum=<%=i%>">[<%=i %>]</a>
				<% } %>
				<a href="BoardControl?service=list-page&pageNum=<%=map.get("next") %>">▶</a>
				<a href="BoardControl?service=list-page&pageNum=<%=totalPage%>">▶▶</a>
			</td>
			<td>
				<a href="BoardControl?service=input-form">글쓰기</a>
			</td>
		</tr>
	</table>
	
	
</BODY>
</HTML>
