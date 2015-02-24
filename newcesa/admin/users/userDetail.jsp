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
	m1 = 3;
	m2 = 1;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<script type="text/javascript">
	function valChk(){
		var f = document.inputFrm;

		if ( isEmpty(f.title.value)) {
			alert("내용을 입력하세요.");
			f.title.focus();
			return false;
		}
		if ( isEmpty(f.point.value)) {
			alert("Point를 입력하세요.");
			f.point.focus();
			return false;
		}

		return true;
	}


	function doReg(){
		var frm = document.inputFrm;
		if( valChk()==false ) return;
		frm.submit();
	}
	
	function doRegComment(){
		var frm = document.commentFrm;
		frm.submit();
	}

</script>
<%
	// Params
	String seq = request.getParameter("seq");
	String userId = request.getParameter("userId");

	String currPage = StringUtils.defaultString(request.getParameter("currPage"),"1");
	int listCnt = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("listCnt"),  "10"));
	String sUserId = StringUtils.defaultString(request.getParameter("sUserId"));

	RowSetMapper cRowSet = null;

	
	// Vars
	String email = null;
	String mailingYn = null;
	String regDate = null;
	String lastLogin = null;
	String recomId = null;
	int point = 0;
	String udid = null;
	String ip = null;
	String warning_level = null;
	String block_yn = null;
	String comment = null;
		
	String recomIdWarningLevel = "";

	int count = 0;
	
	StringBuffer sbufUdidList = new StringBuffer();
	int countUdid = 0;

	try {

		if(StringUtils.isEmpty(userId) && StringUtils.isEmpty(seq)) {
			out.println("no exist user");
			return;
		}

		if(StringUtils.isEmpty(userId))		// userId 인자가 있거나 seq가 있거나
			cRowSet = UserDAO.getInstance().getUserDetail(Integer.parseInt(seq));
		else 
			cRowSet = UserDAO.getInstance().getUserDetailId(userId);

		if(cRowSet.next()){
			userId = cRowSet.getString("USER_ID");
			email = cRowSet.getString("EMAIL");
			mailingYn = cRowSet.getString("MAILING_YN");
			regDate = cRowSet.getString("REG_DATE");
			lastLogin = cRowSet.getString("LAST_LOGIN");
			recomId = cRowSet.getString("RECOM_ID");
			udid = cRowSet.getString("UDID");
			ip = cRowSet.getString("IP");
			point = cRowSet.getInt("POINT");
			seq = cRowSet.getString("MEMBER_SEQ");
			warning_level	= cRowSet.getString("WARNING_LEVEL");
			block_yn		= cRowSet.getString("BLOCK_YN");
			comment			= cRowSet.getString("COMMENT");

			if(regDate.length()>19) regDate=regDate.substring(0,19);
			if(lastLogin.length()>19) lastLogin=lastLogin.substring(0,19);
		} else {
			out.println("no exist user");
			return;
		}
		
		
		
		cRowSet = UserDAO.getInstance().getUserUdidList(udid, Integer.parseInt(seq));
		
		while(cRowSet.next()){
			countUdid++;
			sbufUdidList.append("<a href='userDetail.jsp?userId="+cRowSet.getString("USER_ID")+"'><font color='"+cRowSet.getString("WARNING_LEVEL")+"'>"+cRowSet.getString("USER_ID")+"</font></a></br>");
		}


		// 추천한 사람 정보 추출
		cRowSet = UserDAO.getInstance().getUserDetailId(recomId);
		if(cRowSet.next()) {
			recomIdWarningLevel = cRowSet.getString("WARNING_LEVEL");
		}


	}catch(Exception e){
		log.debug("admin_detail e:"+e);
	}
%>
<script type="text/javascript">

</script>

<div class="top_title">User Management</div>


