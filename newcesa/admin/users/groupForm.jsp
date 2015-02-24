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
	m1 = 1;
	m2 = 2;
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
	String groupName = "";


	if(!"".equals(seq)){
		cRowSet = GroupDAO.getInstance().getGroupDetail(seq);
		
		if(cRowSet.next()){
			groupName = cRowSet.getString("group_name");
		}
	}


%>
<script type="text/javascript">
function goSubmit(){
	var f = document.frm;

	if ( isEmpty(f.groupName.value)) {
		alert("그룹명을 입력하세요.");
		f.groupName.focus();
		return;
	}
	f.action = "groupProc.jsp";
	f.submit();
}

</script>

<div class="top_title">그룹 <%=!"".equals(seq) ? "수정" : "추가"%></div>


<form name="frm" method="post" action="">
<div>
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="seq" value="<%=seq%>" />
</div>
<div id="form_warp">
	<dl>
		<dt class="inpt_dt">그룹명</dt>
		<dd class="inpt_dd"><input type="text" name="groupName" maxlength="50" value="<%=groupName%>" size="50" /></dd>
	</dl>         
</div>

</form>

<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='groupList.jsp'" value="Cancel" class="button white" style="width:50px;" />
	<input type="button" onclick="goSubmit();" value="Save" class="button black" style="width:50px;" />
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
