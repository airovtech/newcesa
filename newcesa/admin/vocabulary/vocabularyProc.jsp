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
		if(mode.equals("Create")){

			if( "".equals(word) || "".equals(groupSeq) ) {
				message = "Invalid Parameters";
			} else {

				// 회원 가입 조회
				RowSetMapper cRowSet = null;
		        cRowSet = VocabularyDAO.getInstance().chkVocabulary(groupSeq, word);

		        if(cRowSet.next()){
					message = "사용할 수 없는 어휘 입니다.";
				} else {
				
					params.add(0, word);
					params.add(1, groupSeq);

					// 회원 정보 등록
					result = VocabularyDAO.getInstance().regVocabulary(params);
				
					if(result){
						// 회원 가입 완료
						message = "등록이 완료되었습니다.";
					}
				}
			}
		} else if(mode.equals("Modify")){

			params.add(0, word);
			params.add(1, groupSeq);
			params.add(2, seq);

			log.debug(word);
			log.debug(groupSeq);
			log.debug(seq);

			result = VocabularyDAO.getInstance().modVocabulary(params);
		
			if(result){
				message = "수정되었습니다.";
			}
		} 
		else if(mode.equals("Delete")){
			result = VocabularyDAO.getInstance().delVocabulary(seq);
		
			if(result){
				message = "삭제되었습니다.";
			}
		}
		else {
			message = "Error : Need Process Type";
		}

		out.println("<script language='javascript'>");
		out.println("alert('"+message+"');");
		out.println("self.location.replace('vocabularyList.jsp');");
		out.println("</script>");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