<div id="form_warp">
	<dl>		
		<dt class="inpt_dt">User ID</dt>
		<dd class="inpt_dd"><font color='<%=warning_level%>'><%=userId%></font></dd>
		
		<dt class="inpt_dt">Email</dt>
		<dd class="inpt_dd"><%=email%></dd>

		<dt class="inpt_dt">등록일</dt>
		<dd class="inpt_dd"><%=regDate%></dd>

		<dt class="inpt_dt">마지막 로그인</dt>
		<dd class="inpt_dd"><%=lastLogin%></dd>
		
		<dt class="inpt_dt">회원가입 IP</dt>
		<dd class="inpt_dd"><a href='/admin/stat/ip.jsp?ip=<%=ip%>'><%=ip%></a></dd>


		<dt class="inpt_dt">UDID</dt>
		<dd class="inpt_dd"><a href='/admin/stat/udid.jsp?udid=<%=udid%>'><%=udid%></a></dd>
		
		<form id="commentFrm" name="commentFrm" method="post" action="userCommentProc.jsp" onsubmit="return false;">
		<input type="hidden" name="user_id" value="<%=userId%>" />

		<dt class="inpt_dt">Level</dt>
		<dd class="inpt_dd">
			<select name=warning_level>
				<option value='BLACK' <%=warning_level.equals("BLACK") ? "selected" : ""%>> 정상
				<option value='BLUE' <%=warning_level.equals("BLUE") ? "selected" : ""%>> 주의
				<option value='RED' <%=warning_level.equals("RED") ? "selected" : ""%>> 경고
			</select>
		</dd>
		<dt class="inpt_dt">Blocked</dt>
		<dd class="inpt_dd">
			<select name=block_yn>
				<option value='Y' <%=block_yn.equals("Y") ? "selected" : ""%>> Block
				<option value='N' <%=block_yn.equals("N") ? "selected" : ""%>> 정상
			</select>
		</dd>
		<dt class="inpt_dt" style="height:100px;">Comment</dt>
		<dd class="inpt_dd" style="height:100px;">
			<textarea name="comment" cols="0" rows="0" style="width:90%; height:90px;"><%=comment%></textarea>
			<input type="button" value="저장" class="button black" style="width:40px;" onclick="javascript:doRegComment();" />
		</dd>
		</form>


		<dt class="inpt_dt" style='height:<%=(countUdid > 1 ? ""+(countUdid*14+30)+"px;" : "30px;")%>'>동일 UDID LIST</dt>
		<dd class="inpt_dd" style='height:<%=(countUdid > 1 ? ""+(countUdid*14+30)+"px;" : "30px;")%>'><%=sbufUdidList%></dd>

		<dt class="inpt_dt">추천한 ID</dt>
		<dd class="inpt_dd"><a href='userDetail.jsp?userId=<%=recomId%>'><font color='<%=recomIdWarningLevel%>'><%=recomId%></font></a></dd>

		<dt class="inpt_dt">Point</dt>
		<dd class="inpt_dd">
			<a href="pointList.jsp?userId=<%=userId%>"><%=String.format("%, d", point)%> P [내역보기]</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='userCalculate.jsp?user_id=<%=userId%>'>[Point 정리]</a> <font color=blue>Point를 DB에서 직접 조정한 경우 정리필요</font>
		</dd>
		
	</dl>         
</div>


<div id="form_warp" >
	<form id="inputFrm" name="inputFrm" method="post" action="userProc.jsp" onsubmit="return false;">
		<input type="hidden" name="user_id" value="<%=userId%>" />
		<dl>
			<dt class="inpt_dt">Point 지급</dt>
			<dd class="inpt_dd">
				내용 : <input type="text" name="title" style="width:150px;"/> (사용자에게 보여질 메세지)
				&nbsp;&nbsp;&nbsp;
				Point : <input type="text" name="point" style="width:50px;"/>
				<input type="button" value="등록" class="button black" style="width:40px;" onclick="javascript:doReg();" />
			</dd>
		</dl>
	</form>
</div>

