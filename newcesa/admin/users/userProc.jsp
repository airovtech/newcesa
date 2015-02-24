<%
	/*============================================================================
	 * @ Description : 관리자 관리 프로세스
	 *
	 * 작성일 : 2011.04.18
	 * 작성자 : 이정순
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/needAdminLogin.jsp" %>
<%
	String user_id	= StringUtils.defaultString(request.getParameter("user_id"));
	String point	= StringUtils.defaultString(request.getParameter("point"));
	String title	= StringUtils.defaultString(request.getParameter("title"));
	
	// proc variable
	String message = "Success";
	List params = new ArrayList();
	int check_result = 0;

	try {
		check_result = UserDAO.getInstance().regAdminPoint(user_id, point, title);

		out.println(check_result);

	if(check_result > 0) {

			out.println("<script language='javascript'>");
			out.println("alert('지급되었습니다.');");
			out.println("self.location.replace('pointList.jsp?userId="+user_id+"');");
			out.println("</script>");

			return;
		}

		out.println("<script language='javascript'>");
		out.println("alert('오류가 발생했습니다.');");
		out.println("self.location.replace('userDetail.jsp?userId="+user_id+"');");
		out.println("</script>");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
