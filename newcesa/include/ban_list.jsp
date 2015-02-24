<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%!

// delimiter : ,
/*	// 2014-02-03 DB에서 금칙어 관리하는 걸로 변경
public String ban_kr = "섹스, 원조교제";
public String ban_us = "penis, fuck, sex, porn, suck";
public String ban_jp = "うんち";
*/


public boolean isBan_KR(String data) throws Exception{
	return _isBan(data);
	//return _isBan(data, ban_kr);
}
public boolean isBan_JP(String data) throws Exception{
	return _isBan(data);
	//return _isBan(data, ban_jp);
}
public boolean isBan_US(String data) throws Exception{
	return _isBan(data.toLowerCase());
	//return _isBan(data.toLowerCase(), ban_us.toLowerCase());
}

public boolean _isBan(String data) throws Exception
{

	RowSetMapper banList = BbsDAO.getInstance().listBanAll();
	
	Logger log = Logger.getLogger(this.getClass());

	while(banList.next()) {

		String token = banList.getString("CONTENT");
		token = token.trim();		// 공백제거
		if(token.length()==0)		// 빈 문자열의 경우 무시
			continue;

		if(data.indexOf(token)!=-1) {	// 원본문자열에 ban_list가 존재하면 isBan true
	
			log.debug("Check : isBan true ("+data+") ("+token+")");
			return true;
		}
	}

	log.debug("isBan false ("+data+")");

	return false;

}

public boolean _isBanIp(String sip) throws Exception
{

	RowSetMapper ipList = BbsDAO.getInstance().listIpAll();
	
	Logger log = Logger.getLogger(this.getClass());

	while(ipList.next()) {

		String ip = ipList.getString("IP");

		if(sip.equals(ip)) {		// IP 가 일치하면 ban
	
			log.debug("Check : isBan IP true ("+sip+") ("+ip+")");
			return true;
		}
	}

	log.debug("isBan IP false ("+sip+")");

	return false;

}
%>
