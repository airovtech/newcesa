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

	//log.debug("userList : "+userList);
	String [] userLists = userList.split(",");

	

	ArrayList arrUserList = new ArrayList();
	for(int i=0;i<userLists.length;i++){
		log.debug("userLists : "+userLists[i]);
		arrUserList.add(userLists[i]);
	}

	//log.debug("size : "+arrUserList.size());


	JSONObject json = new JSONObject();
	JSONObject wordJson = new JSONObject();
	JSONArray items = new JSONArray();
	JSONArray jaPoint = new JSONArray();
	JSONArray chartList = new JSONArray();


	//log.debug("count : "+userLists.length);
	//log.debug("type : "+type);

	int userCount = userLists.length;
	int point = 0;
	int maxPoint = 0;
	float avg = 0.0f;
	DecimalFormat exFormat = new DecimalFormat("#.#"); 

	RowSetMapper cRowSet = null;


	ArrayList arrCheckedData = new ArrayList();

	ArrayList arrActivityData = new ArrayList();
	ArrayList arrWordData = new ArrayList();
	ArrayList arrPointData = new ArrayList();

	//체크 리스트를 가져온다.
	cRowSet = ProjectDAO.getInstance().projectActivityWordList(projectSeq);
	while(cRowSet.next()){
		arrCheckedData.add(cRowSet.getString("checked_activity")+"_"+cRowSet.getString("checked_word"));
	}

	
	StringBuffer sbufTable = new StringBuffer();
	StringBuffer sbufEtcTable = new StringBuffer();
	StringBuffer sbufJsonData = new StringBuffer();
	int tempRow = 0;
	int tempCol = 0;
	int arrPointCount = 0;
	boolean bEtcData = false;
	try {
	
		cRowSet = ProjectDAO.getInstance().projectActivityList(projectSeq);

		sbufTable.append("<div style='font-weight:bold;padding-top:10px;padding-bottom:5px;'>참여인원 : "+userCount+"명 ("+(type.equals("sum") ? "합계": "평균" )+")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sbufTable.append("<input type=\"button\" onclick=\"changeChart('line');\" value=\"라인 그래프\" class=\"button white\" style=\"width:80px;\" />&nbsp;&nbsp;");
		sbufTable.append("<input type=\"button\" onclick=\"changeChart('column');\" value=\"막대 그래프\" class=\"button white\" style=\"width:80px;\" />&nbsp;&nbsp;");
		sbufTable.append("<input type=\"button\" onclick=\"goDownload();\" value=\"엑셀 다운로드\" class=\"button black\" style=\"width:100px;\" />");
		sbufTable.append("</div>");

		//sbufTable.append("<div class=\"btn_group_right_align\" style=\"pading-top:10px;\">");
		//sbufTable.append("</div>");

		sbufTable.append("<table cellspacing=\"0\" cellpadding=\"0\" id=\"resultTable\" class=\"propertiesTable\"> \n");
		sbufTable.append("<tr> \n");
		sbufTable.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th> \n");


		//activity
		while(cRowSet.next()){
			arrActivityData.add(tempCol, cRowSet.getString("activity"));
			sbufTable.append("<th>"+cRowSet.getString("activity")+"</th> \n");

			

			tempCol++;
		}
		sbufTable.append("</tr> \n");
		

		
		cRowSet = ProjectDAO.getInstance().projectWordList(projectSeq);

		//word 및 point
		while(cRowSet.next()){

			sbufTable.append("<tr> \n");
			sbufTable.append("<td>"+cRowSet.getString("word")+"</td> \n");
			arrWordData.add(tempRow, cRowSet.getString("word"));

			tempRow++;
			for(int i=1;i<=tempCol;i++){
				if(arrCheckedData.contains(i+"_"+tempRow)){
					//점수를 가져온다.
					point = MemberDAO.getInstance().getMemberCheckedStatPoint(projectSeq, userList,(String)arrActivityData.get((i-1)), cRowSet.getString("word"));
					
					if(type.equals("sum")){
						sbufTable.append("<td>"+point+"</td> \n");
						arrPointData.add(arrPointCount, point);

						if(point>=maxPoint){
							maxPoint = point;
						}
					}
					else{
						avg = (float)point/userCount;
						sbufTable.append("<td>"+exFormat.format(avg)+"</td> \n");
						arrPointData.add(arrPointCount, exFormat.format(avg));
					}
					
					arrPointCount++;
				}
				else{
					sbufTable.append("<td>-</td> \n");
					arrPointData.add(arrPointCount, 0);
					arrPointCount++;
				}
				
			}
			sbufTable.append("</tr> \n");
			

		}

		//기타를 리스트를 가져온다.
		sbufTable.append("<tr> \n");
		sbufTable.append("<td>기타</td> \n");

		sbufEtcTable.append("<div style='font-weight:bold;padding-top:10px;padding-bottom:5px;'>기타 어휘</div>");
		sbufEtcTable.append("<table cellspacing=\"0\" cellpadding=\"0\" id=\"resultTable\" class=\"propertiesTable\"> \n");
		sbufEtcTable.append("<tr> \n");
		sbufEtcTable.append("<th>참가자</th> \n");
		sbufEtcTable.append("<th>Activity</th> \n");
		sbufEtcTable.append("<th>어휘</th> \n");
		sbufEtcTable.append("<th>평가점수</th> \n");
		sbufEtcTable.append("</tr> \n");


		for(int i=1;i<=tempCol;i++){
			point = 0;
			cRowSet = MemberDAO.getInstance().getMemberCheckedEtcStatPoint(projectSeq, (String)arrActivityData.get((i-1)));

			while(cRowSet.next()){
				if(arrUserList.contains(cRowSet.getString("memberid"))){
					//sbufEtcTable.append(cRowSet.getString("memberid")+" - "+(String)arrActivityData.get((i-1))+" - "+cRowSet.getString("word")+ "-"+cRowSet.getString("check_value")+"</br>");
					sbufEtcTable.append("<tr> \n");
					sbufEtcTable.append("<td>"+cRowSet.getString("memberid")+"</td> \n");
					sbufEtcTable.append("<td>"+cRowSet.getString("activity")+"</td> \n");
					sbufEtcTable.append("<td>"+cRowSet.getString("word")+"</td> \n");
					sbufEtcTable.append("<td>"+cRowSet.getString("check_value")+"</td> \n");
					sbufEtcTable.append("</tr> \n");
					
					point += cRowSet.getInt("check_value");
					bEtcData = true;
				}
			}
			
			log.debug("point : "+point);
			if(point>=maxPoint){
				maxPoint = point;
			}

			if(type.equals("sum")){
				sbufTable.append("<td>"+point+"</td> \n");
				arrPointData.add(arrPointCount, point);
			}
			else{


				avg = (float)point/userCount;
				sbufTable.append("<td>"+exFormat.format(avg)+"</td> \n");
				arrPointData.add(arrPointCount, exFormat.format(avg));
			}
			arrPointCount++;
			
		}

		sbufTable.append("</tr> \n");
		sbufTable.append("</table> \n");

		sbufEtcTable.append("</table> \n");


		
		arrPointCount = 0;
		for(int i=0;i<arrWordData.size();i++){
			
			wordJson = new JSONObject();
			jaPoint = new JSONArray();

			items = new JSONArray();
			items.add("Activity");
			items.add(arrWordData.get(i));
			jaPoint.add(items);

			//log.debug("================================================");
			for(int j=0;j<arrActivityData.size();j++){

				items = new JSONArray();
				items.add(arrActivityData.get(j));

				if(type.equals("sum")){
					items.add(arrPointData.get(arrPointCount));
				}
				else{
					items.add(Float.parseFloat(""+arrPointData.get(arrPointCount)));
				}
				
				arrPointCount++;
				jaPoint.add(items);
			}

			wordJson.put("title", arrWordData.get(i));
			wordJson.put("data",jaPoint);
			
			chartList.add(wordJson);

		}

		//기타 추가
		wordJson = new JSONObject();
		jaPoint = new JSONArray();

		items = new JSONArray();
		items.add("Activity");
		items.add("기타");
		jaPoint.add(items);

		//log.debug("================================================");
		for(int j=0;j<arrActivityData.size();j++){

			items = new JSONArray();
			items.add(arrActivityData.get(j));

			if(type.equals("sum")){
				items.add(arrPointData.get(arrPointCount));
			}
			else{
				items.add(Float.parseFloat(""+arrPointData.get(arrPointCount)));
			}
			
			arrPointCount++;
			jaPoint.add(items);
		}

		wordJson.put("title", "기타");
		wordJson.put("data", jaPoint);
		
		chartList.add(wordJson);

	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	} 

	//out.println(sbufTable);
	json.put("html", sbufTable.toString());

	json.put("maxPoint", maxPoint);
	if(bEtcData){
		json.put("htmlEtc", sbufEtcTable.toString());
	}
	else{
		json.put("htmlEtc", "");
	}


	json.put("chartData", chartList);


	//log.debug(json);
	out.println(json);

%>
<%@ include file="/include/footer.jsp" %>
