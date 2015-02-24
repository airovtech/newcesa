<%
	/*============================================================================
	 * @ Description : 관리자 리스트
	 *
	 * 작성일 : 2011.04.18
	 * 작성자 : 이정순
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerAdmin.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/dnt7-com.tld" prefix="dnt7" %>
<%
	m1 = 1;
	m2 = 2;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
    // Params
	int currPage = Integer.parseInt(StringUtils.defaultString(request.getParameter("_currPage"),  "1"));

	// Vars
	int totalCount = 0;
	RowSetMapper cRowSet = null;
	cRowSet = GroupDAO.getInstance().groupList(sc.getInt("admin.list.cnt"), currPage);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();
	
	int num = totalCount - ((currPage-1) * 10);
%>
<script type="text/javascript">
function goDelete(seq)
{
	if(confirm("삭제하시겠습니까?")){
		location.href = "groupProc.jsp?mode=Delete&seq="+seq;
	}
}
</script>
<div class="top_title">그룹 관리</div>

<div id="roof_table">
	<table cellspacing="0" cellpadding="0" width="900px">
		<colgroup>
			<col width="40px" />
			<col />
			<col />
			<col width="140px" />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">그룹명</th>
			<th class="ta_c">등록일</th>
			<th class="ta_c">수정 / 삭제</th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			while(cRowSet.next()){

		%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta_c"><a href="groupForm.jsp?seq=<%=cRowSet.getString("seq")%>&mode=Modify"><%=cRowSet.getString("group_name")%></a></td>
			<td class="ta_c"><a href="groupForm.jsp?seq=<%=cRowSet.getString("seq")%>&mode=Modify"><%=cRowSet.getString("regdate").substring(0, 16)%></a></td>
			<td class="ta_c"><a href="groupForm.jsp?seq=<%=cRowSet.getString("seq")%>&mode=Modify">수정</a> / <a href="javascript:goDelete('<%=cRowSet.getString("seq")%>')">삭제</a></td>
		</tr>
		<%
			}
		}
		catch(Exception e){
			log.debug(e);	
		}

		if(totalCount == 0){
		%>
		<tr>
			<td colspan="6" class="ta_c">등록된 그룹이 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
</div>
<div style="width:100%; text-align:center; margin-top:20px;">
    <dnt7:page pageCommonUrl='groupList.jsp' totCount='<%=totalCount%>' currPage='<%=currPage%>' serviceType='admin'/>
</div>
<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='groupForm.jsp'" value="Add" class="button white" style="width:33px;"/>
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
