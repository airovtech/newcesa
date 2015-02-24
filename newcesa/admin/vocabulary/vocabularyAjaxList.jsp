<%@ page language="java" contentType="text/html; charset=utf-8"%><%@ include file="/include/header.jsp" %><%@ include file="/include/needAdminLogin.jsp" %><%
	
	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"));
	String seq = StringUtils.defaultString(request.getParameter("seq"));

	RowSetMapper cRowSet = null;
	
	//groupSeq =  "9";

	log.debug("groupSeq : "+groupSeq);
	log.debug("seq : "+seq);
	ArrayList arrData = new ArrayList();

	StringBuffer sbufTableChecked = new StringBuffer();
	StringBuffer sbufTable = new StringBuffer();
	int count = 0;

	try{

		cRowSet = VocabularyDAO.getInstance().getGroupSubList(seq);

		while(cRowSet.next()){
			arrData.add(cRowSet.getString("vocabulary_seq"));
		}

		//어휘 가져오기
		cRowSet = VocabularyDAO.getInstance().getVocabularyList(groupSeq);
		sbufTableChecked.append("<div style='padding-top:20px;padding-bottom:10px;font-weight:bold'>선택된 어휘</div>");
		sbufTableChecked.append("<table cellspacing='0' cellpadding='0' class='layerTable'>");
		sbufTableChecked.append("<colgroup>");
		sbufTableChecked.append("<col width='100px' />");
		sbufTableChecked.append("<col width='300px'/>");
		sbufTableChecked.append("<col width='100px'/>");
		sbufTableChecked.append("<col width='300px'/>");
		sbufTableChecked.append("</colgroup>");
		sbufTableChecked.append("<thead>");
		sbufTableChecked.append("<tr>");
		sbufTableChecked.append("<th>No.</th>");
		sbufTableChecked.append("<th>경험어휘</th>");
		sbufTableChecked.append("<th>No.</th>");
		sbufTableChecked.append("<th>경험어휘</th>");
		sbufTableChecked.append("</tr>");
		sbufTableChecked.append("</thead>");
		sbufTableChecked.append("<tbody>");
		while(cRowSet.next()){

			if(arrData.contains(cRowSet.getString("seq"))){
			
				count++;
				if(count%2==1){
					sbufTableChecked.append("<tr> \n");
				}
				
				sbufTableChecked.append("<td>"+count+"</td> \n");
				sbufTableChecked.append("<td><input type='checkbox' name='checkValue' value='"+cRowSet.getString("word")+"' style='vertical-align:-2px' "+(arrData.contains(cRowSet.getString("seq")) ? "checked" : "" )+"> "+cRowSet.getString("word")+"</td> \n");

				if(count%2==0){
					sbufTableChecked.append("</tr> \n");
				}
			}
		}
		
		if(count==0){
			if("0".equals(groupSeq)){
				sbufTableChecked.append("<tr><td colspan='4'>그룹을 선택하세요.</td></tr>");
			}
			else{
				sbufTableChecked.append("<tr><td colspan='4'>등록된 어휘가 없습니다.</td></tr>");
			}
		}
		else{
			if(count%2==1){
				sbufTableChecked.append("<td>&nbsp;</td> \n");
				sbufTableChecked.append("<td>&nbsp;</td> \n");
				sbufTableChecked.append("</tr> \n");
			}
		}
		sbufTableChecked.append("</tbody>");
		sbufTableChecked.append("</table>");




		count=0;
		cRowSet.beforeFirst();
		sbufTableChecked.append("<div style='padding-top:20px;padding-bottom:10px;font-weight:bold'>추가 어휘</div>");
		sbufTable.append("<table cellspacing='0' cellpadding='0' class='layerTable'>");
		sbufTable.append("<colgroup>");
		sbufTable.append("<col width='100px' />");
		sbufTable.append("<col width='300px'/>");
		sbufTable.append("<col width='100px'/>");
		sbufTable.append("<col width='300px'/>");
		sbufTable.append("</colgroup>");
		sbufTable.append("<thead>");
		sbufTable.append("<tr>");
		sbufTable.append("<th>No.</th>");
		sbufTable.append("<th>경험어휘</th>");
		sbufTable.append("<th>No.</th>");
		sbufTable.append("<th>경험어휘</th>");
		sbufTable.append("</tr>");
		sbufTable.append("</thead>");
		sbufTable.append("<tbody>");

		while(cRowSet.next()){
			if(!arrData.contains(cRowSet.getString("seq"))){	
				count++;
				if(count%2==1){
					sbufTable.append("<tr> \n");
				}
				
				sbufTable.append("<td>"+count+"</td> \n");
				sbufTable.append("<td><input type='checkbox' name='checkValue' value='"+cRowSet.getString("word")+"' style='vertical-align:-2px' "+(arrData.contains(cRowSet.getString("seq")) ? "checked" : "" )+"> "+cRowSet.getString("word")+"</td> \n");

				if(count%2==0){
					sbufTable.append("</tr> \n");
				}
			}
		}
		
		if(count==0){
			if("0".equals(groupSeq)){
				sbufTable.append("<tr><td colspan='4'>그룹을 선택하세요.</td></tr>");
			}
			else{
				sbufTable.append("<tr><td colspan='4'>등록된 어휘가 없습니다.</td></tr>");
			}
		}
		else{
			if(count%2==1){
				sbufTable.append("<td>&nbsp;</td> \n");
				sbufTable.append("<td>&nbsp;</td> \n");
				sbufTable.append("</tr> \n");
			}
		}
		sbufTable.append("</tbody>");
		sbufTable.append("</table>");

		sbufTable.append("<div style='padding-top:20px;padding-bottom:10px;text-align:center;font-weight:bold'><input type='button' onclick='goWordCheck();' value='경험어휘 선택' class='button blue' style='width:145px;' /> <input type='button' onclick='layerClose();' value='닫기' class='button white' style='width:65px;' /></div>");

	}
	catch(Exception e){
		log.debug("Exception:"+e);
	}
	out.println(sbufTableChecked.toString());
	out.println(sbufTable.toString());
%><%@ include file="/include/footer.jsp" %>
