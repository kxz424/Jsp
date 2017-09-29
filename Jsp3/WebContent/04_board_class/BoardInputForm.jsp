<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 글쓰기</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('input[type="button"]').click(function(){
		var wName = $('input[name="writerName"]');
		var title = $('input[name="title"]');
		var content = $('input[name="content"]');
		var password = $('input[name="password"]');
		if(wName.val() == null){
			alert('필수 입력사항입니다.');
			return;
		}
		
		if(title.val() == null){
			alert('필수 입력사항입니다.');
			return;
		}
		
		if(content.text() == null){
			alert('필수 입력사항입니다.');
			return;
		}
		
		if(password.val() == null){
			alert('필수 입력사항입니다.');
			return;
		}
		
		$('form[name="frm"]').submit();
	});
});
</script>


</head>
 <body>
	<h4> 게시판 글 쓰기 </h4><br/>
	나중에 이쁘게 만드시오 <br/><br/>
	<form name='frm' method='post' action="BoardControl?service=input-confirm">
	작성자 : <input type='text' name="writerName"><br/><br/>
	제  목 : <input type='text' name="title"><br/><br/>
	내  용 : <textarea rows='10' cols='40' name="content"></textarea><br/><br/>
	패스워드(수정/삭제시 필요) :
			 <input type='password' name="password"><br/><br/>
	<input type='button' value='작성'>
	<input type='reset' value='취소'>
	</form>

</body>
</html>