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
	String adminid = StringUtils.defaultString(request.getParameter("adminid"));

	int code = 0;
	String message = "사용가능한 아이디 입니다.";

	if ( "".equals(adminid) ){
		code = 1;
		message = "사용할 수 없습니다.";
	} else if ( adminid.length() < 3) {
		code = 1;
		message = "아이디 길이가 짧습니다.";

	} else {
		
		RowSetMapper cRowSet = null;
		cRowSet = AdminDAO.getInstance().chkAdminId(adminid);

		if(cRowSet.next()){
			if ( cRowSet.getInt("deleteYN") == 1 ) {
				code = 2;
				message = "삭제된 아이디 입니다.";
			} else {
				code = 3;
				message = "사용중인 아이디 입니다.";
			}
		}
	}

	out.println("{\"code\":\""+code+"\", \"msg\":\""+message+"\"}");
	
%>
<%@ include file="/include/footer.jsp" %>
