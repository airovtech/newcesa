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
	m2 = 1;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
    // Params
	int currPage = Integer.parseInt(StringUtils.defaultString(request.getParameter("_currPage"),  "1"));
	String groupSeq = StringUtils.defaultString(request.getParameter("groupSeq"),  "");


	// Vars
	int totalCount = 0;
	RowSetMapper cRowSet = null;

	
	//그룹 select	1000으로 셋팅
	StringBuffer sbufSelect = new StringBuffer();
	cRowSet = GroupDAO.getInstance().groupList(1000, 1);
	sbufSelect.append("<select name='groupSeq'>");
	sbufSelect.append("<option value=''>전체</option>");
	while(cRowSet.next()){
		if(groupSeq.equals(cRowSet.getString("seq"))){
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"' selected>"+cRowSet.getString("group_name")+"</option>");
		}
		else{
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("group_name")+"</option>");
		}
	}
	sbufSelect.append("</select>");

	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}


	cRowSet = AdminDAO.getInstance().getAdminList(groupSeq, sc.getInt("admin.list.cnt"), currPage);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();
	


	int num = totalCount - ((currPage-1) * 10);
%>
<script type="text/javascript">
function goSearch(){
	var frm = document.actFrm;

	frm.submit();
}
function goDelete(seq){
	var frm = document.actFrm;

	if(confirm("삭제하시겠습니까?")){
		frm.seq.value = seq;
		frm.mode.value = "Delete";
		frm.action = "adminProc.jsp";
		frm.submit();
	}
}
</script>
<div class="top_title">관리자 관리</div>
<div id="search_form_inline" style="clear:both;">
<form name="actFrm" action="adminList.jsp">
<input type="hidden" id="seq" name="seq" value=""/>
<input type="hidden" id="mode" name="mode" value=""/>
<%
	if(sGroup.equals("0")){
%>
	<div id="search_form_inline_in">
		Group : <%=sbufSelect%>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="Search" style="width:50px;" onclick='goSearch();' class="button white btn_in" />
	</div>
<%
	}			
%>
</form>
</div>

<div id="roof_table">
	<table cellspacing="0" cellpadding="0" width="900px">
		<colgroup>
			<col width="40px" />
			<col width="220px"/>
			<col width="220px"/>
			<col />
			<col width="140px" />
			<col width="100px" />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">아이디</th>
			<th class="ta_c">그룹</th>
			<th class="ta_c">이메일</th>
			<th class="ta_c">최근 로그인</th>
			<th class="ta_c"></th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			while(cRowSet.next()){
				String lastLogin = cRowSet.getString("LASTLOGIN");
				if (lastLogin.length() > 16){
					lastLogin = lastLogin.substring(0, 16);
				}
		%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta_c"><a href="adminForm.jsp?seq=<%=cRowSet.getString("seq")%>"><%=cRowSet.getString("adminID")%></a></td>
			<td class="ta_c"><%=cRowSet.getString("user_group").equals("0") ? "수퍼관리자" : cRowSet.getString("group_name") %></td>
			<td class="ta_c"><%=cRowSet.getString("email")%></td>
			<td class="ta_c"><%=lastLogin%></td>
			<td class="ta_c"><input type="button" onclick="goDelete('<%=cRowSet.getString("seq")%>')" value="삭제" class="button black" style="width:33px;"/></td>
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
			<td colspan="6" class="ta_c">등록된 관리자가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
</div>
<div style="width:100%; text-align:center; margin-top:20px;">
    <dnt7:page pageCommonUrl='adminList.jsp' totCount='<%=totalCount%>' currPage='<%=currPage%>' serviceType='admin'/>
</div>
<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='adminForm.jsp'" value="Add" class="button white" style="width:33px;"/>
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
