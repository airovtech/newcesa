<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/needAdminLogin.jsp" %>
<%
	// Vars
	RowSetMapper cRowSet = null;
	ArrayList params = null;
	boolean check_result = false;
	String result_code = "";
	String result_msg = "";

	try {

		// Param
		String mode		= StringUtils.defaultString(request.getParameter("mode"));
		String tb_id	= StringUtils.defaultString(request.getParameter("tb_id"));
		String id		= StringUtils.defaultString(request.getParameter("id")).trim();
		String name		= StringUtils.defaultString(request.getParameter("name")).trim();

		long tstamp = DateUtil.getTimestamp();

		if("del".equals(mode)){
			// db 데이터 삭제
			check_result = BbsDAO.getInstance().del(tb_id);
			if(check_result){
				result_msg = "삭제되었습니다.";
			}
			else{
				result_msg = "삭제 중 오류가 발생했습니다. log를 확인해주세요.";
			}
		}
		if("delId".equals(mode)){
			// db 데이터 삭제
			check_result = BbsDAO.getInstance().delId(id);
			if(check_result){
				result_msg = "삭제되었습니다.";
			}
			else{
				result_msg = "삭제 중 오류가 발생했습니다. log를 확인해주세요.";
			}
		}
		if("delName".equals(mode)){
			// db 데이터 삭제
			check_result = BbsDAO.getInstance().delName(name);
			if(check_result){
				result_msg = "삭제되었습니다.";
			}
			else{
				result_msg = "삭제 중 오류가 발생했습니다. log를 확인해주세요.";
			}
		}

		out.println("<script type='text/javascript'>");
		if(result_msg.indexOf("오류")!=-1)
			out.println("alert('"+result_msg+"');");
		out.println("self.location.replace('bbsList.jsp');");
		out.println("</script>");
		

	}
	catch(Exception e){
		log.debug("sales register Exception:"+e);
		out.println("<pre>");
		e.printStackTrace(new PrintWriter(out));
		out.println("</pre>");
	}

%>
<%@ include file="/include/footer.jsp" %>
