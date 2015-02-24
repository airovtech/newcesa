<%@ page language="java" contentType="application/vnd.ms-excel; name='excel', text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/include/header.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv=Content-Type content="application/vnd.ms-excel ; charset=utf-8">
		
	</head>
	<body>
<%

	String projectSeq = StringUtils.defaultString(request.getParameter("projectSeq"));	
	String userList = StringUtils.defaultString(request.getParameter("userList"));
	String type = StringUtils.defaultString(request.getParameter("type"));

	log.debug(projectSeq);
	log.debug(userList);
	log.debug(type);

	String [] userLists = userList.split(",");

	ArrayList arrUserList = new ArrayList();
	for(int i=0;i<userLists.length;i++){
		arrUserList.add(userLists[i]);
	}

	


	JSONObject json = new JSONObject();
	JSONObject wordJson = new JSONObject();
	JSONArray items = new JSONArray();
	JSONArray jaPoint = new JSONArray();
	JSONArray chartList = new JSONArray();


	//log.debug("count : "+userLists.length);
	//log.debug("type : "+type);

	int userCount = userLists.length;
	int point = 0;
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

	
	StringBuffer sbufTableSum = new StringBuffer();
	StringBuffer sbufTableAvg = new StringBuffer();
	StringBuffer sbufEtcTable = new StringBuffer();
	StringBuffer sbufJsonData = new StringBuffer();
	int tempRow = 0;
	int tempCol = 0;
	int arrPointCount = 0;
	boolean bEtcData = false;
	try {
	
		cRowSet = ProjectDAO.getInstance().projectActivityList(projectSeq);

		sbufTableSum.append("<div style='font-weight:bold;padding-top:10px;padding-bottom:5px;'>참여인원 : "+userCount+"명 (합계)</div>");
		sbufTableSum.append("<table border=\"1\"> \n");
		sbufTableSum.append("<tr> \n");
		sbufTableSum.append("<th></th> \n");

		sbufTableAvg.append("<div style='font-weight:bold;padding-top:10px;padding-bottom:5px;'>참여인원 : "+userCount+"명 (평균)</div>");
		sbufTableAvg.append("<table border=\"1\"> \n");
		sbufTableAvg.append("<tr> \n");
		sbufTableAvg.append("<th></th> \n");




		//activity
		while(cRowSet.next()){
			arrActivityData.add(tempCol, cRowSet.getString("activity"));
			sbufTableSum.append("<th>"+cRowSet.getString("activity")+"</th> \n");
			sbufTableAvg.append("<th>"+cRowSet.getString("activity")+"</th> \n");
			tempCol++;
		}
		sbufTableSum.append("</tr> \n");
		sbufTableAvg.append("</tr> \n");
		

		
		cRowSet = ProjectDAO.getInstance().projectWordList(projectSeq);

		//word 및 point
		while(cRowSet.next()){

			sbufTableSum.append("<tr> \n");
			sbufTableSum.append("<td>"+cRowSet.getString("word")+"</td> \n");

			sbufTableAvg.append("<tr> \n");
			sbufTableAvg.append("<td>"+cRowSet.getString("word")+"</td> \n");

			arrWordData.add(tempRow, cRowSet.getString("word"));

			tempRow++;
			for(int i=1;i<=tempCol;i++){
				if(arrCheckedData.contains(i+"_"+tempRow)){
					//점수를 가져온다.
					point = MemberDAO.getInstance().getMemberCheckedStatPoint(projectSeq, userList,(String)arrActivityData.get((i-1)), cRowSet.getString("word"));
					

					sbufTableSum.append("<td>"+point+"</td> \n");
					arrPointData.add(arrPointCount, point);


					avg = (float)point/userCount;
					sbufTableAvg.append("<td>"+exFormat.format(avg)+"</td> \n");
					arrPointData.add(arrPointCount, exFormat.format(avg));

					
					arrPointCount++;
				}
				else{
					sbufTableSum.append("<td></td> \n");
					sbufTableAvg.append("<td></td> \n");

					arrPointData.add(arrPointCount, 0);
					arrPointCount++;
				}
				
			}
			sbufTableSum.append("</tr> \n");
			sbufTableAvg.append("</tr> \n");
			

		}
		//기타를 리스트를 가져온다.
		sbufTableSum.append("<tr> \n");
		sbufTableSum.append("<td>기타</td> \n");

		sbufTableAvg.append("<tr> \n");
		sbufTableAvg.append("<td>기타</td> \n");

		sbufEtcTable.append("<div style='font-weight:bold;padding-top:10px;padding-bottom:5px;'>기타 어휘</div>");
		sbufEtcTable.append("<table border=\"1\"> \n");
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


			sbufTableSum.append("<td>"+point+"</td> \n");
			arrPointData.add(arrPointCount, point);
			
			avg = (float)point/userCount;
			sbufTableAvg.append("<td>"+exFormat.format(avg)+"</td> \n");
			arrPointData.add(arrPointCount, exFormat.format(avg));
			

			arrPointCount++;
			
		}

		sbufTableSum.append("</tr> \n");
		sbufTableSum.append("</table> \n");

		sbufTableAvg.append("</tr> \n");
		sbufTableAvg.append("</table> \n");



		sbufEtcTable.append("</table> \n");
	

		response.setContentType("application/vnd.ms-excel; charset=utf-8;" );
		response.setHeader("Content-Disposition","attachment;filename=cesa_"+DateUtil.getToday("YYYYMMDDHHMMSS")+".xls");
		response.setHeader("Content-Transfer-Encoding", "binary");
					
		response.setHeader("Pragma","public");
		response.setHeader("Expires","0");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-Type","application/force-download");
		response.setHeader("Content-Type","application/vnd.ms-excel");

		out.println(sbufTableSum);
		out.println("<br/>");
		out.println(sbufTableAvg);
		out.println("<br/>");
		out.println(sbufEtcTable);

		

	
	}           
	catch(Exception e){
		log.debug("admin register Exception:"+e);
	}
%>
<%@ include file="/include/footer.jsp"%>