<%@ page language="java" contentType="text/html; charset=utf-8"%>
<script type="text/javascript">
function showMenu(index){
	var maxMenuIndex = 6;

	for (var i=1 ; i <= maxMenuIndex ; i++)	{
		if(i == index){
			$("#m" + i).show();
		} else {
			$("#m" + i).hide();
		}
	}
}

function goMemberId(){
	var frm = document.memberIdForm;
	frm.submit();
}
</script>
		<div id="header"><!-- header 시작 -->
			<dl>
				<dt class="h_logo">
					<a href="/admin/" name="top"><img src="/images/login_bgH.gif" style="height:38px;"/></a>
				</dt>
				<dd class="h_menu_top">
					<div>
						<dl>
							<dt style="float:right;text-align:right;">
								<%if(isAdminLogin){%>
								<%=adminID%> - 
								<a href="/admin/login/logout.jsp" class="r_header">Logout</a>
								<%}%>
							</dt>
						</dl>
					</div>
				</dd>



				<dd class="h_menu_list" style="position:absolute;margin:30px 0 0 250px;">

					<div>
						<dl>
							<dt class="h_menu h_header_<%=m1==1 ? "on":"off"%> pointer"
								onmouseover="this.className='h_menu h_header_on pointer'"
								onmouseout="this.className='h_menu h_header_<%=m1==1 ? "on":"off"%> pointer'"
								onclick="showMenu(1);">관리자 관리</dt>
							<dt class="h_menu h_header_<%=m1==2 ? "on":"off"%> pointer"
								onmouseover="this.className='h_menu h_header_on pointer'"
								onmouseout="this.className='h_menu h_header_<%=m1==2 ? "on":"off"%> pointer'"
								onclick="showMenu(2);">경험어휘 관리</dt>
							<dt class="h_menu h_header_<%=m1==3 ? "on":"off"%> pointer"
								onmouseover="this.className='h_menu h_header_on pointer'"
								onmouseout="this.className='h_menu h_header_<%=m1==3 ? "on":"off"%> pointer'"
								onclick="showMenu(3);">프로젝트 관리</dt>
							<dt class="h_menu h_header_<%=m1==4 ? "on":"off"%> pointer"
								onmouseover="this.className='h_menu h_header_on pointer'"
								onmouseout="this.className='h_menu h_header_<%=m1==4 ? "on":"off"%> pointer'"
								onclick="showMenu(4);">통계 관리</dt>
						</dl>
					</div>
				</dd>
			</dl>

		</div> <!-- header 끝 -->
		
		<div id="header_gradation"></div>
		
		<div id="all_container"><!-- all_container 시작 -->
			<div id="content"> <!-- content 시작 -->
			
				<div  id="left_warp"><!-- left menu 시작 -->
					<div id="left" >
						<!-- Menu 1 -->
						<dl id="m1" style="<%=m1==1 ? "" : "display:none;"%>">
							<dt class="top_title">| 관리자 관리</dt>
							<dt class="<%=m1 == 1 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 1 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/users/adminList.jsp">관리자 관리</a>
							</dt>
							<%
								//슈퍼 관리자만 보임
								if(sGroup.equals("0")){
							%>
							<dt class="<%=m1 == 1 && m2 == 2 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 1 && m2 == 2 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/users/groupList.jsp">그룹 관리</a>
							</dt>
							<%
								}
							%>
						</dl>


						<!-- Menu 2 -->
						<dl id="m2" style="<%=m1==2 ? "" : "display:none;"%>">
							<dt class="top_title">| 경험어휘 관리</dt>
							<dt class="<%=m1 == 2 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 2 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/vocabulary/vocabularyList.jsp">경험어휘 관리</a>
							</dt>
							<dt class="<%=m1 == 2 && m2 == 2 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 2 && m2 == 2 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/vocabulary/groupList.jsp">경험어휘 그룹관리</a>
							</dt>
						</dl>

						<!-- Menu 3 -->
						<dl id="m3" style="<%=m1==3 ? "" : "display:none;"%>">
							<dt class="top_title">| 프로젝트 관리</dt>

							<dt class="<%=m1 == 3 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 3 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/project/projectList.jsp">프로젝트 관리</a>
							</dt>
						</dl>
						
						
						<!-- Menu 4 -->
						<dl id="m4" style="<%=m1==4 ? "" : "display:none;"%>">
							<dt class="top_title">| 통계 관리</dt>
							<dt class="<%=m1 == 4 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 4 && m2 == 1 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/stat/projectMemberList.jsp">사용자 설정</a>
							</dt>
							<dt class="<%=m1 == 4 && m2 == 2 ? "depth2_sel" : "depth2_nosel"%>"
								onmouseover="this.className='depth2_sel pointer'"
								onmouseout="this.className='<%=m1 == 4 && m2 == 2 ? "depth2_sel" : "depth2_nosel"%>'">
								<a href="/admin/stat/stat.jsp">통계 관리</a>
							</dt>
						</dl>
						
					</div>
				</div><!-- left menu 끝 -->
				<div id="right_warp"><!-- right 시작 -->
					<div id="right">
