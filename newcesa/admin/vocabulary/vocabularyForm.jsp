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
	m2 = 1;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
	String seq = StringUtils.defaultString(request.getParameter("seq"));
	String currPage = StringUtils.defaultString(request.getParameter("currPage"),"1");
	String mode = "".equals(seq) ? "Create" : "Modify";
	RowSetMapper cRowSet = null;
	
	// Vars
	String word = "";
	String groupSeq = "";


	if(!"".equals(seq)){
		cRowSet = VocabularyDAO.getInstance().getVocabularyDetail(seq);
		
		if(cRowSet.next()){
			word = cRowSet.getString("word");
			groupSeq = cRowSet.getString("user_group");
		}
	}
	

	//그룹 select	1000으로 셋팅
	StringBuffer sbufSelect = new StringBuffer();
	cRowSet = GroupDAO.getInstance().groupList(1000, 1);
	sbufSelect.append("<select name='groupSeq' id='groupSeq'>");
	while(cRowSet.next()){
		if(groupSeq.equals(cRowSet.getString("seq"))){
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"' selected>"+cRowSet.getString("group_name")+"</option>");
		}
		else{
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("group_name")+"</option>");
		}
	}
	sbufSelect.append("</select>");

%>
<script type="text/javascript">
function goSubmit(){
	var f = document.frm;

	if ( isEmpty(f.word.value)) {
		alert("어휘를 입력하세요.");
		f.word.focus();
		return;
	} else if ( f.checkWord.value != "1")	{
		alert("어휘 중복검사를 해주세요.");
		return;
	}


	f.action = "vocabularyProc.jsp";
	f.submit();
}

function duplCheck(checkWord) {
	var groupSeq = document.frm.groupSeq.value;
	var paramData = $("#frm").serialize();

	$.getJSON('vocabularyCheck.jsp', paramData, function(data) {
		if(data.code == 0)	{
			$("#checkWord").val(1);
		}
		alert(data.msg);
	});
}
</script>

<div class="top_title">어휘 <%="".equals(seq) ? "등록" : "수정"%></div>

<form name="frm" id="frm" method="post" action="">
<div>
	<input type="hidden" name="checkWord" id="checkWord" value="" />
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="seq" value="<%=seq%>" />
</div>
<div id="form_warp">
	<dl>
		<dt class="inpt_dt">어휘</dt>
		<dd class="inpt_dd">
			<input type="text" name="word" id="word" value="<%=word%>" maxlength="20" onchange="$('#checkWord').val('');" />
			<input type="button" onclick="duplCheck($('#word').val())" value="중복확인" class="button white" />
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

</form>

<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='vocabularyList.jsp'" value="Cancel" class="button white" style="width:50px;" />
	<input type="button" onclick="goSubmit();" value="Save" class="button black" style="width:50px;" />
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
