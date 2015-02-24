<%
	/*============================================================================
	 * @ Description : 관리자용 레이아웃 헤더
	 *
	 * 작성일 : 2010.12.16
	 * 작성자 : 우진호
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/needAdminLogin.jsp" %>
<%
	int m1 = 0, m2 = 0, m3 = 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title> CESA : Admin </title>
<!--[if IE 6]>
	<script type="text/javascript" src="/js/minmax.js"></script>
<![endif]-->
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.6.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery-block-ui.js"></script>
<script type="text/javascript" src="/js/calendarpop.js"></script>
<script type="text/javascript" src="/js/strFunction.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script type="text/javascript" src="/js/jquery.form.js"></script>

<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>


<link rel="stylesheet" type="text/css" href="/backoffice/css/default.css" />
<link rel="stylesheet" type="text/css" href="/backoffice/css/layout.css" />
<link rel="stylesheet" type="text/css" href="/backoffice/css/btn_ui.css" />
<link rel="stylesheet" type="text/css" href="/backoffice/css/login.css" />
<link rel="stylesheet" type="text/css" href="/backoffice/css/popup.css" />

<link rel="stylesheet" type="text/css" href="/css/admin/extended.css" />
</head>

<body>
	<div id="all" style="min-width:1200px;"><!-- all 시작 -->
	<div id="mask"></div>