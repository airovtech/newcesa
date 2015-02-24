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

String result = "fail";
String filename = null;

JSONObject json = new JSONObject();
JSONArray items = new JSONArray();

try{

	//업로드 경로 설정
	String path = sc.get("excel.file.path");

	//업로드 폴더
	File f = new File(path);
	if(!f.exists()){
		f.mkdirs();
	}

	MultipartRequest multi = new MultipartRequest(request, sc.get("excel.file.path"));

	filename = StringUtils.defaultString(multi.getFilesystemName("filename"));

	//excel 파일 읽기
	//POIFSFileSystem excel  =  new POIFSFileSystem(new FileInputStream("/home/project/newcesa/upload/activity_sample.xlsx"));

	log.debug("path : "+ sc.get("excel.file.path"));
	log.debug("filename : "+filename);


	InputStream ExcelFileToRead = new FileInputStream(sc.get("excel.file.path")+"/"+filename);
	XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);


	XSSFWorkbook test = new XSSFWorkbook(); 

	XSSFSheet sheet = wb.getSheetAt(0);
	XSSFRow row; 
	XSSFCell cell;

	Iterator rows = sheet.rowIterator();
	int count = 0;
	int cellCount = 0;
	while (rows.hasNext()){
		count++;
		row=(XSSFRow) rows.next();
		Iterator cells = row.cellIterator();
		
		//첫번째 row는 건너 뛴다.
		if(count==1){
			continue;
		}

		while (cells.hasNext()){
			cellCount++;
			cell=(XSSFCell) cells.next();
			//첫번째 cell 은 건너 뛴다.

			if(cellCount==1){
				continue;
			}
			
			if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
				//log.debug(cell.getStringCellValue());
				items.add(cell.getStringCellValue());
				result = "success";
			}
		}
	}
	
	json.put("result", result);
	json.put("activity", items);

	out.println(json);

}
catch(Exception e){
	log.debug(e.toString());
	log.error(e.toString(), e);
}
%><%@ include file="/include/footer.jsp" %>
