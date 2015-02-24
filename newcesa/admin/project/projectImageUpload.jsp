<%
	/*============================================================================
	 * @ Description : 관리자 등록시 ID 체크
	 *
	 * 작성일 : 2011.04.18
	 * 작성자 : 이정순
	 ============================================================================*/
%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/include/header.jsp" %>
<%@ include file="/include/needAdminLogin.jsp" %>
<%

boolean result = false;

String filename = null;

try{

	//FileUploader uploader = new FileUploader(request, application, sc.get("available.file.extension"));
	
	MultipartRequest multi = new MultipartRequest(request, sc.get("imageBanner.file.path"));

	//String seq = StringUtils.defaultString(multi.getParameter("seq"));

	//업로드 경로 설정
	String path = sc.get("imageBanner.file.path");
	
	log.debug("path : "+path);

	//업로드 폴더
	File f = new File(path);
	if(!f.exists()){
		f.mkdirs();
	}

	//uploader.setUploadPath(path);
	//filename = StringUtils.defaultString(uploader.getStoredFile("imageName"));

	filename = StringUtils.defaultString(multi.getFilesystemName("imageName"));

	out.println("{\"filename\":\""+filename+"\"}");
}
catch(Exception e){
	log.debug(e.toString());
	log.error(e.toString(), e);
}
%><%@ include file="/include/footer.jsp" %>
