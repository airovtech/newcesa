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
	m1 = 2;
	m2 = 2;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
	String seq = StringUtils.defaultString(request.getParameter("seq"));
	String currPage = StringUtils.defaultString(request.getParameter("currPage"),"1");
	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"),"0");

	
	
	String mode = "".equals(seq) ? "Create" : "Modify";
	RowSetMapper cRowSet = null;
	
	// Vars
	String groupName = StringUtils.defaultString(request.getParameter("groupName"), "");

	ArrayList arrData = new ArrayList();

	if(!"".equals(seq)){
		cRowSet = VocabularyDAO.getInstance().getGroupDetail(seq);
		
		if(cRowSet.next()){
			groupName = cRowSet.getString("group_name");
			groupSeq = cRowSet.getString("user_group");
		}

		cRowSet = VocabularyDAO.getInstance().getGroupSubList(seq);

		while(cRowSet.next()){
			arrData.add(cRowSet.getString("vocabulary_seq"));
		}
	}
	


	//그룹 select	1000으로 셋팅
	StringBuffer sbufSelect = new StringBuffer();
	if(!"".equals(seq)){
		cRowSet = GroupDAO.getInstance().getGroupDetail(groupSeq);
		if(cRowSet.next()){
			sbufSelect.append(cRowSet.getString("group_name"));
			sbufSelect.append("<input type='hidden' name='groupSeq' value='"+cRowSet.getString("seq")+"' />");
		}
	}
	else{
		cRowSet = GroupDAO.getInstance().groupList(1000, 1);
		sbufSelect.append("<select name='groupSeq' id='groupSeq' onchange='goSearch()'>");
		sbufSelect.append("<option value='0'>선택해 주세요.</option>");

		while(cRowSet.next()){
			if(groupSeq.equals(cRowSet.getString("seq"))){
				sbufSelect.append("<option value='"+cRowSet.getString("seq")+"' selected>"+cRowSet.getString("group_name")+"</option>");
			}
			else{
				sbufSelect.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("group_name")+"</option>");
			}
		}
		sbufSelect.append("</select>");
	}

	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}

	//어휘 가져오기
	StringBuffer sbufTableChecked = new StringBuffer();
	StringBuffer sbufTable = new StringBuffer();
	int count = 0;
	cRowSet = VocabularyDAO.getInstance().getVocabularyList(groupSeq);
	while(cRowSet.next()){

		if(arrData.contains(cRowSet.getString("seq"))){
		
			count++;
			if(count%2==1){
				sbufTableChecked.append("<tr> \n");
			}
			
			sbufTableChecked.append("<td class='ta_c'>"+count+"</td> \n");
			sbufTableChecked.append("<td class='ta'><input type='checkbox' name='checkValue' value='"+cRowSet.getString("seq")+"' style='vertical-align:-2px' "+(arrData.contains(cRowSet.getString("seq")) ? "checked" : "" )+"> "+cRowSet.getString("word")+"</td> \n");

			if(count%2==0){
				sbufTableChecked.append("</tr> \n");
			}
		}
	}
	
	if(count==0){
		if("0".equals(groupSeq)){
			sbufTableChecked.append("<tr><td class='ta_c' colspan='4'>그룹을 선택하세요.</td></tr>");
		}
		else{
			sbufTableChecked.append("<tr><td class='ta_c' colspan='4'>등록된 어휘가 없습니다.</td></tr>");
		}
	}
	else{
		if(count%2==1){
			sbufTableChecked.append("<td class='ta_c'>&nbsp;</td> \n");
			sbufTableChecked.append("<td class='ta'>&nbsp;</td> \n");
			sbufTableChecked.append("</tr> \n");
		}
	}


	count=0;
	cRowSet.beforeFirst();
	while(cRowSet.next()){
		if(!arrData.contains(cRowSet.getString("seq"))){	
			count++;
			if(count%2==1){
				sbufTable.append("<tr> \n");
			}
			
			sbufTable.append("<td class='ta_c'>"+count+"</td> \n");
			sbufTable.append("<td class='ta'><input type='checkbox' name='checkValue' value='"+cRowSet.getString("seq")+"' style='vertical-align:-2px' "+(arrData.contains(cRowSet.getString("seq")) ? "checked" : "" )+"> "+cRowSet.getString("word")+"</td> \n");

			if(count%2==0){
				sbufTable.append("</tr> \n");
			}
		}
	}
	
	if(count==0){
		if("0".equals(groupSeq)){
			sbufTable.append("<tr><td class='ta_c' colspan='4'>그룹을 선택하세요.</td></tr>");
		}
		else{
			sbufTable.append("<tr><td class='ta_c' colspan='4'>등록된 어휘가 없습니다.</td></tr>");
		}
	}
	else{
		if(count%2==1){
			sbufTable.append("<td class='ta_c'>&nbsp;</td> \n");
			sbufTable.append("<td class='ta'>&nbsp;</td> \n");
			sbufTable.append("</tr> \n");
		}
	}
