/*=========================================================================
 * @BusinessType : Common
 * @File : PoiUtil.java
 * @FileName : 엑셀 처리를 위한 유틸 함수
 *
 * Note :
 *
 * Change History
 * @LastModifyDate : 20110418
 * @LastModifier   : 최형범
 * @LastVersion    : 1.0
 *
 *  2011-04-18 최초생성
 *
 ========================================================================*/
package com.cesa.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;


public class PoiUtil {
	Logger log = Logger.getLogger(PoiUtil.class);	
	public PoiUtil(){}
	
	/**
	* String 데이타를 엑셀 파일로 변환 다운로드
	* @Param String
	* @return void
	*/
	public void writeToExcel(){
		FileOutputStream fileOut;
		String filePath = "test.xls";
		String args[][] = 	new String[][] {{"123","123","4234"},{"123","123","123"},};
		String sheetName="testView";
		log.debug("test poi");
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName);

			int rowsTotal = args.length;
			
			for(int rowInx = 0 ; rowInx < rowsTotal ; rowInx++){
			
				HSSFRow row = sheet.getRow(rowInx);
				if(row == null){
					row = sheet.createRow(rowInx);
				}
				int colsTotal = args[rowInx].length;

				for(int colInx = 0 ; colInx < colsTotal ; colInx++){
					HSSFCell cell = row.getCell(colInx);
					if(cell == null){
						cell = row.createCell(colInx);
					}

					cell.setCellValue(args[rowInx][colInx]);
				}
			}
			
			fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
		
		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				log.debug("e : " + e);
			}
		}

	}
}
