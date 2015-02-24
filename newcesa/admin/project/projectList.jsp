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
	m1 = 3;
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

	int today = Integer.parseInt(DateUtil.getToday("YYYYMMDD"));
	int startDate = 0;
	int endDate = 0;

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

	cRowSet = ProjectDAO.getInstance().getProjectList(groupSeq, sc.getInt("admin.list.cnt"), currPage);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();
	int num = totalCount - ((currPage-1) * 10);

%>
<script type="text/javascript">
function goSearch(){
	var frm = document.actFrm;

	frm.submit();
}

function goDelete(seq)
{
	if(confirm("삭제하시겠습니까?")){
		location.href = "projectProc.jsp?mode=Delete&seq="+seq;
	}
}
</script>
<div class="top_title">프로젝트 관리</div>
<div id="search_form_inline" style="clear:both;">
<form name="actFrm" action="projectList.jsp">
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
	<table cellspacing="0" cellpadding="0" width="100%">
		<colgroup>
			<col width="40px" />
			<col width="50px"/>
			<col />
			<col width="100px"/>
			<col width="150px"/>
			<col width="100px"/>
			<col width="100px"/>
			<col width="100px" />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">이미지</th>
			<th class="ta_c">프로젝트명</th>
			<th class="ta_c">그룹명</th>
			<th class="ta_c">기간</th>
			<th class="ta_c">등록일</th>
			<th class="ta_c">상태</th>
			<th class="ta_c">수정 / 삭제</th>
		</tr>
		</thead>
		<tbody>
		<%
		
		String status = null;
		try{			
			while(cRowSet.next()){

				//int today = Integer.parseInt(DateUtil.getToday("YYYYMMDD"));
				startDate = Integer.parseInt(cRowSet.getString("start_date").replace("-",""));
				endDate = Integer.parseInt(cRowSet.getString("end_date").replace("-",""));
				
				if(startDate > today){
					status = "대기";
				}
				else if(endDate >=today){
					status = "진행중";
				}
				else{
					status = "종료";
				}
		%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta_c"><img name="preImage" id="preImage" src="<%=sc.get("imageBanner.file.url")%>/<%=cRowSet.getString("image")%>" height="50" /></td>
			<td class="ta_c"><%=cRowSet.getString("project_name")%></td>
			<td class="ta_c"><%=cRowSet.getString("group_name")%></td>
			<td class="ta_c"><%=cRowSet.getString("start_date")%>~<%=cRowSet.getString("end_date")%></td>
			<td class="ta_c"><%=cRowSet.getString("regdate").substring(0, 10)%></td>
			<td class="ta_c"><%=status%></td>
			<td class="ta_c"><a href="projectFormStep01.jsp?seq=<%=cRowSet.getString("seq")%>">수정</a> / <a href="javascript:goDelete('<%=cRowSet.getString("seq")%>')">삭제</a></td>
		</tr>
		<%
			}
		}
		catch(Exception e){
			log.debug(e);	
		}

		if(num == 0){
		%>
		<tr>
			<td colspan="8" class="ta_c">등록된 프로젝트가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
</div>

<div style="width:100%; text-align:center; margin-top:20px;">
    <dnt7:page pageCommonUrl='projectList.jsp' totCount='<%=totalCount%>' currPage='<%=currPage%>' serviceType='admin'/>
</div>

<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='projectFormStep01.jsp'" value="Add" class="button white" style="width:33px;"/>
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