<div style="width:100%; text-align:center; margin-top:20px;">
<br><br>&nbsp;
</div>

추천받은 ID List
<div id="roof_table">
	<table cellspacing="0" cellpadding="0">
		<colgroup>
			<col width="50px" />
			<col width="100px" />
			<col width="50px" />
			<col width="100px" />
			<col />
			<col />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">ID</th>
			<th class="ta_c">Point</th>
			<th class="ta_c">IP</th>
			<th class="ta_c">UDID</th>
			<th class="ta_c">가입일</th>
		</tr>
		</thead>
		<tbody>
<%

		// 추천받은 목록 추출
		cRowSet = UserDAO.getInstance().getRecomIdList(userId);
		int num = cRowSet.size();
		while(cRowSet.next()) 
		{
%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta"><a href='userDetail.jsp?userId=<%=cRowSet.getString("USER_ID")%>'><font color='<%=cRowSet.getString("WARNING_LEVEL")%>'><%=cRowSet.getString("USER_ID")%></font></a></td>
			<td class="ta_r"><%=String.format("%, d", cRowSet.getInt("POINT"))%></td>
			<td class="ta">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='/admin/stat/ip.jsp?ip=<%=cRowSet.getString("IP")%>'><%=cRowSet.getString("IP")%></a></td>
			<td class="ta">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='/admin/stat/udid.jsp?udid=<%=cRowSet.getString("UDID")%>'><%=cRowSet.getString("UDID")%></a></td>
			<td class="ta_c"><%=cRowSet.getString("REG_DATE").substring(0,19)%></td>
		</tr>
<%
		}
%>
		</tbody>
	</table>
</div>

<br/> <br/> <br/>
Referral 목록
<div id="roof_table">
	<table cellspacing="0" cellpadding="0">
		<colgroup>
			<col width="100px" />
			<col width="100px" />
			<col width="100px" />
			<col width="100px" />
			<col width="150px" />
			<col width="150px" />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">Sub ID</th>
			<th class="ta_c">IP</th>
			<th class="ta_c">지급여부</th>
			<th class="ta_c">등록일</th>
			<th class="ta_c">지급일</th>
		</tr>
		</thead>
		<tbody>
<%

		RowSetMapper rs = PartyTreatsUpLogDAO.getInstance().getList(userId);
		String sub_id = null;
		String earned = null;
		String reg_date = null;
		String earned_date = null;

		num = rs.size();

		while(rs.next()) 
		{
			sub_id = rs.getString("SUB_ID");
			earned = rs.getString("EARNED");
			reg_date = rs.getString("REG_DATE").substring(0,19);
			earned_date = rs.getString("EARNED_DATE");

			if(earned_date.length()>19)
				earned_date = earned_date.substring(0,19);
%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta_c"><a href='userDetail.jsp?userId=<%=sub_id%>'><font color='<%=rs.getString("WARNING_LEVEL")%>'><%=sub_id%></font></a></td>
			<td class="ta"><a href='/admin/stat/ip.jsp?ip=<%=rs.getString("IP")%>'><%=rs.getString("IP")%></a></td>
			<td class="ta_c"><%=earned%></td>
			<td class="ta_c"><%=reg_date%></td>
			<td class="ta_c"><%=earned_date%></td>
		</tr>
<%
		}
%>
		</tbody>
	</table>
</div>
<br/>
<br/>





