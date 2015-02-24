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
	String adminid = "";
	String email = "";
	String useYN = "";
	String regDate = "";
	String url = "";
	String groupSeq = "";


	if(!"".equals(seq)){
		cRowSet = AdminDAO.getInstance().getAdminDetail(seq);
		
		if(cRowSet.next()){
			adminid = cRowSet.getString("adminid");
			email = cRowSet.getString("email");
			useYN = cRowSet.getString("useYN");
			groupSeq = cRowSet.getString("user_group");
		}
	}
	

	//그룹 select	1000으로 셋팅
	StringBuffer sbufSelect = new StringBuffer();
	cRowSet = GroupDAO.getInstance().groupList(1000, 1);
	sbufSelect.append("<select name='groupSeq'>");
	sbufSelect.append("<option value='0'>슈퍼 관리자</option>");
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

	if (f.mode.value == "Create") {
		if ( isEmpty(f.adminid.value)) {
			alert("아이디를 입력하세요.");
			f.adminid.focus();
			return;
		} else if ( f.checkID.value != "1")	{
			alert("아이디 중복검사를 해주세요.");
			return;
		}
	}

	if (f.mode.value == "Create" || !isEmpty(f.password.value) ) {

		if ( isEmpty(f.password.value)) {
			alert("비밀번호를 입력하세요.");
			f.password.focus();
			return;

		} else if (f.password.value.length < 6) {
			alert("비밀번호 길이를 최소 6자 이상으로 입력해주세요");
			return;

		} else if ( f.password.value != f.verify.value) {
			alert("비밀번호가 일치하지 않습니다.");
			return;
		}
	}

	if ( isEmpty(f.email.value)) {
		alert("메일주소를 입력하세요.");
		f.email.focus();
		return;
	} else if (!isValidEmail(f.email.value)) {
		alert("유효한 이메일 주소가 아닙니다.");
		f.email.focus();
		return;
	}

	f.action = "adminProc.jsp";
	f.submit();
}

function duplCheck(checkID) {
	$.getJSON('adminIdCheck.jsp?adminid=' + checkID, null, function(data) {
		if(data.code == 0)	{
			$("#checkID").val(1);
		}
		alert(data.msg);
	});
}
</script>

<div class="top_title">관리자 <%="".equals(seq) ? "등록" : "수정"%></div>


<form name="frm" method="post" action="">
<div>
	<input type="hidden" name="checkID" id="checkID" value="" />
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="seq" value="<%=seq%>" />
</div>
<div id="form_warp">
	<dl>
		<dt class="inpt_dt">ID</dt>
		<dd class="inpt_dd">
		<%if(mode.equals("Create")){%>
			<input type="text" name="adminid" id="adminid" value="<%=adminid%>" maxlength="20" onchange="$('#checkID').val('');" />
			<input type="button" onclick="duplCheck($('#adminid').val())" value="중복확인" class="button white" />
		<%} else {
			out.print(adminid);
		}%>
		</dd>
		
		<dt class="inpt_dt">비밀번호</dt>
		<dd class="inpt_dd"><input type="password" name="password" maxlength="30" /></dd>
		
		<dt class="inpt_dt">비밀번호 확인</dt>
		<dd class="inpt_dd"><input type="password" name="verify" maxlength="30" /></dd>

		<dt class="inpt_dt">이메일</dt>
		<dd class="inpt_dd"><input type="text" name="email" value="<%=email%>" maxlength="50" style="300px;"/></dd>

		<%
			if(sGroup.equals("0")){
		%>
		<dt class="inpt_dt">그룹</dt>
		<dd class="inpt_dd"><%=sbufSelect%></dd>
		<%
			}	
		%>
	</dl>         
</div>

</form>

<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='adminList.jsp'" value="Cancel" class="button white" style="width:50px;" />
	<input type="button" onclick="goSubmit();" value="Save" class="button black" style="width:50px;" />
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
