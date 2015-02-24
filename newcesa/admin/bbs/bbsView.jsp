<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerAdmin.jsp" %>
<%
	m1 = 2;
	m2 = 12;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
	String tb_id = StringUtils.defaultString(request.getParameter("tb_id"));

	String mode = "".equals(tb_id) ? "reg" : "edit";
	RowSetMapper cRowSet = null;

	cRowSet = BbsDAO.getInstance().detail(tb_id);
	cRowSet.next();
	
%>
<script type="text/javascript">
function goDelete() {
	if(confirm('정말 삭제하시겠습니까?'))
		document.location.href='bbsProc.jsp?mode=del&tb_id=<%=tb_id%>';
}
</script>

<div class="top_title">Bbs Management</div>


<form name="frm" method="post" action="bbsProc.jsp">
<div>
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="tb_id" value="<%=tb_id%>" />
</div>
<div id="form_warp">
	<dl>
		<dt class="inpt_dt">TB_ID</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("TB_ID")%></dd>
		
	</dl>
	<dl>
		<dt class="inpt_dt">LANGUAGE</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("LANGUAGE")%></dd>
		
	</dl>
	<dl>
		<dt class="inpt_dt">USER_ID</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("USER_ID")%></dd>
		
	</dl>
	<dl>
		<dt class="inpt_dt">USER_NAME</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("USER_NAME")%></dd>
		
	</dl>
	<dl>
		<dt class="inpt_dt">CONTENTS</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("CONTENTS")%></dd>
		
	</dl>
	<dl>
		<dt class="inpt_dt">TSTAMP</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("TSTAMP")%></dd>
		
	</dl>
	<dl>
		<dt class="inpt_dt">REG_DATE</dt>
		<dd class="inpt_dd"><%=cRowSet.getString("REG_DATE").substring(0,19)%></dd>
		
	</dl>
</div>

</form>

<div class="btn_group_right_align">
<% if(mode.equals("edit")) { %>
	<input type="button" onclick="goDelete();" value="삭제" class="button white" style="width:45px;"/>
<% } %>
	<input type="button" onclick="document.location.href='bbsList.jsp'" value="List" class="button white" style="width:45px;"/>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
