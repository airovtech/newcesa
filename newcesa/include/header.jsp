<%@ page language="java" pageEncoding="utf-8"%><%@ 
	page import="java.io.*,
				java.sql.*,
				java.util.*,
				java.util.Date,
				java.text.*,
				javax.naming.*,
				net.sf.json.*,
				org.apache.poi.hssf.usermodel.HSSFCell,
				org.apache.poi.hssf.usermodel.HSSFRow,
				org.apache.poi.hssf.usermodel.HSSFSheet,
				org.apache.poi.hssf.usermodel.HSSFWorkbook, 
				org.apache.poi.xssf.usermodel.XSSFCell,
				org.apache.poi.xssf.usermodel.XSSFRow,
				org.apache.poi.xssf.usermodel.XSSFSheet,
				org.apache.poi.xssf.usermodel.XSSFWorkbook,
				java.util.Map.Entry"%><%@ 
	page import="com.cesa.common.*,
				com.cesa.dao.*,
				com.cesa.util.*,
				com.cesa.db.*"%><%@ 
	page import="org.apache.log4j.Logger" %><%@ 
	page import="org.apache.commons.lang.StringUtils" %><%@ 
	page import="org.apache.commons.lang.math.NumberUtils" %><%@ 
	page import="org.apache.commons.codec.digest.DigestUtils"%><%

	//Cache 없음
	response.setDateHeader("Expires", -1);
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-store"); //HTTP 1.0
	response.setHeader("Cache-Control","no-cashe"); //HTTP 1.1

	request.setCharacterEncoding("utf-8");

	String remoteAddr =request.getRemoteAddr();

	//세션 timeout 설정
	//session.setMaxInactiveInterval(3600);

	//log
	Logger log = Logger.getLogger(this.getClass());

	String useBrowser = request.getHeader("User-Agent");
	if(useBrowser==null) useBrowser = "";

	// SiteContext getting
	SiteContext sc = SiteContext.getInstance();
	
	//admin
	boolean isAdminLogin = false;
	String adminID = "";
	String sPermission = "";
	String sGroup = "";
	
	//front
	boolean isFrontLogin = false;
	String sMemberId = "";
	String sMemberSeq = "";
	String sProjectSeq = "";
	

	if(	session.getAttribute("adminID") != null ){
		isAdminLogin = true;
		adminID = (String)session.getAttribute("adminID");
		sGroup = (String)session.getAttribute("sGroup");
	}
	else {
		isAdminLogin = false;
	}

	if(	session.getAttribute("sMemberId") != null ){
		isFrontLogin = true;
		sMemberId = (String)session.getAttribute("sMemberId");
		sMemberSeq = (String)session.getAttribute("sMemberSeq");
		sProjectSeq = (String)session.getAttribute("sProjectSeq");
	}
	else {
		isFrontLogin = false;
	}


	try{
%>
