<%
	/*============================================================================
	 * @ Description : Free app party Admin Login
	 *
	 * 작성일 : 2011.04.14
	 * 작성자 :	최형범
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/header.jsp" %>
<%
	// Params
	String memberId = StringUtils.defaultString(request.getParameter("memberId"));
	String memberPasswd = StringUtils.defaultString(request.getParameter("memberPasswd"));
	String projectSeq = StringUtils.defaultString(request.getParameter("projectSeq"));

	// MD5
	memberPasswd = DigestUtils.md5Hex(memberPasswd);

	// Vars
	RowSetMapper cRowSet = null;
	String check_result = "";
	String message = "";



	log.debug("memberId : "+memberId);
	log.debug("memberPasswd : "+memberPasswd);
	log.debug("projectSeq : "+projectSeq);


	// 관리자 아디이와 비밀번호를 확인한다.
	check_result = MemberDAO.getInstance().isMember(memberId, memberPasswd, projectSeq);

	if(check_result.equals("success")){
		//String memberId, String memberPasswd, String projectSeq)
		//기본정보를 가져온다.
		cRowSet = MemberDAO.getInstance().getMemberDetail(memberId, memberPasswd, projectSeq);

		// session 영역에 adminid를 저장한다.
		session.setAttribute("sMemberId", memberId);
		
		if(cRowSet.next()){
			session.setAttribute("sMemberSeq", StringUtils.defaultString(cRowSet.getString("seq"),""));
			session.setAttribute("sProjectSeq", projectSeq);
		}

		session.setMaxInactiveInterval(60*60*48);	// session time : 24시간
		session.getMaxInactiveInterval();

		response.sendRedirect("/front/index.jsp");
	}
	else if(check_result.equals("fail")) {

		message = "비밀번호가 일치하지 않습니다.";


		out.println("<script language='javascript'>");
		out.println("alert('"+message+"');");
		out.println("self.location.replace('index.jsp?pNo="+projectSeq+"');");
		out.println("</script>");
	}

%>
<%@ include file="/include/footer.jsp" %>
