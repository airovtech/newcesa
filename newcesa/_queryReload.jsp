<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%
	out.println("query reloading<br>");
	QueryContext.getInstance().reloadConfig();
%>
<dnt7:page name="searchFrm" showAlways="true" serviceType='front'/>
<%@ include file="/include/footer.jsp" %>
