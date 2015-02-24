<%
	/*============================================================================
	 * @ Description : 관리자 정보 등록, 수정 폼
	 *
	 * 작성일 : 2011.04.18
	 * 작성자 : 이정순
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%><%@ include file="/include/header.jsp" %><%@ page import="net.sf.json.*" %><%
	
	String result = "success";
	JSONObject json = new JSONObject();
	JSONObject item = new JSONObject();
	JSONArray items = new JSONArray();

	
	RowSetMapper cRowSet = null;
	
	String aUserId = StringUtils.defaultString(request.getParameter("aUserId"));
	String ajaxMode = StringUtils.defaultString(request.getParameter("ajaxMode"));
	int curPage = Integer.parseInt(StringUtils.defaultIfEmpty(request.getParameter("curPage"),  "2"));
	int pageListCount = sc.getInt("admin.list.cnt");

	int totalCount = 0;


	int num = 0;
	String title = "";
	String type1 = "";
	String type2 = "";
	String ip = "";
	String udid = "";
	String date = "";
	String score = "";
	String gc_id = "";


	String win = "";
	String point = "";
	String extra = "";
	String claim = "";
	String claimDate = "";
	String expireDate = "";


	try{
		

		if(ajaxMode.equals("log")){

			cRowSet = StatDAO.getInstance().getPartyTreatsUser(aUserId, sc.getInt("admin.list.cnt"), curPage);
			totalCount = cRowSet.getQueryManager().getMaxRowSize();
			
			

			num = pageListCount*(curPage-1);
			
			while(cRowSet.next()){
				
				num++;
				title = cRowSet.getString("TITLE");
				if(title.length()>20) {
					title = title.substring(0,19) + "...";
				}
			
				type1 = (cRowSet.getString("GETIT_YN").equals("Y") ? "Get" : "");
				type2 = (cRowSet.getString("INSTALL_YN").equals("Y") ? "Install" : "");

				ip = cRowSet.getString("IP");
				udid = cRowSet.getString("UDID");
				date = cRowSet.getString("REG_DATE").substring(0, 19);


				item = new JSONObject();
				item.put("num", ""+num);
				item.put("title", title);
				item.put("type1", type1);
				item.put("type2", type2);
				item.put("ip", ip);
				item.put("udid", udid);
				item.put("date", date);

				items.add(item);
			}

			if(totalCount == num){
				json.put("noMore", "yes");
			}
			else{
				json.put("noMore", "no");
			}
			

			json.put("items", items);
			json.put("nextPage", curPage+1);
			json.put("totalCount", totalCount);
		}
		else if(ajaxMode.equals("result")){

			cRowSet = StatDAO.getInstance().getPartyTreatsUserResult(aUserId, sc.getInt("admin.list.cnt"), curPage);
			totalCount = cRowSet.getQueryManager().getMaxRowSize();
			

			num = pageListCount*(curPage-1);
			
			while(cRowSet.next()){
				
				num++;
				title = cRowSet.getString("TITLE");
				if(title.length()>20) {
					title = title.substring(0,19) + "...";
				}
				

				win = cRowSet.getString("WIN_YN");
				point = cRowSet.getString("POINT");
				extra = cRowSet.getString("EXTRA_PRIZE");
				claim = cRowSet.getString("CLAIM_YN");
				claimDate = cRowSet.getString("CLAIM_DATE").substring(0, 19);
				expireDate = cRowSet.getString("EXPIRE_DATE").substring(0, 19);

				udid = cRowSet.getString("UDID");
				date = cRowSet.getString("REG_DATE").substring(0, 19);

				item = new JSONObject();
				item.put("num", ""+num);
				item.put("title", title);
				item.put("win", win);
				item.put("point", point);
				item.put("extra", extra);
				item.put("claim", claim);
				item.put("udid", udid);
				item.put("date", date);
				item.put("claimDate", claimDate);
				item.put("expireDate", expireDate);

				items.add(item);
			}

			if(totalCount == num){
				json.put("noMore", "yes");
			}
			else{
				json.put("noMore", "no");
			}
			

			json.put("items", items);
			json.put("nextPage", curPage+1);
			json.put("totalCount", totalCount);
		}
		else if(ajaxMode.equals("gcLog")){

			cRowSet = GameContestDAO.getInstance().getUserLog(aUserId, sc.getInt("admin.list.cnt"), curPage);
			totalCount = cRowSet.getQueryManager().getMaxRowSize();
			

			num = pageListCount*(curPage-1);
			
			while(cRowSet.next()){
				
				num++;
				title = cRowSet.getString("TITLE");
				gc_id = cRowSet.getString("GC_ID");
				udid = cRowSet.getString("UDID");
				score = cRowSet.getString("SCORE");
				date = cRowSet.getString("REG_DATE").substring(0, 19);


				item = new JSONObject();
				item.put("num", ""+num);
				item.put("title", title);
				item.put("gc_id", gc_id);
				item.put("udid", udid);
				item.put("score", score);
				item.put("date", date);

				items.add(item);
			}

			if(totalCount == num){
				json.put("noMore", "yes");
			}
			else{
				json.put("noMore", "no");
			}
			

			json.put("items", items);
			json.put("nextPage", curPage+1);
			json.put("totalCount", totalCount);
		}
		

	}
	catch(Exception e){
		result = "fail";
		e.getMessage();
		log.fatal(e, e);
	}
	finally{

		json.put("result", result);
		out.println(json);

	}

%>
<%@ include file="/include/footer.jsp" %>
