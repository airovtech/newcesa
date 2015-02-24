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

	RowSetMapper cRowSet = null;

	// Param
	String mode		= StringUtils.defaultString(request.getParameter("mode"));

	String seq		= StringUtils.defaultString(request.getParameter("seq"));

	String newProjectName	= StringUtils.defaultString(request.getParameter("newProjectName"));
	String projectDesc	= StringUtils.defaultString(request.getParameter("projectDesc"));
	String startDate	= StringUtils.defaultString(request.getParameter("startDate"));
	String endDate	= StringUtils.defaultString(request.getParameter("endDate"));
	String tempImage	= StringUtils.defaultString(request.getParameter("tempImage"));

	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"), "0");
	String returnUrl = "projectList.jsp";



	String[] activityNames = request.getParameterValues("activityName");
	String[] wordNames = request.getParameterValues("wordName");
	String[] checkVals = request.getParameterValues("checkVal");

	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}

	String lastInsertId = null;

	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;

	try {

		//data를 가져온다.
		cRowSet = ProjectDAO.getInstance().getProjectDetail(seq);
		
		if(cRowSet.next()){
			projectDesc = cRowSet.getString("project_desc");
			startDate = cRowSet.getString("start_date");
			endDate = cRowSet.getString("end_date");
			tempImage = cRowSet.getString("image");

			groupSeq = cRowSet.getString("user_group");
		}

		if(  "".equals(newProjectName) || "".equals(projectDesc) || "".equals(startDate) || "".equals(endDate) ) {
			message = "Invalid Parameters";
			returnUrl = "projectList.jsp";
		} else {
		
			params.add(0, newProjectName);
			params.add(1, projectDesc);
			params.add(2, startDate);
			params.add(3, endDate);
			params.add(4, tempImage);
			params.add(5, groupSeq);
			
			//등록
			lastInsertId = ProjectDAO.getInstance().regProject(params);
		
			
		}

		
		int activityIndex = 0;
		int wordIndex = 0;

		log.debug("-----------------------------------------------");
		if(activityNames!=null){
			//삭제 후 재등록
			ProjectDAO.getInstance().delProjectActivity(lastInsertId);

			for(int i=0;i<activityNames.length;i++){
				log.debug(activityNames[i]);
				ProjectDAO.getInstance().regProjectActivity(activityNames[i], lastInsertId);
			}
		}
		log.debug("-----------------------------------------------");
		if(wordNames!=null){
			//삭제 후 재등록
			ProjectDAO.getInstance().delProjectWord(lastInsertId);
			for(int i=0;i<wordNames.length;i++){
				log.debug(wordNames[i]);
				ProjectDAO.getInstance().regProjectWord(wordNames[i], lastInsertId);
			}
		}
		
		log.debug("-----------------------------------------------");
		if(checkVals!=null){
			
			//삭제 후 재등록
			ProjectDAO.getInstance().delProjectActivityWord(lastInsertId);

			for(int i=0;i<checkVals.length;i++){
				log.debug(checkVals[i]);
				
				if(checkVals[i].split("-").length>1){
					activityIndex = Integer.parseInt(checkVals[i].split("-")[0])-1;
					wordIndex = Integer.parseInt(checkVals[i].split("-")[1])-1;

					ProjectDAO.getInstance().regProjectActivityWord(checkVals[i].split("-")[0], checkVals[i].split("-")[1], activityNames[activityIndex], wordNames[wordIndex], lastInsertId);
				}
			}
		}



		out.println("<script language='javascript'>");
		out.println("alert('새로운 이름으로 저장되었습니다.');");
		out.println("self.location.replace('"+returnUrl+"');");
		out.println("</script>");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
