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
	// Param
	String mode		= StringUtils.defaultString(request.getParameter("mode"));

	String seq		= StringUtils.defaultString(request.getParameter("seq"));
	String word	= StringUtils.defaultString(request.getParameter("word"));

	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"), "0");


	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}


	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;

	try {
		
		if(mode.equals("Delete")){
			result = ProjectDAO.getInstance().delProject(seq);
		
			if(result){
				message = "삭제되었습니다.";
			}
		}
		else {
			message = "Error : Need Process Type";
		}

		out.println("<script language='javascript'>");
		out.println("alert('"+message+"');");
		out.println("self.location.replace('projectList.jsp');");
		out.println("</script>");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
