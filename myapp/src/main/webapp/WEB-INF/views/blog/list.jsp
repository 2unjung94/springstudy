<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" value="<%=request.getContextPath()%>"/>
<c:set var="dt" value="<%=System.currentTimeMillis()%>"/>

<jsp:include page="../layout/header.jsp"/>

<!-- 작성화면 클릭 -> 로그인 체크 인터셉터 -> 작성화면 (세션의 user 정보 사용) -> 서버로 넘김 -->
<h1 class="title">블로그 목록</h1>

<a href="${contextPath}/blog/write.page">블로그작성</a>



<%@ include file="../layout/footer.jsp" %>