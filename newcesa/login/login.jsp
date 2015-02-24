<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerFront.jsp" %>
<%
	
	RowSetMapper cRowSet = null;

	/*
	if(!"".equals(seq)){
		cRowSet = ProjectDAO.getInstance().getProjectDetail(seq);
		
		if(cRowSet.next()){
			projectName = cRowSet.getString("project_name");
			projectDesc = cRowSet.getString("project_desc");
			startDate = cRowSet.getString("start_date");
			endDate = cRowSet.getString("end_date");
			image = cRowSet.getString("image");

			groupSeq = cRowSet.getString("user_group");
		}
	}
	*/

%>

	<div style="width:100%; padding:0 0; text-align:center;">
		<img src="/img/front/top_logo.png" width="100%" />
	</div>
	<div style="width:87.5%; padding:0 6.25%; margin-top:30px;">
		<img src="/img/front/project_img.png" alt="Project img" style="width:100%; padding-bottom:15px;"/>
		<span style="width:100%; text-align:left;">
		 프로젝트 설명글 출력
		</span>
		<div style="width:100%; margin-top:25px;">
			<div style="width:100%;">
				<input type="text" id="userId" name="userId" value="" maxlength="15" title="아이디 입력" style="width:95.8%; height:22px;" />
			</div>
			<div style="width:100%; padding: 20px 0;">
				<input type="password" id="userPwd" name="userPwd"  maxlength="20"title="비밀번호 입력" style="width:95.8%; height:22px;" />
			</div>
		</div>
		<div style="padding-bottom:50px;"><img src="/img/front/login_btn.png" width="100%" /></div>
		<div style="padding-bottom:50px;"><img src="/img/front/cdi_logo.png" width="100%" /></div>
	</div><!--Content End-->


<%@ include file="/include/footer.jsp" %>
<%@ include file="/include/footerFront.jsp" %>