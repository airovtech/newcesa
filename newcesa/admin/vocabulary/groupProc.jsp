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

	String groupName = StringUtils.defaultString(request.getParameter("groupName"));
	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"), "0");

	String orgGroupName = StringUtils.defaultString(request.getParameter("orgGroupName"));

	String[] checkValue = request.getParameterValues("checkValue");

	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}

	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;

	String lastInsertId = null;

	try {
		if(mode.equals("Create")){

			if( "".equals(groupName) || "".equals(groupSeq) ) {
				message = "Invalid Parameters";
			} else {

				if(orgGroupName.equals(groupName)){
					groupName = "복사한 그룹 - "+groupName;
				}

				lastInsertId = VocabularyDAO.getInstance().regGroup(groupName, groupSeq);
				
				if(!StringUtils.isEmpty(lastInsertId)){
					
					if(checkValue!=null){
						for(int i=0;i<checkValue.length;i++){
							VocabularyDAO.getInstance().regGroupSub(checkValue[i], lastInsertId);
						}
					}
					message = "등록이 완료되었습니다.";
				}
			}
		} else if(mode.equals("Modify")){

			result = VocabularyDAO.getInstance().modGroup(groupName, groupSeq, seq);
		
			if(result){
				
				VocabularyDAO.getInstance().delGroupSub(seq);
				if(checkValue!=null){
					for(int i=0;i<checkValue.length;i++){
						log.debug("---- checkValue : "+checkValue[i]);
						VocabularyDAO.getInstance().regGroupSub(checkValue[i], seq);
					}
				}

				message = "수정되었습니다.";
			}
		}
		else if(mode.equals("Delete")){
			result = VocabularyDAO.getInstance().delGroup(seq);
			if(result){
				message = "삭제되었습니다.";
			}
		}
		else {
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
