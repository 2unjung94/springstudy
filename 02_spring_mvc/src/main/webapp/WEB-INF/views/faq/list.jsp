<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>faq 목록</title>
<script>
/* 주소창에 직접 faq/list.do 로 접속하면 
	 let addResult = ; 가 되버려서 js 오류 발생한다.
	 따라서 값이 없어도 동작하게끔 따옴표로 묶어준다. 
*/
	function fnAddResult(){
		let addResult = '${addResult}';
		if(addResult !=='' && addResult === '1'){
			alert('정상적으로 등록되었습니다.');	
		}
	}
	fnAddResult();
</script>
</head>
<body>


</body>
</html>