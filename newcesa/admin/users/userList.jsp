<%
	/*============================================================================
	 * @ Description : 사용자 리스트
	 *
	 * 작성일 : 2010.12.15
	 * 작성자 : 박병웅
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerAdmin.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/dnt7-com.tld" prefix="dnt7" %>
<%
	m1 = 3;
	m2 = 1;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
	int currPage = Integer.parseInt(StringUtils.defaultString(request.getParameter("_currPage"),  "1"));
	int listCnt = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("listCnt"),  "50"));

	String sUserId = StringUtils.defaultString(request.getParameter("sUserId"));
	String pageUrl = "userList.jsp?listCnt="+listCnt;
	if(StringUtils.isNotEmpty(sUserId)) pageUrl += "&sUserId=" + sUserId;

	// Vars
	int totalCount = 0;
	RowSetMapper cRowSet = null;
	cRowSet = UserDAO.getInstance().getUserList(listCnt, currPage, sUserId);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();

	int num = totalCount - ((currPage-1) * listCnt);
%>
<script type="text/javascript">

function goSearch(){
	var frm = document.actFrm;
	frm.submit();
}
function goCommentView(userid){
	document.location.href="/admin/comments/commentList.jsp?sUserId="+userid;
}

function goDetail(seq){
	document.location.href="/admin/users/userDetail.jsp?seq="+seq+"&listCnt=<%=listCnt%>&currPage=<%=currPage%>&sUserId=<%=sUserId%>";
}

</script>

<div class="top_title">User Management</div>

<form name="actFrm" action="userList.jsp">
<div id="search_form_inline">
	<div id="search_form_inline_in">

		리스트 : 
		<select id="listCnt" name="listCnt" onchange="goSearch()">
			<option value="10" <%=listCnt==10 ? "selected" : "" %>>10</option>
			<option value="20" <%=listCnt==20 ? "selected" : "" %>>20</option>
			<option value="30" <%=listCnt==30 ? "selected" : "" %>>30</option>
			<option value="50" <%=listCnt==50 ? "selected" : "" %>>50</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;

		User ID / E-MAIL : <input type="text" name="sUserId" value='<%=sUserId%>' style="width:160px;" />
		<input type="button" value="Search" style="width:50px;" onclick='goSearch();' class="button white btn_in" />
	</div>
</div>
</form>

<div id="roof_table">
	<table cellspacing="0" cellpadding="0">
		<colgroup>
			<col width="40px" />
			<col />
			<col width="150px" />
			<col width="100px" />
			<col width="150px" />
			<col width="150px" />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">User ID (Reference ID)</th>
			<th class="ta_c">Email</th>
			<th class="ta_c">Point</th>
			<th class="ta_c">Join Date</th>
			<th class="ta_c">Last Login</th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			while(cRowSet.next()){
				String lastLogin = cRowSet.getString("LAST_LOGIN");
				if (lastLogin.length() > 16){
					lastLogin = lastLogin.substring(0, 16);
				}
		%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta_c"><a href="userDetail.jsp?userId=<%=cRowSet.getString("USER_ID")%>"><font color='<%=cRowSet.getString("WARNING_LEVEL")%>'><%=cRowSet.getString("USER_ID")%></font></a> <%= StringUtils.isNotEmpty(cRowSet.getString("RECOM_ID")) ? "(<a href='userDetail.jsp?userId="+cRowSet.getString("RECOM_ID")+"'>"+cRowSet.getString("RECOM_ID")+"</a>)" : "" %></td>
			<td class="ta_c"><%=cRowSet.getString("EMAIL")%></td>
			<td class="ta_r"><%=String.format("%, d", cRowSet.getInt("POINT"))%> P</td>
			<td class="ta_c"><%=cRowSet.getString("REG_DATE").substring(0, 16)%></td>
			<td class="ta_c"><%=lastLogin%></td>
		</tr>
		<%
			}
		}
		catch(Exception e){
			log.debug(e);
			log.fatal(e.getMessage(), e);
		}

		if(totalCount == 0){
		%>
		<tr>
			<td colspan="6" class="ta_c">등록된 사용자가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
</div>
<div style="width:100%; text-align:center; margin-top:20px;">
    <dnt7:page pageCommonUrl='<%=pageUrl%>' totCount='<%=totalCount%>' currPage='<%=currPage%>' serviceType='admin' articlesPerPage='<%=listCnt%>'/>
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
