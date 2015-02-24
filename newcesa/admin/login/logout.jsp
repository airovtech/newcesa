<%
	/*============================================================================
	 * @ Description : PSSD 로그 아웃
	 *
	 * 작성일 : 2010.07.26
	 * 작성자 : 박병웅
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/header.jsp" %>
<%
	session.setAttribute("adminID", null);
	response.sendRedirect("/admin");
%>
<%@ include file="/include/footer.jsp" %>
