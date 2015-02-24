<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerFront.jsp" %>
<%
	
	RowSetMapper cRowSet = null;

	String seq = StringUtils.defaultString(request.getParameter("pNo"));

	if(StringUtils.isEmpty(seq)){
		out.println("<script language='javascript'>");
		out.println("alert('프로젝트 URL을 확인 하세요.');");
		out.println("</script>");
	}

	String projectName = null;
	String projectDesc = null;
	String image = null;
	String groupSeq = null;
	
	
	if(!"".equals(seq)){
		cRowSet = ProjectDAO.getInstance().getProjectDetail(seq);
		
		if(cRowSet.next()){
			projectName = cRowSet.getString("project_name");
			projectDesc = cRowSet.getString("project_desc");
			//startDate = cRowSet.getString("start_date");
			//endDate = cRowSet.getString("end_date");
			image = cRowSet.getString("image");

			groupSeq = cRowSet.getString("user_group");
		}
	}
	
	if(StringUtils.isEmpty(projectDesc)){
		projectDesc = "";
	}

	if(StringUtils.isEmpty(image)){
		image = "/img/front/project_img.png";
	}
	else{
		image = sc.get("imageBanner.file.url")+"/"+image;
	}
	
%>
<script type="text/javascript">
function login(){

	var frm = document.loginFrm;
	if ( isEmpty(frm.memberId.value) ) {
		alert("아이디를 입력하세요.");
		frm.memberId.focus();
		return;
	}

	if ( isEmpty(frm.memberPasswd.value) ) {
		alert("패스워드를 입력하세요.");
		frm.memberPasswd.focus();
		return;
	}
	frm.submit();
}


$(function(){
	$("#memberId").focus(function(){
       $(this).removeClass("backLoginId");
	   $(this).addClass("backLoginId_empty");
    }).blur(function(){
        if(isEmpty($(this).val()))
          $(this).addClass("backLoginId");
    });
 
    $("#memberPasswd").focus(function(){
        $(this).removeClass("backLoginPasswd");
		$(this).addClass("backLoginPasswd_empty");
    }).blur(function(){
        if(isEmpty($(this).val())){
          $(this).addClass("backLoginPasswd");
		}
    });
});

</script>

<div style="width:100%; padding:0 0; text-align:center;">
	<img src="/img/front/top_logo.png" width="100%" />
</div>
<div style="width:87.5%; padding:0 6.25%; margin-top:30px;">

	<img src="<%=image%>" alt="Project img" style="width:100%; padding-bottom:15px;"/>

	<div style="width:100%; text-align:center;"><%=projectDesc%></div>
	<div style="width:100%; margin-top:25px;">
		<form name="loginFrm" id="loginFrm" action="/login/loginProc.jsp" method="post">
		<input type="hidden" name="projectSeq" id="projectSeq" value="<%=seq%>" />
		<div style="width:100%;">
			<input type="text" id="memberId" name="memberId" value="" maxlength="30" title="아이디 입력" class="backLoginId" />
		</div>
		<div style="width:100%; padding: 20px 0;">
			<input type="password" id="memberPasswd" name="memberPasswd"  maxlength="20"title="비밀번호 입력" class="backLoginPasswd" />
		</div>
		</form>
	</div>
	<div style="padding-bottom:50px;"><a href="#" onclick="login();"><img src="/img/front/login_btn.png" width="100%" /></a></div>
	<div style="padding-bottom:50px;"><img src="/img/front/cdi_logo.png" width="100%" /></div>
</div><!--Content End-->


<%@ include file="/include/footer.jsp" %>
<%@ include file="/include/footerFront.jsp" %>