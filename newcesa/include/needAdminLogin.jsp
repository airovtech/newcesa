<%
	/*============================================================================
	 * @ Description : 로그인이 필요한 페이지에 include 한다. (관리자용)
	 *
	 * 작성일 : 2010.12.16
	 * 작성자 : 우진호
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%

	if( !isAdminLogin ){
		//ju.alertAndRedirect("Need Login", "/admin/login/login.jsp");
		out.println("<script language='javascript'>");
		out.println("alert(\"Need Login\");");
		out.println("self.location.replace('/admin/login/login.jsp');");
		out.println("</script>");
		return;
	}

%>

