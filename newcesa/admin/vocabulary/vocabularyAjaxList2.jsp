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
	
	String groupSeq = StringUtils.defaultString(request.getParameter("seq"));
	
	JSONObject json = new JSONObject();
	JSONArray items = new JSONArray();
			
	RowSetMapper cRowSet = null;

	log.debug("groupSeq : "+groupSeq);

	try{
		cRowSet = VocabularyDAO.getInstance().getGroupSubList(groupSeq);

		while(cRowSet.next()){
			items.add(cRowSet.getString("word"));
		}
	}
	catch(Exception e){
		log.debug("Exception:"+e);
	}
	json.put("word", items);
	out.println(json);
%>
<%@ include file="/include/footer.jsp" %>
