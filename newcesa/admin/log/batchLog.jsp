<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/headerAdmin.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/dnt7-com.tld" prefix="dnt7" %>
<%
	m1 = 2;
	m2 = 2;
	m3 = 1;
%>
<%@ include file="/admin/include/menu.jsp" %>
<%
%>
<div class="top_title" style='width:700px;'>
<a href='batchLog.jsp' class='top_title'>Batch Log</a>
<a href='htmlLog.jsp?server=jp' class='top_title'>[JP HTML]</a>
<a href='htmlLog.jsp?server=us' class='top_title'>[US HTML]</a>
<a href='htmlLog.jsp?server=kr' class='top_title'>[KR HTML]</a>
</div>
<br>
<br>


<div id="roof_table">
JP : <a href='http://pad.zap.jp.net' target=pad>http://pad.zap.jp.net</a> (2014-02-26 변경)
<br/>
US : <a href='http://pad.wikia.com/wiki/Homepage/NASchedule?action=render' target=pad>http://pad.wikia.com/wiki/Homepage/NASchedule?action=render</a>
<br/>
KR : <a href='http://pad.inven.co.kr/' target=pad>http://pad.inven.co.kr/</a>
<br/>
JP_SPECIAL : <a href='http://pad.zap.jp.net/' target=pad>http://pad.zap.jp.net/</a>
<br/>
US_SPECIAL : <a href='http://pad.wikia.com/wiki/Homepage/NASchedule?action=render' target=pad>http://pad.wikia.com/wiki/Homepage/NASchedule?action=render</a>
<br/>
KR_SPECIAL : <a href='http://pad.inven.co.kr/' target=pad>http://pad.inven.co.kr/</a>
<br/>
<br/>
<font color=blue> * 참고사이트 </font>
<br/>JP : 중국사이트 <a href='http://pad.skyozora.com/' target=pad>http://pad.skyozora.com/</a>
<br/>JP : 일본사이트 <a href='http://www.pazzbot.net/' target=pad>http://www.pazzbot.net/</a>
<br/>KR : ThisIsGame <a href='http://thisisgame.com/pad/' target=pad>http://thisisgame.com/pad/</a>
<br/>
<br/>
	<table cellspacing="0" cellpadding="0" border="0">
		<colgroup>
			<col width="100px" />
			<col />
		</colgroup>
		<thead>
		<tr>
			<th class="ta_c">Seq</th>
			<th class="ta_c">Log File</th>
		</tr>
		</thead>
		<tbody>
		<%
			ArrayList <String> list = new ArrayList <String> ();
			File logDir = new File("/home/project/pad/logs/");
			String [] fileList = logDir.list();
			int seq = 0;

			for(String filename : fileList) {

				if(!filename.startsWith("batch"))
					continue;

				list.add(filename);
				seq++;

			}

			Collections.sort(list);

			seq = 0;
			String filename = "";
			for(int i=list.size()-1; i>=0; i--) {
				filename = list.get(i);
				seq++;
				
				if(seq>50)
					break;
		%>
		<tr>
			<td class="ta_c">
			<%=seq%>
			
			</td>
			<td class="ta_c">
			<a href='/logs/<%=filename%>' target=log><%=filename%></a>
			</td>
		</tr>
		<%
			}
		%>
		</tbody>
	</table>
</div>
<%@ include file="/include/footerAdmin.jsp" %>
<%@ include file="/include/footer.jsp" %>
