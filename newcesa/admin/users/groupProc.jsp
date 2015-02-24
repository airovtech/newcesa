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
	String groupName	= StringUtils.defaultString(request.getParameter("groupName"));


	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;

	try {
		if(mode.equals("Create")){

			
			result = GroupDAO.getInstance().regGroup(groupName);
				
			if(result){
				// 회원 가입 완료
				message = "그룹이 추가되었습니다.";
			}
			
		
		} else if(mode.equals("Modify")){
			
			result = GroupDAO.getInstance().modGroup(groupName, seq);
		
			if(result){
				message = "수정되었습니다.";
			}
		} else if(mode.equals("Delete")){
			
			result = GroupDAO.getInstance().delGroup(seq);
		
			if(result){
				message = "삭제되었습니다.";
			}
		} else {
			message = "Error : Need Process Type";
		}

		out.println("<script language='javascript'>");
		out.println("alert('"+message+"');");
		out.println("self.location.replace('groupList.jsp');");
		out.println("</script>");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
