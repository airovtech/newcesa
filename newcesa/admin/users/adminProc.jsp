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
	String adminid	= StringUtils.defaultString(request.getParameter("adminid"));
	String password	= StringUtils.defaultString(request.getParameter("password"));
	String verify	= StringUtils.defaultString(request.getParameter("verify"));
	String email	= StringUtils.defaultString(request.getParameter("email"));
	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"), "0");


	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}

	// MD5
	if(!"".equals(password)){
		password = DigestUtils.md5Hex(password);
	}

	// proc variable
	String message = "Success";
	List params = new ArrayList();
	boolean result = false;

	try {
		if(mode.equals("Create")){

			if( "".equals(adminid) || "".equals(password) || "".equals(verify) || "".equals(email) ) {
				message = "Invalid Parameters";
			} else {
				// 회원 가입 조회
				RowSetMapper cRowSet = null;
		        cRowSet = AdminDAO.getInstance().chkAdminId(adminid);

		        if(cRowSet.next()){
					message = "사용할 수 없는 아이디 입니다.";
				} else {
				
					params.add(0, adminid);
					params.add(1, password);
					params.add(2, email);
					params.add(3, groupSeq);

					// 회원 정보 등록
					result = AdminDAO.getInstance().regAdmin(params);
				
					if(result){
						// 회원 가입 완료
						message = "회원 가입 처리가 완료되었습니다.";
					}
				}
			}

		} else if(mode.equals("Modify")){
			if(!"".equals(password)){
				params.add(0, password);
				params.add(1, email);
				params.add(2, groupSeq);
				params.add(3, seq);

			}
			else{
				params.add(0, email);
				params.add(1, groupSeq);
				params.add(2, seq);
			}
			result = AdminDAO.getInstance().modAdmin(params);
		
			if(result){
				message = "수정되었습니다.";
			}
		} else if(mode.equals("Delete")){

			result = AdminDAO.getInstance().delAdmin(seq);

			if(result){
				message = "삭제되었습니다.";
			}

		} else {
			message = "Error : Need Process Type";
		}

		out.println("<script language='javascript'>");
		out.println("alert('"+message+"');");
		out.println("self.location.replace('adminList.jsp');");
		out.println("</script>");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 
%>
<%@ include file="/include/footer.jsp" %>
