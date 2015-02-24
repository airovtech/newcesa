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
	m1 = 4;
	m2 = 2;
	m3 = 1;
%>
<script type='text/javascript' src="/js/tablefilter_all_min.js"></script>
<link rel="stylesheet" type="text/css" href="/backoffice/css/filtergrid.css">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%@ include file="/admin/include/menu.jsp" %>
<%
	// Params
	String currPage = StringUtils.defaultString(request.getParameter("currPage"),"1");
	String projectSeq = StringUtils.defaultString(request.getParameter("projectSeq"),"0");

	//out.println("projectSeq : "+projectSeq);
	RowSetMapper cRowSet = null;
	
	// Vars
	String groupSeq = "";
	

	String memberid = "";

	String property01 = "";
	String property02 = "";
	String property03 = "";
	String property04 = "";
	String property05 = "";
	String property06 = "";
	String property07 = "";
	String property08 = "";
	String property09 = "";
	String property10 = "";

	String mode = "Create";

	boolean bProperty01 = false;
	boolean bProperty02 = false;
	boolean bProperty03 = false;
	boolean bProperty04 = false;
	boolean bProperty05 = false;
	boolean bProperty06 = false;
	boolean bProperty07 = false;
	boolean bProperty08 = false;
	boolean bProperty09 = false;
	boolean bProperty10 = false;


	StringBuffer sbufTableFilter = new StringBuffer();
	
	if(!sGroup.equals("0")){
		groupSeq = sGroup;
	}
	
	cRowSet = ProjectDAO.getInstance().getProjectList(groupSeq, 1000, 1);

	StringBuffer sbufSelect = new StringBuffer();
	sbufSelect.append("<select name='projectSeq' onchange='changeProject();'>");
	sbufSelect.append("<option value=''>프로젝트를 선택해 주세요.</option>");
	while(cRowSet.next()){
		if(cRowSet.getString("seq").equals(projectSeq)){
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"' selected>"+cRowSet.getString("project_name")+" ("+cRowSet.getString("group_name")+") "+cRowSet.getString("start_date")+"~"+cRowSet.getString("end_date")+"</option> \n");
		}
		else{
			sbufSelect.append("<option value='"+cRowSet.getString("seq")+"'>"+cRowSet.getString("project_name")+" ("+cRowSet.getString("group_name")+") "+cRowSet.getString("start_date")+"~"+cRowSet.getString("end_date")+"</option> \n");
		}
	}
	sbufSelect.append("</select>");


	//항목 불러오기
	cRowSet = ProjectDAO.getInstance().projectPropertiesList(projectSeq);

	if(cRowSet.next()){
		property01 = cRowSet.getString("property01");
		property02 = cRowSet.getString("property02");
		property03 = cRowSet.getString("property03");
		property04 = cRowSet.getString("property04");
		property05 = cRowSet.getString("property05");
		property06 = cRowSet.getString("property06");
		property07 = cRowSet.getString("property07");
		property08 = cRowSet.getString("property08");
		property09 = cRowSet.getString("property09");
		property10 = cRowSet.getString("property10");
		mode = "Modify";
	}
	int tempCount = 0;
	sbufTableFilter.append("var tableFilters = { \n");
	sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");	//회원 아이디

	if(!StringUtils.isEmpty(property01)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty01=true;
	}
	if(!StringUtils.isEmpty(property02)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty02=true;
	}
	if(!StringUtils.isEmpty(property03)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty03=true;
	}
	if(!StringUtils.isEmpty(property04)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty04=true;
	}
	if(!StringUtils.isEmpty(property05)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\" \n");
		bProperty05=true;
	}
	if(!StringUtils.isEmpty(property06)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty06=true;
	}
	if(!StringUtils.isEmpty(property07)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty07=true;
	}
	if(!StringUtils.isEmpty(property08)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty08=true;
	}
	if(!StringUtils.isEmpty(property09)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty09=true;
	}
	if(!StringUtils.isEmpty(property10)) {
		sbufTableFilter.append("col_"+(tempCount++)+": \"checklist\", \n");
		bProperty10=true;
	}


	sbufTableFilter.append("} \n");

	//멤버 목록  프로퍼티 
	cRowSet = MemberDAO.getInstance().getMemberPropertiesList(projectSeq);
	//out.println(cRowSet.size());

	
%>
<script type="text/javascript">
var tempWidth = 1300;
var maxPoint = 0;
var addWidth = 0;
var tempAjaxData;

function changeProject(){
	var frm = document.frm;
	frm.action = "stat.jsp";
	frm.submit();
}

function getStatList(type){
	var frm = document.frm;

	document.getElementById("tableType").value = type;
	
	//alert(tf_propertiesTable.GetColValues(0, false));

	var params="type="+type+"&projectSeq="+frm.projectSeq.value+"&userList="+encodeURIComponent(tf_propertiesTable.GetColValues(0, false));

	$.getJSON('statAjaxList.jsp?'+params, null, function(data) {

		document.getElementById("statList").innerHTML = data.html;
		document.getElementById("etcList").innerHTML = data.htmlEtc;
		

		//테이블 길이를 정확히 알수 없어 대강 all 의 폭을 늘린다.
		if(data.chartData[0].data.length>10){
		   addWidth = data.chartData[0].data.length*20;
		}
		//alert(tempWidth);
		document.getElementById("all").style.minWidth = (tempWidth+addWidth)+"px";
		
		tempAjaxData = data.chartData;

		//alert(data.chartData.length);
		//먼저 차트를 넣을 div를 만든다.
		var divHtml = "";
		for(var i=0;i<data.chartData.length;i++){
			divHtml+="<div id=\"chart_"+(i+1)+"\" style=\"display:inline;\"></div> \n";
		}
		document.getElementById("chartList").innerHTML = divHtml;
		
		if(type=="sum"){
			maxPoint = data.maxPoint+2;
		}
		else{
			maxPoint = 6;
		}
		for(var i=0;i<data.chartData.length;i++){
			drawChart(data.chartData[i], "chart_"+(i+1));
		}
	});
}