%>
<script type="text/javascript">
function goSubmit(){
	var f = document.frm;

	if ( isEmpty(f.groupName.value)) {
		alert("경험어휘 그룹명을 입력하세요.");
		f.groupName.focus();
		return;
	}
	
	if (f.groupSeq.value=="0") {
		alert("그룹을 선택하세요.");
		f.groupSeq.focus();
		return;
	}

	f.action = "groupProc.jsp";
	f.submit();
}

function goSearch(){
	var f = document.frm;
	f.submit();
}

function goCopy() {
	var f = document.frm;
	f.seq.value = "";
	if ( isEmpty(f.groupName.value)) {
		alert("경험어휘 그룹명을 입력하세요.");
		f.groupName.focus();
		return;
	}
	if (f.groupSeq.value=="0") {
		alert("그룹을 선택하세요.");
		f.groupSeq.focus();
		return;
	}

	if(confirm("그룹을 복사합니다.")){
		f.mode.value = "Create";
		f.action = "groupProc.jsp";
		f.submit();
	}
}
</script>

<div class="top_title">경험어휘 그룹<%="".equals(seq) ? "등록" : "수정"%></div>

<form name="frm" id="frm" method="post" action="">
<div>
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="seq" value="<%=seq%>" />
	<input type="hidden" name="orgGroupName" value="<%=groupName%>" />
</div>
<div id="form_warp">
	<dl>
		<dt class="inpt_dt">그룹명</dt>
		<dd class="inpt_dd">
			<input type="text" name="groupName" id="groupName" value="<%=groupName%>" maxlength="50"  size="40"/>
		</dd>
		<%
			if(sGroup.equals("0")){
		%>
		<dt class="inpt_dt">그룹</dt>
		<dd class="inpt_dd"><%=sbufSelect%></dd>
		<%
			}
			else{
		%>
			<input type="hidden" name="groupSeq" id="groupSeq" value="<%=sGroup%>" />
		<%
			}
		%>
	</dl>         
</div>
<div class="btn_group_right_align"></div>
<div id="roof_table">
	<div style='padding-bottom:5px;padding-top:10px;font-weight:bold'>- 선택 된 어휘</div>
	<table cellspacing="0" cellpadding="0" width="905px">
		<colgroup>
			<col width="150px" />
			<col width="300px"/>
			<col width="150px"/>
			<col width="305px"/>
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">경험어휘</th>
			<th class="ta_c">No.</th>
			<th class="ta_c">경험어휘</th>
		</tr>
		</thead>
		<tbody>

		<%=sbufTableChecked%>


		</tbody>
	</table>
	<br/>
	<div style='padding-bottom:5px;font-weight:bold'>- 추가</div>
	<table cellspacing="0" cellpadding="0" width="905px">
		<colgroup>
			<col width="150px" />
			<col width="300px"/>
			<col width="150px"/>
			<col width="305px"/>
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">경험어휘</th>
			<th class="ta_c">No.</th>
			<th class="ta_c">경험어휘</th>
		</tr>
		</thead>
		<tbody>
		<%=sbufTable%>
		</tbody>
	</table>
</div>
</form>

<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='vocabularyList.jsp'" value="Cancel" class="button white" style="width:50px;" />

	<input type="button" onclick="goSubmit();" value="Save" class="button black" style="width:50px;" />
	<input type="button" onclick="goCopy();" value="Copy" class="button black" style="width:50px;" />
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
