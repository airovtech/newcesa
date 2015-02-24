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
	String projectSeq = StringUtils.defaultString(request.getParameter("projectSeq"));	
	String userList = StringUtils.defaultString(request.getParameter("userList"));
	String type = StringUtils.defaultString(request.getParameter("type"));


	
	String [] userLists = userList.split(",");

	log.debug("count : "+userLists.length);
	log.debug("type : "+type);

	int userCount = userLists.length;
	int point = 0;
	float avg = 0.0f;
	DecimalFormat exFormat = new DecimalFormat("#.#"); 

	RowSetMapper cRowSet = null;


	ArrayList arrCheckedData = new ArrayList();
	ArrayList arrActivityData = new ArrayList();

	//체크 리스트를 가져온다.
	cRowSet = ProjectDAO.getInstance().projectActivityWordList(projectSeq);
	while(cRowSet.next()){
		arrCheckedData.add(cRowSet.getString("checked_activity")+"_"+cRowSet.getString("checked_word"));
	}

	
	StringBuffer sbufTable = new StringBuffer();
	int tempRow = 0;
	int tempCol = 0;
	try {
	
		cRowSet = ProjectDAO.getInstance().projectActivityList(projectSeq);

		sbufTable.append("<div style='font-weight:bold;padding-top:10px;padding-bottom:5px;'>참여인원 : "+userCount+"명 ("+(type.equals("sum") ? "합계": "평균" )+")</div>");
		sbufTable.append("<table cellspacing=\"0\" cellpadding=\"0\" class=\"propertiesTable\"> \n");
		sbufTable.append("<tr> \n");
		sbufTable.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th> \n");
		while(cRowSet.next()){
			arrActivityData.add(tempCol, cRowSet.getString("activity"));
			sbufTable.append("<th>"+cRowSet.getString("activity")+"</th> \n");
			tempCol++;
		}
		sbufTable.append("</tr> \n");

		
		cRowSet = ProjectDAO.getInstance().projectWordList(projectSeq);
		while(cRowSet.next()){
			tempRow++;
			sbufTable.append("<tr> \n");
			sbufTable.append("<td>"+cRowSet.getString("word")+"</td> \n");
			for(int i=1;i<=tempCol;i++){
				if(arrCheckedData.contains(i+"_"+tempRow)){
					//점수를 가져온다.

					point = MemberDAO.getInstance().getMemberCheckedStatPoint(projectSeq, userList,(String)arrActivityData.get((i-1)), cRowSet.getString("word"));
					if(type.equals("sum")){
						sbufTable.append("<td>"+point+"</td> \n");
					}
					else{
						avg = (float)point/userCount;
						sbufTable.append("<td>"+exFormat.format(avg)+"</td> \n");
					}
					
					//log.debug(MemberDAO.getInstance().getMemberCheckedStatPoint(projectSeq, userList,(String)arrActivityData.get((i-1)), cRowSet.getString("word")));

				}
				else{
					sbufTable.append("<td>-</td> \n");
				}
				
			}
			sbufTable.append("</tr> \n");
		}
		sbufTable.append("</table> \n");

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 

	out.println(sbufTable);
%>
<%@ include file="/include/footer.jsp" %>
