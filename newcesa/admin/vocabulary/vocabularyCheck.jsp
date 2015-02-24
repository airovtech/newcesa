<%
	/*============================================================================
	 * @ Description : 관리자 등록시 ID 체크
	 *
	 * 작성일 : 2011.04.18
	 * 작성자 : 이정순
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/needAdminLogin.jsp" %>
<%
	String word = StringUtils.defaultString(request.getParameter("word"));
	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"));
	
	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}

	log.debug(word);

	int code = 0;
	String message = "사용가능한 어휘 입니다.";

	if ("".equals(word) ){
		code = 1;
		message = "사용할 수 없습니다.";
	} else {
		
		RowSetMapper cRowSet = null;
		cRowSet = VocabularyDAO.getInstance().chkVocabulary(groupSeq, word);

		if(cRowSet.next()){
			code = 2;
			message = "이미 등록된 어휘 입니다.";
		}
	}

	out.println("{\"code\":\""+code+"\", \"msg\":\""+message+"\"}");
	
%>
<%@ include file="/include/footer.jsp" %>