<script type="text/javascript">
function addRow(num, title, type1, type2, ip, udid, date){
    var tableRef = document.getElementById('logTable');
	
    var newRow = tableRef.insertRow((tableRef.rows.length));

    var newCell1 = newRow.insertCell(0);
    var newCell2 = newRow.insertCell(1);
	var newCell3 = newRow.insertCell(2);
	var newCell4 = newRow.insertCell(3);
	var newCell5 = newRow.insertCell(4);
	var newCell6 = newRow.insertCell(5);

	newCell1.className = "ta_c";
	newCell2.className = "ta_c";
	newCell3.className = "ta_c";
	newCell4.className = "ta_c";
	newCell5.className = "ta_c";
	newCell6.className = "ta_c";

    var newText1 = document.createTextNode(num);
    var newText2 = document.createTextNode(title);
	var newText3 = document.createTextNode(type1+''+type2);
	var newText6 = document.createTextNode(date);

	
	var linkIp=document.createElement('a');
	linkIp.href = '/admin/stat/ip.jsp?ip='+ip;
	linkIp.appendChild(document.createTextNode(ip));

	var linkUdid=document.createElement('a');
	linkUdid.href = '/admin/stat/udid.jsp?udid='+udid;
	linkUdid.appendChild(document.createTextNode(udid));


    newCell1.appendChild(newText1);
    newCell2.appendChild(newText2);
	newCell3.appendChild(newText3);
	newCell4.appendChild(linkIp);
	newCell5.appendChild(linkUdid);
	newCell6.appendChild(newText6);
}

function getLogData(){
	
	if(document.logFrm.noMore.value=="yes"){
		alert("No more!!");
		return;
	}

	var paramData = $("#logFrm").serialize();
	$.getJSON(
		'/admin/users/userDetailAjax.jsp',
		paramData,
		function(data) {
			
			if(data.result == "success"){

				document.logFrm.noMore.value = data.noMore;
				document.logFrm.curPage.value = data.nextPage;

				for(var i=0;i<data.items.length;i++){
					addRow(data.items[i].num, data.items[i].title ,data.items[i].type1, data.items[i].type2, data.items[i].ip, data.items[i].udid, data.items[i].date);
				}
			
			}
			else{
				alert("error!!");
			}
		}
	);
}
</script>

<%
	cRowSet = StatDAO.getInstance().getPartyTreatsUser(userId, sc.getInt("admin.list.cnt"), 1);
	int totalCount = cRowSet.getQueryManager().getMaxRowSize();
	num = 0;
%>

