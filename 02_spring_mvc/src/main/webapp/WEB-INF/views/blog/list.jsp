<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="UTF-8">
<title>블로그 목록</title>
<style>
  .blog{
    width: 200px;
    cursor: pointer;
    background-color: yellow;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <c:forEach items="${blogList}" var="blog" varStatus="vs">
    <div class="blog">
      <span>${vs.index}</span>
      <span class="blog-no">${blog.blogNo}</span>
      <span>${blog.title}</span>
    </div>
  </c:forEach>
  
  <script type="text/javascript">
  	
  	$('.blog').on('click', function(evt){
  			
  		/* 클릭한 div 에 하위 blog-no 찾기 */
  		let blogNo = $(this).find('.blog-no').text();
  		location.href ='${contextPath}/blog/detail.do?blogNo=' + blogNo;
  	});
  	
  </script>
  

</body>
</html>