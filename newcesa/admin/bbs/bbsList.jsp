<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerAdmin.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/dnt7-com.tld" prefix="dnt7" %>
<%
	m1 = 2;
	m2 = 12;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
    int currPage = Integer.parseInt(StringUtils.defaultString(request.getParameter("_currPage"),  "1"));
	int listCnt = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("listCnt"),  "30"));

	// Vars
	int totalCount = 0;
	RowSetMapper cRowSet = null;
	
	cRowSet = BbsDAO.getInstance().listAdmin(listCnt, currPage);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();
	
	int num = totalCount - (currPage-1)*listCnt;
%>
<script type="text/javascript">
function goSearch()
{
	document.actFrm.submit();
}

</script>
<div class="top_title">Bbs Management</div>

<div id="search_form_inline" style="clear:both;">
<form action=bbsProc.jsp method=post>
<input type=hidden name=mode value=delId>
<input type=text name=id size=10>
<input type=submit value='ID로 삭제'>
</form>
&nbsp; &nbsp; &nbsp; &nbsp;
<form action=bbsProc.jsp method=post>
<input type=hidden name=mode value=delName>
<input type=text name=name size=10>
<input type=submit value='Name으로 삭제'>
</form>
</div>


총 <%=totalCount%> 개
<div id="roof_table">
	<table cellspacing="0" cellpadding="0" border="0">
		<colgroup>
			<col />
			<col />
			<col />
			<col />
			<col />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">USER_ID</th>
			<th class="ta_c">USER_NAME</th>
			<th class="ta_c">CONTENTS</th>
			<th class="ta_c">IP</th>
			<th class="ta_c">REG_DATE</th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			
			String contents = null;
			while(cRowSet.next()){	
				contents = cRowSet.getString("CONTENTS");
				//if(contents.length()>20) contents = contents.substring(0,20)+"...";
		%>
		<tr>
			<td class="ta_c"><a href='bbsView.jsp?tb_id=<%=cRowSet.getString("TB_ID")%>'><%=cRowSet.getString("USER_ID")%></a></td>
			<td class="ta_c"><a href='bbsView.jsp?tb_id=<%=cRowSet.getString("TB_ID")%>'><%=cRowSet.getString("USER_NAME")%></a></td>
			<td class="ta_l"><a href='bbsView.jsp?tb_id=<%=cRowSet.getString("TB_ID")%>'><%=contents%></a></td>
			<td class="ta_c"><a href='bbsView.jsp?tb_id=<%=cRowSet.getString("TB_ID")%>'><%=cRowSet.getString("IP")%></a></td>
			<td class="ta_c"><a href='bbsView.jsp?tb_id=<%=cRowSet.getString("TB_ID")%>'><%=cRowSet.getString("REG_DATE").substring(0,19)%></a></td>
		</tr>
		<%
			}
		}catch(Exception e){
			log.debug(e);
		}
	
		if (totalCount == 0){
		%>
		<tr>
			<td colspan="7" class="ta_c">데이터가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
</div>
<div style="width:100%; text-align:center; margin-top:20px;">
	<dnt7:page pageCommonUrl='<%=("bbsList.jsp?")%>' totCount='<%=totalCount%>' currPage='<%=currPage%>' serviceType='admin' articlesPerPage='<%=listCnt%>'/>
</div>
<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