<form name="logFrm" id="logFrm">
<input type="hidden" name="aUserId" value="<%=userId%>">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="noMore" value="<%=totalCount>=sc.getInt("admin.list.cnt") ? "no" : "yes" %>">
<input type="hidden" name="ajaxMode" value="log">
</form>
<div id="roof_table">
PartyTreats Log
	<table cellspacing="0" cellpadding="0" id="logTable" border="0">
		<colgroup>
			<col width="40px" />
			<col />
			<col />
			<col />
			<col />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">PartyTreats</th>
			<th class="ta_c">Type</th>
			<th class="ta_c">IP</th>
			<th class="ta_c">UDID</th>
			<th class="ta_c">Date</th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			String title = "";
			while(cRowSet.next()){

				title = cRowSet.getString("TITLE");
				if(title.length()>20) 
					title = title.substring(0,19) + "...";

		%>
		<tr>
			<td class="ta_c"><%=++num%></td>
			<td class="ta_c"><%=title%></td>
			<td class="ta_c">
				<%=cRowSet.getString("GETIT_YN").equals("Y") ? "Get" : ""%>
				<%=cRowSet.getString("INSTALL_YN").equals("Y") ? "Install" : ""%>
			</td>
			<td class="ta_c"><a href='/admin/stat/ip.jsp?ip=<%=cRowSet.getString("IP")%>'><%=cRowSet.getString("IP")%></a></td>
			<td class="ta_c"><a href='/admin/stat/udid.jsp?udid=<%=cRowSet.getString("UDID")%>'><%=cRowSet.getString("UDID")%></a></td>
			<td class="ta_c"><%=cRowSet.getString("REG_DATE").substring(0, 19)%></td>
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
			<td colspan="6" class="ta_c">데이터가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
	<div class="btn_group_right_align"><input type="button" value="PartyTreats Log 계속보기" onclick="getLogData()" class="button white" ></div>

</div>
<br>




<script type="text/javascript">
function addRowResult(num, title, win_yn, point, extra, claim, udid, regDate, claimDate, expireDate){
    var tableRef = document.getElementById('resultTable');
	
    var newRow = tableRef.insertRow((tableRef.rows.length));

    var newCell1 = newRow.insertCell(0);
    var newCell2 = newRow.insertCell(1);
	var newCell3 = newRow.insertCell(2);
	var newCell4 = newRow.insertCell(3);
	var newCell5 = newRow.insertCell(4);
	var newCell6 = newRow.insertCell(5);
	var newCell7 = newRow.insertCell(6);
	var newCell8 = newRow.insertCell(7);
	var newCell9 = newRow.insertCell(8);

	newCell1.className = "ta_c";
	newCell2.className = "ta_c";
	newCell3.className = "ta_c";
	newCell4.className = "ta_c";
	newCell5.className = "ta_c";
	newCell6.className = "ta_c";
	newCell7.className = "ta_c";
	newCell8.className = "ta_c";
	newCell9.className = "ta_c";

    var newText1 = document.createTextNode(num);
    var newText2 = document.createTextNode(title);
	var newText3 = document.createTextNode(win_yn+" ("+point+")");
	var newText4 = document.createTextNode(extra);
	var newText5 = document.createTextNode(claim);
	var newText7 = document.createTextNode(regDate);
	var newText8 = document.createTextNode(claimDate);
	var newText9 = document.createTextNode(expireDate);


	var linkUdid=document.createElement('a');
	linkUdid.href = '/admin/stat/udid.jsp?udid='+udid;
	linkUdid.appendChild(document.createTextNode(udid));

    newCell1.appendChild(newText1);
    newCell2.appendChild(newText2);
	newCell3.appendChild(newText3);
	newCell4.appendChild(newText4);
	newCell5.appendChild(newText5);
	newCell6.appendChild(linkUdid);
	newCell7.appendChild(newText7);
	newCell8.appendChild(newText8);
	newCell9.appendChild(newText9);
}

function getResultData(){
	
	if(document.resultFrm.noMore.value=="yes"){
		alert("No more!!");
		return;
	}

	var paramData = $("#resultFrm").serialize();
	$.getJSON(
		'/admin/users/userDetailAjax.jsp',
		paramData,
		function(data) {
			
			if(data.result == "success"){

				document.resultFrm.noMore.value = data.noMore;
				document.resultFrm.curPage.value = data.nextPage;
				
				for(var i=0;i<data.items.length;i++){

					addRowResult(data.items[i].num, data.items[i].title ,data.items[i].win, data.items[i].point, data.items[i].extra, data.items[i].claim, data.items[i].udid, data.items[i].date, data.items[i].claimDate, data.items[i].expireDate);
				}
			
			}
			else{
				alert("error!!");
			}
		}
	);
}
</script>

<%
	cRowSet = StatDAO.getInstance().getPartyTreatsUserResult(userId, sc.getInt("admin.list.cnt"), 1);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();
	num = 0;
%>
<form name="resultFrm" id="resultFrm">
<input type="hidden" name="aUserId" value="<%=userId%>">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="noMore" value="<%=totalCount>=sc.getInt("admin.list.cnt") ? "no" : "yes" %>">
<input type="hidden" name="ajaxMode" value="result">

</form>
<div id="roof_table">
PartyTreats Result
	<table cellspacing="0" cellpadding="0" id="resultTable" border="0">
		<colgroup>
			<col width="40px" />
			<col width="180px"/>
			<col />
			<col />
			<col />
			<col />
			<col width="100px"/>
			<col width="100px"/>
			<col width="100px"/>
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">PartyTreats</th>
			<th class="ta_c">WIN (Point)</th>
			<th class="ta_c">EXTRA<br/>PRIZE</th>
			<th class="ta_c">CLAIM</th>
			<th class="ta_c">UDID</th>
			<th class="ta_c">REG<br/>DATE</th>
			<th class="ta_c">CLAIM<br/>DATE</th>
			<th class="ta_c">EXPIRE<br/>DATE</th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			String title = "";
			while(cRowSet.next()){

				title = cRowSet.getString("TITLE");
				if(title.length()>20) 
					title = title.substring(0,19) + "...";

		%>
		<tr>
			<td class="ta_c"><%=++num%></td>
			<td class="ta_c"><%=title%></td>
			<td class="ta_c"><%=cRowSet.getString("WIN_YN")%> (<%=cRowSet.getString("POINT")%>)</td>
			<td class="ta_c"><%=cRowSet.getString("EXTRA_PRIZE")%></td>
			<td class="ta_c"><%=cRowSet.getString("CLAIM_YN")%></td>
			<td class="ta_c"><%=cRowSet.getString("UDID")%></td>
			<td class="ta_c"><%=cRowSet.getString("REG_DATE").substring(0, 19)%></td>
			<td class="ta_c"><%=cRowSet.getString("CLAIM_DATE").length()>0 ? cRowSet.getString("CLAIM_DATE").substring(0, 19) : ""%></td>
			<td class="ta_c"><%=cRowSet.getString("EXPIRE_DATE").substring(0, 19)%></td>
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
			<td colspan="9" class="ta_c">데이터가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
	<div class="btn_group_right_align"><input type="button" value=" PartyTreats Result 계속보기 " onclick="getResultData()" class="button white" ></div>

</div>
<br>

<script type="text/javascript">
function addRowGCLog(num, title, gc_id, udid, score, regDate){
    var tableRef = document.getElementById('gcLogTable');
	
    var newRow = tableRef.insertRow((tableRef.rows.length));

    var newCell1 = newRow.insertCell(0);
    var newCell2 = newRow.insertCell(1);
	var newCell3 = newRow.insertCell(2);
	var newCell4 = newRow.insertCell(3);
	var newCell5 = newRow.insertCell(4);
	var newCell6 = newRow.insertCell(5);

	newCell1.className = "ta_c";
	newCell2.className = "ta_c";
	newCell3.className = "ta_c";
	newCell4.className = "ta_c";
	newCell5.className = "ta_c";
	newCell6.className = "ta_c";

    var newText1 = document.createTextNode(num);
    var newText2 = document.createTextNode(title);
	var newText3 = document.createTextNode(gc_id);
	var newText4 = document.createTextNode(udid);
	var newText5 = document.createTextNode(score);
	var newText6 = document.createTextNode(regDate);


	var linkUdid=document.createElement('a');
	linkUdid.href = '/admin/stat/udid.jsp?udid='+udid;
	linkUdid.appendChild(document.createTextNode(udid));

    newCell1.appendChild(newText1);
    newCell2.appendChild(newText2);
	newCell3.appendChild(newText3);
	newCell4.appendChild(linkUdid);
	newCell5.appendChild(newText5);
	newCell6.appendChild(newText6);
}

function getGCLogData(){
	
	if(document.gcLogFrm.noMore.value=="yes"){
		alert("No more!!");
		return;
	}

	var paramData = $("#gcLogFrm").serialize();
	$.getJSON(
		'/admin/users/userDetailAjax.jsp',
		paramData,
		function(data) {
			
			if(data.result == "success"){

				document.gcLogFrm.noMore.value = data.noMore;
				document.gcLogFrm.curPage.value = data.nextPage;
				
				for(var i=0;i<data.items.length;i++){

					addRowGCLog(data.items[i].num, data.items[i].title ,data.items[i].gc_id, data.items[i].udid, data.items[i].score, data.items[i].date);
				}
			
			}
			else{
				alert("error!!");
			}
		}
	);
}
</script>

<%
	cRowSet = GameContestDAO.getInstance().getUserLog(userId, sc.getInt("admin.list.cnt"), 1);
	totalCount = cRowSet.getQueryManager().getMaxRowSize();
	num = 0;
%>
<form name="gcLogFrm" id="gcLogFrm">
<input type="hidden" name="aUserId" value="<%=userId%>">
<input type="hidden" name="curPage" value="">
<input type="hidden" name="noMore" value="<%=totalCount>=sc.getInt("admin.list.cnt") ? "no" : "yes" %>">
<input type="hidden" name="ajaxMode" value="gcLog">

</form>
<div id="roof_table">
Game Contest Log
	<table cellspacing="0" cellpadding="0" id="gcLogTable" border="0">
		<colgroup>
			<col width="40px" />
			<col width="180px"/>
			<col />
			<col width="100px"/>
			<col width="100px"/>
			<col width="100px"/>
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">Game Contest</th>
			<th class="ta_c">GC ID</th>
			<th class="ta_c">UDID</th>
			<th class="ta_c">Record</th>
			<th class="ta_c">REG_DATE</th>
		</tr>
		</thead>
		<tbody>
		<%
		try{
			while(cRowSet.next()){

		%>
		<tr>
			<td class="ta_c"><%=++num%></td>
			<td class="ta_c"><%=cRowSet.getString("TITLE")%></td>
			<td class="ta_c"><%=cRowSet.getString("GC_ID")%></td>
			<td class="ta_c"><%=cRowSet.getString("UDID")%></td>
			<td class="ta_c"><%=cRowSet.getString("SCORE")%></td>
			<td class="ta_c"><%=cRowSet.getString("REG_DATE").substring(0, 19)%></td>
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
			<td colspan="6" class="ta_c">데이터가 없습니다.</td>
		</tr>
		<%
		}
		%>
		</tbody>
	</table>
	<div class="btn_group_right_align"><input type="button" value=" Game Contest Log 계속보기 " onclick="getGCLogData()" class="button white" ></div>

</div>
<br>

<br/> <br/> <br/>
Game Contest Result
<div id="roof_table">
	<table cellspacing="0" cellpadding="0">
		<colgroup>
			<col width="50px" />
			<col />
			<col width="100px" />
			<col />
			<col width="80px" />
			<col width="80px" />
			<col width="80px" />
			<col width="80px" />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">No.</th>
			<th class="ta_c">Game Contest</th>
			<th class="ta_c">GC ID</th>
			<th class="ta_c">UDID</th>
			<th class="ta_c">Score</th>
			<th class="ta_c">Grade</th>
			<th class="ta_c">Reg.Date</th>
			<th class="ta_c">Claim.Date</th>
		</tr>
		</thead>
		<tbody>
<%

		cRowSet = GameContestDAO.getInstance().getUserResult(userId);
		num = rs.size();

		String claim_date = null;
		while(cRowSet.next()) 
		{

			claim_date = cRowSet.getString("CLAIM_DATE");
			if(claim_date==null) claim_date = "";
			if(claim_date.length()>19)
				claim_date = claim_date.substring(5,16);
%>
		<tr>
			<td class="ta_c"><%=num--%></td>
			<td class="ta_c"><%=cRowSet.getString("TITLE")%></td>
			<td class="ta_c"><%=cRowSet.getString("GC_ID")%></td>
			<td class="ta_c"><%=cRowSet.getString("UDID")%></td>
			<td class="ta_c"><%=cRowSet.getString("SCORE")%></td>
			<td class="ta_c"><%=WebUtil.printRank(cRowSet.getInt("RANK"))%></td>
			<td class="ta_c"><%=cRowSet.getString("REG_DATE").substring(5, 16)%></td>
			<td class="ta_c"><%=claim_date%></td>
		</tr>
<%
		}
%>
		</tbody>
	</table>
</div>
<br/>
<br/>


<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='userList.jsp?listCnt=<%=listCnt%>&_currPage=<%=currPage%>&sUserId=<%=sUserId%>'" value="List" class="button white" style="width:50px;" />
</div>


<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
