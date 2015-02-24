<%
	/*============================================================================
	 * @ Description : Free app party Admin Login
	 *
	 * 작성일 : 2011.04.14
	 * 작성자 :	최형범
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/header.jsp" %>
<%
	// Params
	String adminid = StringUtils.defaultString(request.getParameter("adminid"));
	String password = StringUtils.defaultString(request.getParameter("password"));

	// MD5
	password = DigestUtils.md5Hex(password);

	// Vars
	RowSetMapper cRowSet = null;
	String check_result = "";

	// 관리자 아디이와 비밀번호를 확인한다.
	check_result = AdminDAO.getInstance().isAdmin(adminid, password);

	if(check_result.equals("success")){
		//기본정보를 가져온다.
		cRowSet = AdminDAO.getInstance().chkAdminId(adminid);

		// session 영역에 adminid를 저장한다.
		session.setAttribute("adminID", adminid);
		
		if(cRowSet.next()){
			session.setAttribute("sPermission", StringUtils.defaultString(cRowSet.getString("permission"),""));
			session.setAttribute("sGroup", StringUtils.defaultString(cRowSet.getString("user_group"),""));
		}

		session.setMaxInactiveInterval(60*60*24);	// session time : 24시간
		out.println(session.getMaxInactiveInterval());
		response.sendRedirect("../main.jsp");
	}
	else {
		response.sendRedirect("login.jsp?check_result="+check_result);
	}

%>
<%@ include file="/include/footer.jsp" %>
