<%
	/*============================================================================
	 * @ Description : 관리자 정보 등록, 수정 폼
	 *
	 * 작성일 : 2011.04.18
	 * 작성자 : 이정순
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerAdmin.jsp" %>
<%
	m1 = 4;
	m2 = 1;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
	String currPage = StringUtils.defaultString(request.getParameter("currPage"),"1");
	String projectSeq = StringUtils.defaultString(request.getParameter("projectSeq"),"0");

	//out.println("projectSeq : "+projectSeq);
	RowSetMapper cRowSet = null;
	
	// Vars
	String groupSeq = "";
	

	String memberid = "";

	String property01 = "";
	String property02 = "";
	String property03 = "";
	String property04 = "";
	String property05 = "";
	String property06 = "";
	String property07 = "";
	String property08 = "";
	String property09 = "";
	String property10 = "";

	String mode = "Create";

	
	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}
	
	cRowSet = ProjectDAO.getInstance().getProjectList(groupSeq, 1000, 1);

	StringBuffer sbufSelect = new StringBuffer();
	sbufSelect.append("<select name='projectSeq' onchange='changeProject();'>");
	sbufSelect.append("<option value=''>프로젝트를 선택해 주세요.</option>");
	while(cRowSet.next()){
		if(cRowSet.getString("seq").equals(projectSeq)){
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"' selected>"+cRowSet.getString("project_name")+" ("+cRowSet.getString("group_name")+") "+cRowSet.getString("start_date")+"~"+cRowSet.getString("end_date")+"</option> \n");
		}
		else{
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("project_name")+" ("+cRowSet.getString("group_name")+") "+cRowSet.getString("start_date")+"~"+cRowSet.getString("end_date")+"</option> \n");
		}
	}
	sbufSelect.append("</select>");


	//항목 불러오기
	cRowSet = ProjectDAO.getInstance().projectPropertiesList(projectSeq);
	if(cRowSet.next()){
		property01 = cRowSet.getString("property01");
		property02 = cRowSet.getString("property02");
		property03 = cRowSet.getString("property03");
		property04 = cRowSet.getString("property04");
		property05 = cRowSet.getString("property05");
		property06 = cRowSet.getString("property06");
		property07 = cRowSet.getString("property07");
		property08 = cRowSet.getString("property08");
		property09 = cRowSet.getString("property09");
		property10 = cRowSet.getString("property10");

		mode = "Modify";
	}

	//멤버 목록  프로퍼티 
	cRowSet = MemberDAO.getInstance().getMemberPropertiesList(projectSeq);
	//out.println(cRowSet.size());



	
%>
<script type="text/javascript">
function changeProject(){
	var frm = document.frm;
	frm.action = "projectMemberList.jsp";
	frm.submit();
}


function goSubmit(){
	var frm = document.frm;
	frm.action = "projectMemberProc.jsp";
	frm.submit();
	
}

$(window).load(function() {
	document.getElementById("all").style.minWidth = "1200px";
});


</script>

<div class="top_title">프로젝트 별 사용자 정보 설정</div>
<form name="frm" id="frm" method="post" action="projectMemberList.jsp">
<input type="hidden" name="mode" value="<%=mode%>" />
<div>

</div>
<div id="form_warp">
	<dl>

		<dt class="inpt_dt">프로젝트명</dt>
		<dd class="inpt_dd">
			<%=sbufSelect%>
		</dd>
	</dl>         
</div>
<div style="padding-top:50px;">
<table cellspacing="0" cellpadding="0" class="propertiesTable" id="propertiesTable" width="905">
	<tr>
		<th>항목</th>
		<th><input type="text" name="property01" id="property01" value="<%=property01%>" size="5" /></th>
		<th><input type="text" name="property02" id="property02" value="<%=property02%>" size="5" /></th>
		<th><input type="text" name="property03" id="property03" value="<%=property03%>" size="5" /></th>
		<th><input type="text" name="property04" id="property04" value="<%=property04%>" size="5" /></th>
		<th><input type="text" name="property05" id="property05" value="<%=property05%>" size="5" /></th>
		<th><input type="text" name="property06" id="property06" value="<%=property06%>" size="5" /></th>
		<th><input type="text" name="property07" id="property07" value="<%=property07%>" size="5" /></th>
		<th><input type="text" name="property08" id="property08" value="<%=property08%>" size="5" /></th>
		<th><input type="text" name="property09" id="property09" value="<%=property09%>" size="5" /></th>
		<th><input type="text" name="property10" id="property10" value="<%=property10%>" size="5" /></th>
	</tr>

<%
	while(cRowSet.next()){
		memberid = cRowSet.getString("memberid");
		property01 = cRowSet.getString("property01");
		property02 = cRowSet.getString("property02");
		property03 = cRowSet.getString("property03");
		property04 = cRowSet.getString("property04");
		property05 = cRowSet.getString("property05");
		property06 = cRowSet.getString("property06");
		property07 = cRowSet.getString("property07");
		property08 = cRowSet.getString("property08");
		property09 = cRowSet.getString("property09");
		property10 = cRowSet.getString("property10");
%>
	<tr>
		<td><%=memberid%><input type="hidden" name="memberid" id="memberid" value="<%=memberid%>"/></td>
		<td><input type="text" name="<%=memberid%>_property01" id="<%=memberid%>_property01" value="<%=property01%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property02" id="<%=memberid%>_property02" value="<%=property02%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property03" id="<%=memberid%>_property03" value="<%=property03%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property04" id="<%=memberid%>_property04" value="<%=property04%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property05" id="<%=memberid%>_property05" value="<%=property05%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property06" id="<%=memberid%>_property06" value="<%=property06%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property07" id="<%=memberid%>_property07" value="<%=property07%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property08" id="<%=memberid%>_property08" value="<%=property08%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property09" id="<%=memberid%>_property09" value="<%=property09%>" size="5" /></td>
		<td><input type="text" name="<%=memberid%>_property10" id="<%=memberid%>_property10" value="<%=property10%>" size="5" /></td>
	</tr>
<%
	}	
%>
</table>
</div>
</form>



<div class="btn_group_right_align">
	<input type="button" onclick="goSubmit();" value="저장" class="button black" style="width:50px;" />
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
