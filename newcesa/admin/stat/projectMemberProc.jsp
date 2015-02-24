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
	String projectSeq	= StringUtils.defaultString(request.getParameter("projectSeq"));

	
	log.debug(mode);
	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;


	String property01 = StringUtils.defaultString(request.getParameter("property01"));
	String property02 = StringUtils.defaultString(request.getParameter("property02"));
	String property03 = StringUtils.defaultString(request.getParameter("property03"));
	String property04 = StringUtils.defaultString(request.getParameter("property04"));
	String property05 = StringUtils.defaultString(request.getParameter("property05"));
	String property06 = StringUtils.defaultString(request.getParameter("property06"));
	String property07 = StringUtils.defaultString(request.getParameter("property07"));
	String property08 = StringUtils.defaultString(request.getParameter("property08"));
	String property09 = StringUtils.defaultString(request.getParameter("property09"));
	String property10 = StringUtils.defaultString(request.getParameter("property10"));


	String[] memberid = request.getParameterValues("memberid");

	try {


		if(mode.equals("Create")){

			params.add(0, projectSeq);
			params.add(1, property01);
			params.add(2, property02);
			params.add(3, property03);
			params.add(4, property04);
			params.add(5, property05);
			params.add(6, property06);
			params.add(7, property07);
			params.add(8, property08);
			params.add(9, property09);
			params.add(10, property10);
		
			result = ProjectDAO.getInstance().regProjectProperties(params);

			if(result){

				for(int i=0;i<memberid.length;i++){
					//log.debug(memberid[i]);

					property01 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property01"));
					property02 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property02"));
					property03 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property03"));
					property04 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property04"));
					property05 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property05"));
					property06 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property06"));
					property07 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property07"));
					property08 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property08"));
					property09 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property09"));
					property10 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property10"));
					
					params = new ArrayList();
					params.add(0, projectSeq);
					params.add(1, memberid[i]);
					params.add(2, property01);
					params.add(3, property02);
					params.add(4, property03);
					params.add(5, property04);
					params.add(6, property05);
					params.add(7, property06);
					params.add(8, property07);
					params.add(9, property08);
					params.add(10, property09);
					params.add(11, property10);
					
					MemberDAO.getInstance().delMemberProperties(memberid[i], projectSeq);
					MemberDAO.getInstance().regMemberProperties(params);

				}

				message = "저장되었습니다.";
			}
			
		} else if(mode.equals("Modify")){
	
			params.add(0, property01);
			params.add(1, property02);
			params.add(2, property03);
			params.add(3, property04);
			params.add(4, property05);
			params.add(5, property06);
			params.add(6, property07);
			params.add(7, property08);
			params.add(8, property09);
			params.add(9, property10);
			params.add(10, projectSeq);

			result = ProjectDAO.getInstance().modProjectProperties(params);

			if(result){
				for(int i=0;i<memberid.length;i++){
					//log.debug(memberid[i]);
					property01 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property01"));
					property02 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property02"));
					property03 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property03"));
					property04 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property04"));
					property05 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property05"));
					property06 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property06"));
					property07 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property07"));
					property08 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property08"));
					property09 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property09"));
					property10 = StringUtils.defaultString(request.getParameter(memberid[i]+"_property10"));
					
					params = new ArrayList();
					params.add(0, projectSeq);
					params.add(1, memberid[i]);
					params.add(2, property01);
					params.add(3, property02);
					params.add(4, property03);
					params.add(5, property04);
					params.add(6, property05);
					params.add(7, property06);
					params.add(8, property07);
					params.add(9, property08);
					params.add(10, property09);
					params.add(11, property10);
					
					MemberDAO.getInstance().delMemberProperties(memberid[i], projectSeq);
					MemberDAO.getInstance().regMemberProperties(params);
					
				}

				message = "수정되었습니다.";
			}
		}
		else {
			message = "Error : Need Process Type";
		}

		out.println("<script language='javascript'>");
		out.println("alert('"+message+"');");
		out.println("self.location.replace('projectMemberList.jsp?projectSeq="+projectSeq+"');");
		out.println("</script>");


	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