function goDownload() {
	var frm = document.frm;
	var type = document.getElementById("tableType").value;
	var params="type="+type+"&projectSeq="+frm.projectSeq.value+"&userList="+tf_propertiesTable.GetColValues(0, false);

	document.location.href = "statExcelDownload.jsp?"+params;
}


function changeChart(chartType){
	document.getElementById("chartType").value = chartType;
	//getStatList(document.getElementById("tableType").value);
	
	for(var i=0;i<tempAjaxData.length;i++){
		drawChart(tempAjaxData[i], "chart_"+(i+1));
	}

	
}


$(window).load(function() {
	document.getElementById("all").style.minWidth = "1300px";
	var tempCount = 0;
	<%=sbufTableFilter%>
    var tf03 = setFilterGrid("propertiesTable", 1, tableFilters)

});

</script>

<div class="top_title">프로젝트 통계 설정</div>
<form name="frm" id="frm" method="post" action="projectMemberList.jsp">
<input type="hidden" name="mode" value="<%=mode%>" />
<input type="hidden" name="chartType" id="chartType" value="" />
<input type="hidden" name="tableType" id="tableType" value="" />
<div>

</div>
<div id="form_warp">
	<dl>

		<dt class="inpt_dt">프로젝트명</dt>
		<dd class="inpt_dd">
			<%=sbufSelect%>
		</dd>
	</dl>         
</div>
<div style="padding-top:50px;">
<table cellspacing="0" cellpadding="0" id="propertiesTable" width="905">

	<tr>
		<th>참가자</th>
		<% 
			if(bProperty01) out.println("<th>"+property01+"</th>"); 
			if(bProperty02) out.println("<th>"+property02+"</th>"); 
			if(bProperty03) out.println("<th>"+property03+"</th>"); 
			if(bProperty04) out.println("<th>"+property04+"</th>"); 
			if(bProperty05) out.println("<th>"+property05+"</th>"); 
			if(bProperty06) out.println("<th>"+property06+"</th>"); 
			if(bProperty07) out.println("<th>"+property07+"</th>"); 
			if(bProperty08) out.println("<th>"+property08+"</th>"); 
			if(bProperty09) out.println("<th>"+property09+"</th>"); 
			if(bProperty10) out.println("<th>"+property10+"</th>"); 
		%>
	</tr>
<%
	while(cRowSet.next()){
		memberid = cRowSet.getString("memberid");
		property01 = cRowSet.getString("property01");
		property02 = cRowSet.getString("property02");
		property03 = cRowSet.getString("property03");
		property04 = cRowSet.getString("property04");
		property05 = cRowSet.getString("property05");
		property06 = cRowSet.getString("property06");
		property07 = cRowSet.getString("property07");
		property08 = cRowSet.getString("property08");
		property09 = cRowSet.getString("property09");
		property10 = cRowSet.getString("property10");
%>
	<tr>
		<td><%=memberid%></td>
		<%
			if(bProperty01) out.println("<td>"+property01+"</td>"); 
			if(bProperty02) out.println("<td>"+property02+"</td>"); 
			if(bProperty03) out.println("<td>"+property03+"</td>"); 
			if(bProperty04) out.println("<td>"+property04+"</td>"); 
			if(bProperty05) out.println("<td>"+property05+"</td>"); 
			if(bProperty06) out.println("<td>"+property06+"</td>"); 
			if(bProperty07) out.println("<td>"+property07+"</td>"); 
			if(bProperty08) out.println("<td>"+property08+"</td>"); 
			if(bProperty09) out.println("<td>"+property09+"</td>"); 
			if(bProperty10) out.println("<td>"+property10+"</td>"); 
		%>
	</tr>
<%
	}	
%>
</table>
</div>
</form>

<div class="btn_group_right_align">
	<input type="button" onclick="getStatList('sum');" value="합계 점수보기" class="button black" style="width:100px;" />
	<input type="button" onclick="getStatList('avg');" value="평균 점수보기" class="button black" style="width:100px;" />
</div>


<div id="statList" style="padding-top:50px;">
</div>
<div id="etcList" style="padding-top:50px;">
</div>


<script type="text/javascript">

google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);

function drawChart(chartData, index) {
	
	var data = google.visualization.arrayToDataTable(chartData.data);
	var options = {
		title: chartData.title,
		width: (tempWidth+addWidth)-400, 
		height: 250,
		legend:"none",
		chartArea:{left:40,top:70, width:"95%", height:"60%"},
		hAxis: {textStyle: { color: '#000000', fontSize: '10', paddingRight: '10', marginRight: '10'} },
		vAxis: {textStyle: { color: '#000000', fontSize: '10', paddingRight: '10', marginRight: '10', minValue:0, maxValue:5, format:'0'}, viewWindow:{min:0, max:maxPoint} },
		pointSize:3,
		backgroundColor:"#FFFFFF"
	};
	
	var chart;
	if(document.getElementById("chartType").value=="line"){
		chart = new google.visualization.LineChart(document.getElementById(index));
	}
	else{
		chart = new google.visualization.ColumnChart(document.getElementById(index));
	}

	chart.draw(data, options);

}

</script>

<div id="chartList"></div>
<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>