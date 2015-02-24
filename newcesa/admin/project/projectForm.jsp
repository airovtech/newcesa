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
<%
	// Params
	String seq = StringUtils.defaultString(request.getParameter("seq"));
	String currPage = StringUtils.defaultString(request.getParameter("currPage"),"1");
	String mode = "".equals(seq) ? "Create" : "Modify";
	RowSetMapper cRowSet = null;
	
	// Vars
	String projectName = "";
	String projectDesc = "";
	String startDate = DateUtil.getToday("YYYY-MM-DD");
	String endDate = DateUtil.getToday("YYYY-MM-DD");
	String image = "";

	String groupSeq = "";

	

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
	

	//그룹 select	1000으로 셋팅
	StringBuffer sbufSelect = new StringBuffer();
	cRowSet = GroupDAO.getInstance().groupList(1000, 1);
	sbufSelect.append("<select name='groupSeq' id='groupSeq'>");
	while(cRowSet.next()){
		if(groupSeq.equals(cRowSet.getString("seq"))){
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"' selected>"+cRowSet.getString("group_name")+"</option>");
		}
		else{
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("group_name")+"</option>");
		}
	}
	sbufSelect.append("</select>");


	//경험어휘 select
	StringBuffer sbufVocabulary = new StringBuffer();
	cRowSet = VocabularyDAO.getInstance().getGroupList(groupSeq);
	sbufVocabulary.append("<select name='vGroupSeq' id='vGroupSeq'>");
	while(cRowSet.next()){
		sbufVocabulary.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("group_name")+"</option>");
	}
	sbufVocabulary.append("</select>");


%>
<script type="text/javascript">
function goSubmit(){
	var f = document.frm;

	if ( isEmpty(f.projectName.value)) {
		alert("프로젝트명을 입력하세요.");
		f.projectName.focus();
		return;
	}

	if ( isEmpty(f.projectDesc.value)) {
		alert("프로젝트 설명을 입력하세요.");
		f.projectDesc.focus();
		return;
	}

	if ( isEmpty(f.tempImage.value)) {
		alert("이미지를 업로드하세요.");
		f.tempImage.focus();
		return;
	}


	f.action = "projectProcStep01.jsp";
	f.submit();
}


$(document).ready(function() { 

	var options = { 
        target:        '#output2',   // target element(s) to be updated with server response 
        beforeSubmit:  showRequest,  // pre-submit callback 
        success:       showResponse,  // post-submit callback 
		url:       'projectImageUpload.jsp',         // override for form's 'action' attribute 
        dataType:  'json',        // 'xml', 'script', or 'json' (expected server response type) 
        clearForm: true,        // clear all form fields after successful submit 
        resetForm: true        // reset the form after successful submit 
    }; 

	$('#imageFileForm').submit(function() { 
        $(this).ajaxSubmit(options); 
        return false; 
    }); 

});

// pre-submit callback 
function showRequest(formData, jqForm, options) { 
    var queryString = $.param(formData); 
    return true; 
} 
 
// post-submit callback 
function showResponse(responseText, statusText, xhr, $form)  { 
	document.getElementById("preImage").src = "/upload/imageBanner/"+responseText.filename;
	document.getElementById("tempImage").value = responseText.filename;
}

function sampleDownload()
{
	location.href = "/upload/activity_sample.xlsx";
}
</script>

<div class="top_title">프로젝트 <%="".equals(seq) ? "등록" : "수정"%></div>

<form name="frm" id="frm" method="post" action="projectProc.jsp">
<div>
	<input type="hidden" name="mode" value="<%=mode%>" />
	<input type="hidden" name="seq" value="<%=seq%>" />
</div>
<div id="form_warp">
	<dl>
		<%
			if(sGroup.equals("0")){
		%>
		<dt class="inpt_dt">그룹</dt>
		<dd class="inpt_dd"><%=sbufSelect%></dd>
		<%
			}
			else{
		%>
			<input type="hidden" name="groupSeq" id="groupSeq" value="<%=sGroup%>" />
		<%
			}
		%>

		<dt class="inpt_dt">프로젝트명</dt>
		<dd class="inpt_dd">
			<input type="text" name="projectName" id="projectName" value="<%=projectName%>" maxlength="40" size="40" />
		</dd>

		<dt class="inpt_dt" style="height:55px;">프로젝트 설명</dt>
		<dd class="inpt_dd" style="height:55px;">
			<textarea name="projectDesc" rows="3" cols="90"></textarea>
		</dd>

		<dt class="inpt_dt">프로젝트 기간</dt>
		<dd class="inpt_dd">
			<input type="text" id="startDate" name="startDate" style="width:75px;" value="<%=startDate%>" readonly='readonly' /> <input type="button" onclick="callCalendar(getElementById('startDate'))" value="달력"/>
			~
			<input type="text" id="endDate" name="endDate" style="width:75px;" value="<%=endDate%>" readonly='readonly' /> <input type="button" onclick="callCalendar(getElementById('endDate'))" value="달력"/>
		</dd>
		
		<dt class="inpt_dt" style="height:100px;">이미지</dt>
		<dd class="inpt_dd" style="height:100px;">
			<img name="preImage" id="preImage" src="/images/blank.gif" height="90" />
			<input type="hidden" name="tempImage" id="tempImage" value="" />
		</dd>
		</form>
		<dt class="inpt_dt">이미지 업로드</dt>
		<dd class="inpt_dd">
			<form name="imageFileForm" id="imageFileForm" method="post" enctype="multipart/form-data">
				<input type="file" id="imageName" name="imageName" value="" size="15"/> <input type="submit" class="button blue" value="저장" style="width:45px"/>
			</form>
		</dd>

		<!--

		<dt class="inpt_dt">Activity 업로드</dt>
		<dd class="inpt_dd">
			<form name="activityFileForm" id="activityFileForm" method="post" enctype="multipart/form-data">
				<input type="file" id="imageName" name="imageName" value="" size="15"/> <input type="submit" class="button blue" value="저장" style="width:45px"/>
				&nbsp;&nbsp;&nbsp;<input type="button" class="button white" value="샘플파일 다운로드" style="width:120px" onclick='sampleDownload()'/>
			</form>
		</dd>

		<dt class="inpt_dt">경험어휘 설정</dt>
		<dd class="inpt_dd">
			<%=sbufVocabulary%>
		</dd>
		-->
		
	</dl>         
</div>
<div class="btn_group_right_align">
	<input type="button" onclick="document.location.href='projectList.jsp'" value="Cancel" class="button white" style="width:50px;" />
	<input type="button" onclick="goSubmit();" value="Next" class="button black" style="width:50px;" />
</div>

<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
