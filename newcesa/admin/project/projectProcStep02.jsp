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

	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;
	
	String[] activityNames = request.getParameterValues("activityName");
	String[] wordNames = request.getParameterValues("wordName");
	String[] checkVals = request.getParameterValues("checkVal");

	int activityIndex = 0;
	int wordIndex = 0;

	try {
		log.debug("-----------------------------------------------");
		if(activityNames!=null){
			//삭제 후 재등록
			ProjectDAO.getInstance().delProjectActivity(seq);

			for(int i=0;i<activityNames.length;i++){
				log.debug(activityNames[i]);
				ProjectDAO.getInstance().regProjectActivity(activityNames[i], seq);
			}
		}
		log.debug("-----------------------------------------------");
		if(wordNames!=null){
			//삭제 후 재등록
			ProjectDAO.getInstance().delProjectWord(seq);
			for(int i=0;i<wordNames.length;i++){
				log.debug(wordNames[i]);
				ProjectDAO.getInstance().regProjectWord(wordNames[i], seq);
			}
		}
		
		log.debug("-----------------------------------------------");
		if(checkVals!=null){
			
			//삭제 후 재등록
			ProjectDAO.getInstance().delProjectActivityWord(seq);

			for(int i=0;i<checkVals.length;i++){
				log.debug(checkVals[i]);
				
				if(checkVals[i].split("-").length>1){
					activityIndex = Integer.parseInt(checkVals[i].split("-")[0])-1;
					wordIndex = Integer.parseInt(checkVals[i].split("-")[1])-1;

					ProjectDAO.getInstance().regProjectActivityWord(checkVals[i].split("-")[0], checkVals[i].split("-")[1], activityNames[activityIndex], wordNames[wordIndex], seq);
				}
			}
		}
		log.debug("-----------------------------------------------");


		out.println("<script language='javascript'>");
		out.println("alert('등록 되었습니다.');");
		out.println("self.location.replace('projectList.jsp');");
		out.println("</script>");
	}           
	catch(Exception e){
		log.debug("Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
