<%
	/*============================================================================
	 * @ Description : 관리자 화면 인덱스
	 *
	 * 작성일 : 2010.12.01
	 * 작성자 : 박병웅
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%
	if(session.getAttribute("adminID") != null){
		response.sendRedirect("main.jsp");
	}
	else {
		response.sendRedirect("login/login.jsp");
	}
%>

<%@ include file="/include/footer.jsp" %>
