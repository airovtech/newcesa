/*============================================================================
 * @BusinessType : Utility
 * @File : DateUtil.java
 * @FileName : URL 의 내용을 가져오는 클래스 
 *
 * Note:
 *
 * Change history
 * @LastModifyDate : 20111213
 * @LastModifier   : 
 * @LastVersion    : 1.0
 *   2005-01-03 최초생성
 ============================================================================*/
package com.cesa.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;


/**
 * DateUtil class
 * 날짜 관련 유틸리티
 * 
 * @version	1.0
 * @author Administrator
 */
public class DateUtil {
	
	final public static String SEP="-";
	final public static int FORMAT_UNKNOWN=0;
	final public static int FORMAT_YYYYMMDD=123;
	final public static int FORMAT_MMDDYYYY=231;
	final public static int FORMAT_DDMMYYYY=321;

	final static int nYY[][]={
		{1    , 2   , 1   , 2   , 1   , 2   , 2   , 3   , 2   , 2   , 1   , 2   , 1  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 3   , 2   , 1   , 2   , 2   , 1   , 2   , 2  },
		{ 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 3   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 3   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 3   , 2   , 2   , 1   , 2   , 2   , 2  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 3   , 1   , 2   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 3   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 3   , 2   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 3   , 2   , 1   , 1   , 2   , 2   , 1   , 2  },
		{ 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 2   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 4   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 3   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 3   , 1   , 2   , 2   , 1   , 2   , 2  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 2   , 3   , 1   , 2   , 1   , 2   , 1   , 1   , 2  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 3   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 3   , 2   , 2   , 1   , 2   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 2   , 1   , 3   , 2   , 1   , 1   , 2   , 1   , 2   , 2  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 0  },
		{ 2   , 1   , 2   , 2   , 3   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 2   , 3   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 2   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 1   , 2   , 3   , 1   , 2   , 1   , 2   , 2   , 1  },
		{ 2   , 2   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 4   , 1   , 2   , 1   , 2   , 1   , 1   , 2  },
		{ 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 4   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1  },
		{ 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 1   , 2   , 1   , 1   , 4   , 1   , 2   , 2   , 1   , 2  },
		{ 2   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 2   , 2   , 1   , 4   , 1   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 4   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 3   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 2   , 1   , 3   , 2   , 1   , 2   , 1   , 2  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 4   , 2   , 1   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 1   , 4   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2  },
		{ 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 3   , 2   , 1   , 2   , 2  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 2   , 3   , 2   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 3   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 2   , 2   , 3   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1  },
		{ 2   , 2   , 1   , 2   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 2   , 3   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 3   , 2   , 1   , 2   , 2   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 2   , 3   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2  },
		{ 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 3   , 2   , 1   , 1   , 2  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 4   , 1   , 2   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 3   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 2  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 3   , 2   , 2  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 0  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 3   , 2   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 3   , 1   , 2   , 1   , 2   , 2   , 2   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 2   , 2   , 3   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 3   , 2   , 1   , 1   , 2  },
		{ 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 3   , 2   , 2   , 1   , 2   , 2   , 2   , 1  },
		{ 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 2   , 2   , 1   , 3   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2  },
		{ 2   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 3   , 2   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 1   , 2  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 2   , 1   , 2   , 3   , 2   , 2   , 1   , 2   , 2  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 1   , 2   , 3   , 1   , 2   , 1   , 2   , 1   , 2   , 2  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 4   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 3   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 3   , 2   , 1   , 2   , 1   , 2   , 2   , 2  },
		{ 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 1   , 2   , 2   , 3   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 3   , 2   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 3   , 2   , 1   , 2   , 2   , 2   , 1  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 2   , 0  },
		{ 1   , 2   , 2   , 1   , 2   , 3   , 1   , 2   , 1   , 1   , 2   , 2   , 1  },
		{ 2   , 2   , 1   , 2   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 0  },
		{ 1   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 3   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 3   , 2   , 2   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 0  },
		{ 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 1   , 4   , 1   , 1   , 2   , 1   , 2   , 2  },
		{ 2   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 1   , 2   , 0  },
		{ 2   , 2   , 1   , 2   , 1   , 2   , 1   , 2   , 1   , 1   , 2   , 1   , 0  },
		{ 2   , 2   , 1   , 2   , 2   , 3   , 2   , 1   , 2   , 1   , 2   , 1   , 1  },
		{ 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 1   , 2   , 1   , 0  },
		{ 2   , 1   , 1   , 2   , 1   , 2   , 2   , 1   , 2   , 2   , 1   , 2   , 0  },
		{ 1   , 2   , 3   , 1   , 2   , 1   , 2   , 1   , 2   , 2   , 2   , 1   , 2  },
		{ 1   , 2   , 1   , 1   , 2   , 1   , 1   , 2   , 2   , 1   , 2   , 2   , 0 }
	};


	final static int aiMoonDay[]={
	/*1881*/1, 2, 1, 2, 1, 2, 2, 3, 2, 2, 1, 2, 1,
		1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0,
		1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0,
		2, 1, 1, 2, 1, 3, 2, 1, 2, 2, 1, 2, 2,
		2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0,
		2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		2, 2, 1, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2,
		2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0,
		2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2,

	/*1891*/1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0,
		1, 1, 2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2,
		1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0,
		1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0,
		2, 1, 2, 1, 2, 3, 1, 2, 1, 2, 1, 2, 1,
		2, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 2, 3, 2, 2, 1, 2, 1, 2, 1, 2, 1,
		2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 0,
		1, 2, 1, 1, 2, 1, 2, 2, 3, 2, 2, 1, 2,

	/*1901*/1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0,
		2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0,
		1, 2, 1, 2, 1, 3, 2, 1, 1, 2, 2, 1, 2,
		2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 0,
		2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 4, 1, 2, 1, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0,
		1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2,
		1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0,

	/*1911*/2, 1, 2, 1, 1, 2, 3, 1, 2, 2, 1, 2, 2,
		2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0,
		2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0,
		2, 2, 1, 2, 2, 3, 1, 2, 1, 2, 1, 1, 2,
		2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1,
		2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 0,
		1, 2, 1, 1, 2, 1, 2, 3, 2, 2, 1, 2, 2,
		1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0,

	/*1921*/2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0,
		2, 1, 2, 2, 1, 3, 2, 1, 1, 2, 1, 2, 2,
		1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 0,
		2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 0,
		2, 1, 2, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2,
		1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0,
		2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0,
		1, 2, 3, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2,
		1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 0,
		1, 2, 2, 1, 1, 2, 3, 1, 2, 1, 2, 2, 1,

	/*1931*/2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 0,
		2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 2, 4, 1, 2, 1, 2, 1, 1, 2,
		1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 0,
		1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0,
		2, 1, 1, 4, 1, 2, 1, 2, 1, 2, 2, 2, 1,
		2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 0,
		2, 2, 1, 1, 2, 1, 1, 4, 1, 2, 2, 1, 2,
		2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0,

	/*1941*/2, 2, 1, 2, 2, 1, 4, 1, 1, 2, 1, 2, 1,
		2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 0,
		1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0,
		1, 1, 2, 1, 4, 1, 2, 1, 2, 2, 1, 2, 2,
		1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0,
		2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 0,
		2, 2, 3, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2,
		2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		2, 2, 1, 2, 1, 2, 1, 3, 2, 1, 2, 1, 2,
		2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0,

	/*1951*/2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 1, 2, 1, 4, 2, 1, 2, 1, 2, 1, 2,
		1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 0,
		1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 0,
		2, 1, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2,
		1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0,
		2, 1, 2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2,
		1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 2, 1, 2, 2, 3, 2, 1, 2, 1, 2, 1,

	/*1961*/2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 0,
		1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0,
		2, 1, 2, 1, 3, 2, 1, 2, 1, 2, 2, 2, 1,
		2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0,
		1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 0,
		2, 2, 2, 3, 2, 1, 1, 2, 1, 1, 2, 2, 1,
		2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 2, 1, 2, 3, 2, 1, 2, 1, 2,
		1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0,

	/*1971*/1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2, 1, 2,
		1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0,
		2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1, 0,
		2, 2, 1, 2, 3, 1, 2, 1, 1, 2, 2, 1, 2,
		2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0,
		2, 2, 1, 2, 1, 2, 1, 2, 3, 2, 1, 1, 2,
		2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 0,
		2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 1, 2, 1, 2, 4, 1, 2, 2, 1, 2, 1,
		2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0,

	/*1981*/1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0,
		2, 1, 2, 1, 3, 2, 1, 1, 2, 2, 1, 2, 2,
		2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0,
		2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 3, 2, 2,
		1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 0,
		2, 1, 2, 2, 1, 2, 3, 2, 2, 1, 2, 1, 2,
		1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0,
		2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 0,
		1, 2, 1, 1, 2, 3, 1, 2, 1, 2, 2, 2, 2,

	/*1991*/1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 0,
		1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 0,
		1, 2, 2, 3, 2, 1, 2, 1, 1, 2, 1, 2, 1,
		2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 0,
		1, 2, 2, 1, 2, 2, 1, 2, 3, 2, 1, 1, 2,
		1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 0,
		1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0,
		2, 1, 1, 2, 1, 3, 2, 2, 1, 2, 2, 2, 1,
		2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 0,
		2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 0,

	/*2001*/2, 2, 2, 1, 3, 2, 1, 1, 2, 1, 2, 1, 2,
		2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0,
		2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 0,
		1, 2, 3, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2,
		1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0,
		1, 1, 2, 1, 2, 1, 2, 3, 2, 2, 1, 2, 2,
		1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 0,
		2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 0,
		2, 2, 1, 1, 2, 3, 1, 2, 1, 2, 1, 2, 2,
		2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,

	/*2011*/2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 0,
		2, 1, 2, 4, 2, 1, 2, 1, 1, 2, 1, 2, 1,
		2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 1, 2, 1, 2, 1, 2, 2, 3, 2, 1, 2,
		1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 2, 0,
		1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 0,
		2, 1, 1, 2, 1, 3, 2, 1, 2, 1, 2, 2, 2,
		1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 0,
		2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 0,
		2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2,

	/*2021*/1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 0,
		2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 0,
		1, 2, 3, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2,
		1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 0,
		2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2, 1,
		2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 0,
		1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 0,
		1, 2, 2, 1, 2, 3, 1, 2, 1, 1, 2, 2, 1,
		2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 0,
		1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 0,

	/*2031*/2, 1, 2, 3, 2, 1, 2, 2, 1, 2, 1, 2, 1,
		2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0,
		1, 2, 1, 1, 2, 1, 2, 3, 2, 2, 2, 1, 2,
		1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 0,
		2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 0,
		2, 2, 1, 2, 1, 1, 4, 1, 1, 2, 1, 2, 2,
		2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 0,
		2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 0,
		2, 2, 1, 2, 2, 3, 2, 1, 2, 1, 2, 1, 1,
		2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 0,

	/*2041*/2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 0,
		1, 2, 3, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2,
		1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 0
	};

	/**
	 * 날짜를 Long타입으로 리턴한다. 
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return long 
	 */
	public static long getLong(int year,int month,int date) {
		Date d=getDate(year,month,date);
		return d.getTime();
	}
	
	/**
	 * 날짜를 Date타입으로 리턴한다. 
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return Date 
	 */
	public static Date getDate(int year,int month,int date) {
		Calendar cal=getCalendar(year,month,date);
		return cal.getTime();
	}
	
	/**
	 * 날짜를 Calendar 타입으로 리턴한다. 
	 *
	 * @param int year
	 * @param int month
	 * @param int date
	 * @return Calendar 
	 */
	public static Calendar getCalendar(int year,int month,int date) {
		Calendar cal=Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH,month);
		cal.set(Calendar.DATE,date);
		return cal;
	}
	
	/**
	 * 날짜를 Long 타입으로 리턴한다. 
	 *
	 * @param String dateStr
	 * @param int format
	 * @return String 
	 */
	public static long getLong(String dateStr,int format) {
		Date d=getDate(dateStr,format);
		return d.getTime();
	}
	
	/**
	 * 날짜를 Date 타입으로 리턴한다. 
	 *
	 * @param String dateStr
	 * @param int format
	 * @return long 
	 */
	public static Date getDate(String dateStr,int format) {
		Calendar cal=getCalendar(dateStr,format);
		return cal.getTime();
	}

	/**
	 * 날짜를 Calendar 타입으로 리턴한다.
	 *
	 * @param String dateStr
	 * @param int format
	 * @return Calendar
	 */
	public static Calendar getCalendar(String dateStr, int format) {
		Calendar cal=null;
		switch(format) {
			case FORMAT_YYYYMMDD :
				cal=getCalendarYYYYMMDD(dateStr);
				break;
			case FORMAT_MMDDYYYY :
				cal=getCalendarMMDDYYYY(dateStr);
				break;
			case FORMAT_DDMMYYYY :
				cal=getCalendarDDMMYYYY(dateStr);
				break;
		}
		return cal;
	}
	
	/**
	 * 날짜를 Calendar 타입 YYYYMMDD 으로 리턴한다.
	 *
	 * @param String YYYYMMDD
	 * @return Calendar
	 */
	public static Calendar getCalendarYYYYMMDD(String YYYYMMDD) throws NumberFormatException {
		Calendar cal=Calendar.getInstance();
		int index=0;
		int l=SEP.length();
		String yyyyStr=YYYYMMDD.substring(index,index+4);
		index+=(4+l);
		String mmStr=YYYYMMDD.substring(index,index+2);
		index+=(2+l);
		String ddStr=YYYYMMDD.substring(index,index+2);
		int yyyy=Integer.parseInt(yyyyStr);
		int mm=Integer.parseInt(mmStr);
		int dd=Integer.parseInt(ddStr);
		cal.clear();
		cal.set(yyyy,mm-1,dd);
		return cal;
	}
	
	/**
	 * 날짜를 Calendar 타입 MMDDYYYY 으로 리턴한다.
	 *
	 * @param String MMDDYYYY
	 * @return Calendar
	 */
	public static Calendar getCalendarMMDDYYYY(String MMDDYYYY) throws NumberFormatException {
		Calendar cal=Calendar.getInstance();
		int index=0;
		int l=SEP.length();
		String mmStr=MMDDYYYY.substring(index,index+2);
		index+=(2+l);
		String ddStr=MMDDYYYY.substring(index,index+2);
		index+=(2+l);
		String yyyyStr=MMDDYYYY.substring(index,index+4);
		int yyyy=Integer.parseInt(yyyyStr);
		int mm=Integer.parseInt(mmStr);
		int dd=Integer.parseInt(ddStr);
		cal.clear();
		cal.set(yyyy,mm-1,dd);
		return cal;
	}
	
	/**
	 * 날짜를 Calendar 타입 DDMMYYYY 으로 리턴한다.
	 *
	 * @param String DDMMYYYY
	 * @return Calendar
	 */
	public static Calendar getCalendarDDMMYYYY(String DDMMYYYY) throws NumberFormatException {
		Calendar cal=Calendar.getInstance();
		int index=0;
		int l=SEP.length();
		String ddStr=DDMMYYYY.substring(index,index+2);
		index+=(2+l);
		String mmStr=DDMMYYYY.substring(index,index+2);
		index+=(2+l);
		String yyyyStr=DDMMYYYY.substring(index,index+4);
		int yyyy=Integer.parseInt(yyyyStr);
		int mm=Integer.parseInt(mmStr);
		int dd=Integer.parseInt(ddStr);
		cal.clear();
		cal.set(yyyy,mm-1,dd);
		return cal;
	}

	/**
	 * 시간에 대한 셋팅
	 *
	 * @param String type
	 * @return String
	 */
	public static String getTime(String type) {
		GregorianCalendar calendar = new GregorianCalendar();
		if(type.equals("HHMMSSSSS")) {
		String hour = Long.toString(calendar.get(11));
		String minute = Long.toString(calendar.get(12));
		String second = Long.toString(calendar.get(13));
		String milli = Long.toString(calendar.get(Calendar.MILLISECOND));
		if(hour.length() == 1)
			hour = "0" + hour;
		if(minute.length() == 1)
			minute = "0" + minute;
		if(second.length() == 1)
			second = "0" + second;
		if(milli.length() == 1)
			milli = "00"+milli;
		else if(milli.length() == 2)
			milli = "0"+milli;

		return hour + minute + second + milli;
	}
		if(type.equals("HHMMSS")) {
			String hour = Long.toString(calendar.get(11));
			String minute = Long.toString(calendar.get(12));
			String second = Long.toString(calendar.get(13));
			if(hour.length() == 1)
				hour = "0" + hour;
			if(minute.length() == 1)
				minute = "0" + minute;
			if(second.length() == 1)
				second = "0" + second;
			return hour + minute + second;
		}

		if(type.equals("HH:MM:SS")) {
			String hour = Long.toString(calendar.get(11));
			String minute = Long.toString(calendar.get(12));
			String second = Long.toString(calendar.get(13));
			if(hour.length() == 1)
				hour = "0" + hour;
			if(minute.length() == 1)
				minute = "0" + minute;
			if(second.length() == 1)
				second = "0" + second;
			return hour + ":" + minute + ":" + second;
		}

		if(type.equals("HHMM")) {
			String hour = Long.toString(calendar.get(11));
			String minute = Long.toString(calendar.get(12));
			if(hour.length() == 1)
				hour = "0" + hour;
			if(minute.length() == 1)
				minute = "0" + minute;
			return hour + minute;
		}

		if(type.equals("HH:MM")) {
			String hour = Long.toString(calendar.get(11));
			String minute = Long.toString(calendar.get(12));
			if(hour.length() == 1)
				hour = "0" + hour;
			if(minute.length() == 1)
				minute = "0" + minute;
			return hour + ":" + minute;
		}

		if(type.equals("HH")) {
			String hour = Long.toString(calendar.get(11));
			if(hour.length() == 1)
				hour = "0" + hour;
			return hour;
		}

		if(type.equals("MM")) {
			String minute = Long.toString(calendar.get(12));
			if(minute.length() == 1)
				minute = "0" + minute;
			return minute;
		}

		if(type.equals("AMPM")) {
			int ampm = calendar.get(11);
			if(ampm < 12)
				return "AM";
			else
				return "PM";
		} else {
			return "";
		}
	}

	/**
	 * 년,월,일에 대한 설정
	 *
	 * @param String type
	 * @return String
	 */
	public static String getToday(String type) {
		GregorianCalendar calendar = new GregorianCalendar();

		if(type.equals("YYYYMMDDHHMMSSSSS")){
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			String time = getTime("HHMMSSSSS");
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;

			return year + month + day + time;
		}

		if(type.equals("YYYYMMDDHHMMSS")){
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			String time = getTime("HHMMSS");
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;

			return year + month + day + time;
		}

		if(type.equals("YYYYMMDDHHMM")){
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			String time = getTime("HHMM");
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;

			return year + month + day + time;
		}

		if(type.equals("YYYYMMDD")){
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;
			return year + month + day;
		}

		if(type.equals("YYYYMM")){
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			if(month.length() == 1)
				month = "0" + month;

			return year + month;
		}

		if(type.equals("YYYY/MM/DD")) {
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;
			return year + "/" + month + "/" + day;
		}

		if(type.equals("YYYY-MM-DD")) {
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;
			return year + "-" + month + "-" + day;
		}

		if(type.equals("YYYY.MM.DD")) {
			String year = Long.toString(calendar.get(1));
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;
			return year + "." + month + "." + day;
		}


		if(type.equals("YYMMDD")) {
			String year = Long.toString(calendar.get(1));
			year = year.substring(2, 4);
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;
			return year + month + day;
		}

		if(type.equals("YY/MM/DD")) {
			String year = Long.toString(calendar.get(1));
			year = year.substring(2, 4);
			String month = Long.toString(calendar.get(2) + 1);
			String day = Long.toString(calendar.get(5));
			if(month.length() == 1)
				month = "0" + month;
			if(day.length() == 1)
				day = "0" + day;
			return year + "/" + month + "/" + day;
		}

		if(type.equals("YYMM")) {
			String year = Long.toString(calendar.get(1));
			year = year.substring(2, 4);
			String month = Long.toString(calendar.get(2) + 1);
			if(month.length() == 1)
				month = "0" + month;
			return year + month;
		}
		
		if(type.equals("YYYY"))
			return Long.toString(calendar.get(1));

		if(type.equals("MM")) {
			String month = Long.toString(calendar.get(2) + 1);
			if(month.length() == 1)
				month = "0" + month;
			return month;
		}

		if(type.equals("DD")) {
			String day = Long.toString(calendar.get(5));
			if(day.length() == 1)
				day = "0" + day;
			return day;
		}

		if(type.equals("HHMMSS"))
			return getTime(type);
		if(type.equals("HH:MM:SS"))
			return getTime(type);
		if(type.equals("HHMM"))
			return getTime(type);
		if(type.equals("HH:MM"))
			return getTime(type);
		if(type.equals("HH"))
			return getTime(type);
		if(type.equals("MM"))
			return getTime(type);
		if(type.equals("AMPM"))
			return getTime(type);

		// 요일에 대한 설정
		if(type.equals("WEEK"))
			return Integer.toString(calendar.get(7));

		if(type.equals("WEEK_KR")) {
			int week = calendar.get(7);
			switch(week) {
				case 1: // '\001'
					return "\uC77C";

				case 2: // '\002'
					return "\uC6D4";

				case 3: // '\003'
					return "\uD654";

				case 4: // '\004'
					return "\uC218";

				case 5: // '\005'
					return "\uBAA9";

				case 6: // '\006'
					return "\uAE08";

				case 7: // '\007'
					return "\uD1A0";
			}
			return "";
		}

		if(type.equals("WEEK_EN")) {
			int week = calendar.get(7);
			switch(week) {
				case 1: // '\001'
					return "SUN";

				case 2: // '\002'
					return "MON";

				case 3: // '\003'
					return "TUE";

				case 4: // '\004'
					return "WED";

				case 5: // '\005'
					return "THU";

				case 6: // '\006'
					return "FRI";

				case 7: // '\007'
					return "SAT";
			}
			return "";
		} else {
			return "";
		}
	}

	 /**
	 * 오늘부터 year, month, day까지의 날짜 수
	 *
	 * @param int year
	 * @param int month
	 * @param int day
	 * @return int
	 */
	public static int days_between(int year, int month, int day){
		try{
			Calendar temp=Calendar.getInstance();
			int M1=month-1;                                //Calendar함수에 쓸 수 있는 값으로 변환
			int Y2=temp.get(Calendar.YEAR);      //올해
			//년도 차이에 365를 곱하고 윤년날수를 보정해서 입력한 두 해 사이의
			//일수 차이를 구한다.(년도만 다르고 월, 일은 같다고 가정)
			int sum_of_years= (Y2-year)*365+number_of_addyear(Y2)-number_of_addyear(year);
			int sum_of_day2=temp.get(Calendar.DAY_OF_YEAR);   //올해 오늘까지의 일수
			temp.set(year,M1,day);
			int sum_of_day1=temp.get(Calendar.DAY_OF_YEAR);  //입력년도 입력일까지의 일수
			int sum_of_days=sum_of_day2-sum_of_day1;
			int sum=sum_of_years+sum_of_days;    //일수간의 차이를 보정하여 정확한 일수의 차이를 구함

			return sum;      //int값으로 반환
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	 /**
	 * 두 날짜사이의 일수차이 구함
	 *
	 * @param int year1
	 * @param int month1
	 * @param int day1
	 * @param int year2
	 * @param int month2
	 * @param int day2
	 * @return int
	 */
	public static int days_between(int year1, int month1, int day1, int year2, int month2, int day2){
		
		try{
			Calendar temp=Calendar.getInstance();
			int M1=month1-1;
			int M2=month2-1;	         //Calendar 함수에 쓸 수 있는 값으로 변환

			int sum_of_years= (year2-year1)*365+number_of_addyear(year2)-number_of_addyear(year1);
			//년도 차이에 365를 곱하고 윤년날수를 보정해서 입력한 두 해 사이의
			// 일수 차이를 구한다.(년도만 다르고 월, 일은 같다고 가정)
			temp.set(year2,M2,day2);
			int sum_of_day2=temp.get(Calendar.DAY_OF_YEAR);   //입력해 입력일까지의 날 수
			temp.set(year1,M1,day1);
			int sum_of_day1=temp.get(Calendar.DAY_OF_YEAR);  //입력해 입력일까지의 날 수
			int sum_of_days=sum_of_day2-sum_of_day1;
			int sum=sum_of_years+sum_of_days;     //일수간의 차이를 보정하여 정확한  일수의 차이를 구함

			return sum;    //int값으로 반환
		}
		catch(Exception e){
			return 0;
		}
	}

	/**
	 * 오늘부터 year, month, day까지의 날짜 수
	 *
	 * @param String strYYYYMMDD
	 * @return int
	 */
	public static int days_between(String strYYYYMMDD){
		try{
			int year = Integer.parseInt(strYYYYMMDD.substring(0,4));
			int month = Integer.parseInt(strYYYYMMDD.substring(4,6));
			int day =  Integer.parseInt(strYYYYMMDD.substring(6));

			return days_between(year,month,day);
		}
		catch(Exception e){
			return -1;
		}
	}

	/**
	 * 두 날짜사이의 일수차이 구함
	 *
	 * @param String strYYYYMMDD1
	 * @param String strYYYYMMDD2
	 * @return int
	 */
	public static int days_between(String strYYYYMMDD1,String strYYYYMMDD2){
		try{
			int year = Integer.parseInt(strYYYYMMDD1.substring(0,4));
			int month = Integer.parseInt(strYYYYMMDD1.substring(4,6));
			int day =  Integer.parseInt(strYYYYMMDD1.substring(6));

			int year2 = Integer.parseInt(strYYYYMMDD2.substring(0,4));
			int month2 = Integer.parseInt(strYYYYMMDD2.substring(4,6));
			int day2 =  Integer.parseInt(strYYYYMMDD2.substring(6));

			return days_between(year,month,day,year2,month2,day2);
		}
		catch(Exception e){
			return -1;
		}
	}

	/**
	 * 올해의 2월달 날 수를 계산 
	 * 29 혹은 28을 리턴
	 * @return int
	 */
	public static int february_days() {
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.MONTH,2);
		temp.set(Calendar.DATE,1);
		int aa=temp.get(java.util.Calendar.DAY_OF_YEAR);
		if(aa==61)
			return 29;
		else if(aa==60)
			return 28;
		else
			return 0;
	}

	/**
	 * 올해의 2월달 날 수를 계산 
	 * 29 혹은 28을 리턴
	 * @param int year
	 * @return int
	 */
	public static int february_days(int year){
		Calendar temp = Calendar.getInstance();
		try{
			temp.set(year,2,1);
		}
		catch(Exception e){
			return 0;
		}

		int aa=temp.get(java.util.Calendar.DAY_OF_YEAR);
		if(aa==61)
			return 29;
		else if(aa==60)
			return 28;
		else
			return 0;
	}

	/**
	 * 오늘날짜 리턴
	 * @return java.lang.String
	 */
	public static String getDate(){
		
		Calendar temp=Calendar.getInstance();

		int year=temp.get(Calendar.YEAR);
		int month=temp.get(Calendar.MONTH)+1;
		int day=temp.get(Calendar.DAY_OF_MONTH);

		StringBuffer today=new StringBuffer();

		today.append(year);

		if(month<10)
			today.append("0");

		today.append(month);


		if(day<10)
			today.append("0");

		today.append(day);

		return today.toString();
	}

	/**
	 * 오늘부터 iDay 만큼의 날짜를 빼거나 더한 날짜 리턴
	 * @param int iDay
	 * @return java.lang.String
	 */
	public static String getDate(int iDay){
		
		Calendar temp=Calendar.getInstance();
		StringBuffer sbDate=new StringBuffer();

		temp.add(Calendar.DAY_OF_MONTH,iDay);

		int nYear		=	temp.get(java.util.Calendar.YEAR);
		int nMonth		= 	temp.get(java.util.Calendar.MONTH) + 1;
		int nDay		=	temp.get(java.util.Calendar.DAY_OF_MONTH);

		sbDate.append(nYear)	;
		if (nMonth < 10 ) sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10 ) sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();
	}
	
	/**
	 * 넘겨받은 일자에 일수를 더하거나 뺀 날짜를 반환.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param iDay
	 * @return
	 */
	public static String getDateAdd(int year, int month, int day, int iDay){
		
		Calendar temp=Calendar.getInstance();
		StringBuffer sbDate=new StringBuffer();
		
		temp.set(year, month-1, day);
		temp.add(Calendar.DAY_OF_MONTH, iDay);
		
		int nYear		=	temp.get(java.util.Calendar.YEAR);
		int nMonth		= 	temp.get(java.util.Calendar.MONTH) + 1;
		int nDay		=	temp.get(java.util.Calendar.DAY_OF_MONTH);

		sbDate.append(nYear)	;
		if (nMonth < 10 ) sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10 ) sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();
	}
	
	/**
	 * Calendar의 밀리세컨드를 반환한다.
	 * @return int
	 */
	public static int getMillis() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MILLISECOND);
	}

	/**
	 * 오늘날짜 구하기
	 * iDay 맡큼 빼거나 더한
	 * 년 , 월 , 주 ,일 을 반환
	 * @param int iDay
	 * @param char flag
	 * @return java.lang.String
	 */
	public static String getDate(int iDay,char flag){
		
		Calendar today=Calendar.getInstance();

		switch(flag){
			case	'Y'	:	today.add(Calendar.YEAR,iDay);
							break;
			case	'M'	:	today.add(Calendar.MONTH,iDay);
							break;
			case	'W'	:	today.add(Calendar.WEEK_OF_MONTH,iDay);
							break;
			case	'D'	:	today.add(Calendar.DAY_OF_MONTH,iDay);
							break;
		}

		String strYear		=	Integer.toString(today.get(Calendar.YEAR));
		String strMonth		=	Integer.toString(today.get(Calendar.MONTH)+1);
		strMonth			=	(strMonth.length()==1) ? strMonth="0"+strMonth : strMonth;
		String strDay		=	Integer.toString(today.get(Calendar.DAY_OF_MONTH));
		strDay				=	(strDay.length()==1) ? strDay="0"+strDay : strDay;

		return strYear+strMonth+strDay;
	}

	public static String getYearMonth(int iDay,char flag){
		
		Calendar today=Calendar.getInstance();

		switch(flag){
			case	'Y'	:	today.add(Calendar.YEAR,iDay);
							break;
			case	'M'	:	today.add(Calendar.MONTH,iDay);
							break;
		}

		String strYear		=	Integer.toString(today.get(Calendar.YEAR));

		String strMonth		=	Integer.toString(today.get(Calendar.MONTH)+1);
		strMonth			=	(strMonth.length()==1) ? strMonth="0"+strMonth : strMonth;

		return strYear+strMonth;
	}
	/**
	 * 두날짜 차이 구하기
	 * @param String strDate
	 * @param int iDay
	 * @return java.lang.String
	 */
	public static String getDiffDate(String strDate , int iDay) {
		if (strDate.length() != 8)
			return null;
		iDay -= days_between(strDate);
		return getDate(iDay);
	}

	/**
	 * 두개의 날짜를 입력 받아서 그 차이를 날짜로 리턴한다 (yyyy-MM-dd)로 받았을때        
	 *
	 * @param st_date 시작일자
	 * @param ed_date 종료일자
	 * @return 날짜차이
	 */
	public static long getDiffDay(String st_date , String ed_date) {

		int st_year, st_month, st_day;
		int ed_year, ed_month, ed_day;

		st_year = Integer.parseInt(st_date.substring(0,4));
		st_month = Integer.parseInt(st_date.substring(5,7));
		st_day = Integer.parseInt(st_date.substring(8,10));

		ed_year = Integer.parseInt(ed_date.substring(0,4));
		ed_month = Integer.parseInt(ed_date.substring(5,7));
		ed_day = Integer.parseInt(ed_date.substring(8,10));

		return Math.abs(getDiffDay(st_year,st_month,st_day,ed_year,ed_month,ed_day));
	}

	/**
	 * 두개의 날짜를 입력 받아서 그 차이를 날짜로 리턴한다 (YYYYMMDD)로 받았을때        
	 *
	 * @param st_date 시작일자
	 * @param ed_date 종료일자
	 * @param yn true면 음수 사용.
	 * @return 날짜차이
	 */
	public static long getDiffDay(String st_date , String ed_date, boolean yn) {

		int st_year, st_month, st_day;
		int ed_year, ed_month, ed_day;

		st_year = Integer.parseInt(st_date.substring(0,4));
		st_month = Integer.parseInt(st_date.substring(4,6));
		st_day = Integer.parseInt(st_date.substring(6,8));

		ed_year = Integer.parseInt(ed_date.substring(0,4));
		ed_month = Integer.parseInt(ed_date.substring(4,6));
		ed_day = Integer.parseInt(ed_date.substring(6,8));

		if (yn) {
			return getDiffDay(st_year,st_month,st_day,ed_year,ed_month,ed_day);
		} else {
			return Math.abs(getDiffDay(st_year,st_month,st_day,ed_year,ed_month,ed_day));
		}
	}

	/**
	 * 년월일  두 개를 입력 받아서 그 차이를 날짜로 리턴한다         
	 *
	 * @param year1 년도1
	 * @param month1 월1
	 * @param day1 일1
	 * @param year2 년도2
	 * @param month2 월2 
	 * @param day2 일2
	 * @return 날짜차이 
	 *
	 */
	public static long getDiffDay(int year1, int month1, int day1, int year2, int month2, int day2) {
		long diff, diffday;
		long nowmilli, setmilli;

		GregorianCalendar now = new GregorianCalendar(year1, month1-1, day1, 0, 0);
		GregorianCalendar set = new GregorianCalendar(year2, month2-1, day2, 0, 0);

		nowmilli = now.getTime().getTime();
		setmilli = set.getTime().getTime();

		diff = setmilli - nowmilli;

		diffday = diff / (1000*60*60*24);

		return diffday;
	}


	/**
	 * 특정날짜에서 diff만큼 빼거나 더하기
	 * 년 , 월 , 일 을 반환
	 * @param String theDate
	 * @param int diff
	 * @return java.lang.String
	 */
	public static String getSubtractTheDate(String theDate, int diff){
		Calendar temp=Calendar.getInstance();
		StringBuffer sbDate=new StringBuffer();

		temp.set(java.util.Calendar.YEAR, Integer.parseInt(theDate.substring(0,4)));
		temp.set(java.util.Calendar.MONTH, Integer.parseInt(theDate.substring(4,6))-1);
		temp.set(java.util.Calendar.DATE, Integer.parseInt(theDate.substring(6)));

		temp.add(Calendar.DAY_OF_MONTH,diff);

		int nYear	=	temp.get(java.util.Calendar.YEAR);
		int nMonth	= 	temp.get(java.util.Calendar.MONTH)+1;
		int nDay	=	temp.get(java.util.Calendar.DATE);

		sbDate.append(nYear);
		if (nMonth < 10 ) sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10 ) sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();
	}
	
	/**
	 * 오늘날짜에서 diff만큼 빼거나 더하기
	 * 년 , 월 , 일 을 반환
	 * @param int diff
	 * @return java.lang.String
	 */
	public static String getSubtractTheDate(int diff){
		String theDate = getToday("YYYYMMDD");
		Calendar temp=Calendar.getInstance();
		StringBuffer sbDate=new StringBuffer();

		temp.set(java.util.Calendar.YEAR, Integer.parseInt(theDate.substring(0,4)));
		temp.set(java.util.Calendar.MONTH, Integer.parseInt(theDate.substring(4,6))-1);
		temp.set(java.util.Calendar.DATE, Integer.parseInt(theDate.substring(6)));

		temp.add(Calendar.DAY_OF_MONTH,diff);

		int nYear	=	temp.get(java.util.Calendar.YEAR);
		int nMonth	= 	temp.get(java.util.Calendar.MONTH)+1;
		int nDay	=	temp.get(java.util.Calendar.DATE);

		sbDate.append(nYear);
		if (nMonth < 10 ) sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10 ) sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();
	}

	/**
	 * 특정날짜에서 diff만큼 월을 빼거나 더하기 (YYYYMM형식으로 받았을때)
	 * 년, 월 을 반환
	 * @param int diff
	 * @return java.lang.String
	 */
	public static String getMonth(String dateStr, int diff){
		String theDate = dateStr;
		Calendar temp=Calendar.getInstance();
		StringBuffer sbDate=new StringBuffer();

		temp.set(java.util.Calendar.YEAR, Integer.parseInt(theDate.substring(0,4)));
		temp.set(java.util.Calendar.MONTH, Integer.parseInt(theDate.substring(4,6))-1+(diff));

		int nYear	=	temp.get(java.util.Calendar.YEAR);
		int nMonth	= 	temp.get(java.util.Calendar.MONTH)+1;

		sbDate.append(nYear);
		if (nMonth < 10 ) sbDate.append("0");
		sbDate.append(nMonth);

		return sbDate.toString();

	}

	/**
	 * 년 , 월 , 주 ,일 을 반환
	 * @param String baseDate
	 * @param int offset
	 * @param char flag
	 * @return java.lang.String
	 */
	public static String getDate(String baseDate, int offset,char flag){
		if (baseDate.length() != 8)
			return "";
		int nYYYY = Integer.parseInt(baseDate.substring(0,4));
		int nMM   = Integer.parseInt(baseDate.substring(4,6))-1;
		int nDD    = Integer.parseInt(baseDate.substring(6));

		Calendar today=Calendar.getInstance();
		today.set(nYYYY,nMM,nDD);

		switch(flag){
			case	'Y'	:	today.add(Calendar.YEAR,offset);
							break;
			case	'M'	:	today.add(Calendar.MONTH,offset);
							break;
			case	'W'	:	today.add(Calendar.WEEK_OF_MONTH,offset);
							break;
			case	'D'	:	today.add(Calendar.DAY_OF_MONTH,offset);
							break;
		}

		String strYear		=	Integer.toString(today.get(Calendar.YEAR));

		String strMonth		=	Integer.toString(today.get(Calendar.MONTH)+1);
		strMonth				=	(strMonth.length()==1) ? strMonth="0"+strMonth : strMonth;
		String strDay		=	Integer.toString(today.get(Calendar.DAY_OF_MONTH));
		strDay					=	(strDay.length()==1) ? strDay="0"+strDay : strDay;

		return strYear+strMonth+strDay;
	}
	
	/**
	 * 마지막 일 구하기
	 * @param String strYyMm
	 * @return int
	 */
	public static int getLastDay(String strYyMm){
		int LastDay = 0;
		int year = 0;
		int month = 0;

		year = Integer.parseInt(strYyMm.substring(0, 4));
		if (strYyMm.length() != 6)
			month = Integer.parseInt(strYyMm.substring(4, 5));
		else
			month = Integer.parseInt(strYyMm.substring(4, 6));

		switch (month) {
			case 2: {
				if ( ( (year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
					LastDay = 29;
				else
					LastDay = 28;
				break;
			}
			case 4:
				LastDay = 30;
				break;
			case 6:
				LastDay = 30;
				break;
			case 9:
				LastDay = 30;
				break;
			case 11:
				LastDay = 30;
				break;
			default:
				LastDay = 31;
				break;
		}
		return LastDay;
	}
	
	/**
	 * 오늘 날짜 구하기
	 * @return String
	 */
	public static String getToday() {
		return getToday(false);
	}
	
	/**
	 * 오늘 날짜 구하기
	 * @param boolean flag
	 * @return String
	 */
	public static  String getToday(boolean flag) {
		Calendar temp=Calendar.getInstance();

		int year=temp.get(Calendar.YEAR);
		int month=temp.get(Calendar.MONTH)+1;
		int day=temp.get(Calendar.DAY_OF_MONTH);

		StringBuffer today=new StringBuffer();

		today.append(year);
		today.append("-");

		if(month<10)
			today.append("0");

		today.append(month);

		today.append("-");
		if(day<10)
			today.append("0");

		today.append(day);

		if ( temp.get(Calendar.AM_PM) > 0 )
			today.append(" 오후 ");
		else
			today.append(" 오전 ");

		if (temp.get(Calendar.HOUR) < 10 )
			today.append("0");

		today.append(temp.get(Calendar.HOUR));

		today.append(":");
		if (temp.get(Calendar.MINUTE) < 10 )
			today.append("0");

		today.append(temp.get(Calendar.MINUTE));

		today.append(":");
		if (temp.get(Calendar.SECOND) < 10 )
			today.append("0");

		today.append(temp.get(Calendar.SECOND));

		if(flag) {
			today.append(":");
			today.append(temp.get(Calendar.MILLISECOND));
		}

		return today.toString();
	}
	
	/**
	 * 오늘 날짜 구하기
	 * @param boolean flag
	 * @return String
	 */
	public static  String getTodayEn(boolean flag) {
		Calendar temp=Calendar.getInstance();

		int year=temp.get(Calendar.YEAR);
		int month=temp.get(Calendar.MONTH)+1;
		int day=temp.get(Calendar.DAY_OF_MONTH);

		StringBuffer today=new StringBuffer();

		today.append(year);
		today.append("-");

		if(month<10)
			today.append("0");

		today.append(month);

		today.append("-");
		if(day<10)
			today.append("0");

		today.append(day);

		if ( temp.get(Calendar.AM_PM) > 0 )
			today.append(" PM ");
		else
			today.append(" AM ");

		if (temp.get(Calendar.HOUR) < 10 )
			today.append("0");

		today.append(temp.get(Calendar.HOUR));

		today.append(":");
		if (temp.get(Calendar.MINUTE) < 10 )
			today.append("0");

		today.append(temp.get(Calendar.MINUTE));

		today.append(":");
		if (temp.get(Calendar.SECOND) < 10 )
			today.append("0");

		today.append(temp.get(Calendar.SECOND));

		if(flag) {
			today.append(":");
			today.append(temp.get(Calendar.MILLISECOND));
		}

		return today.toString();
	}
	
	/**
	 * 주차 구하기
	 * @param String strYyyyMmDd
	 * @return int
	 */
	public static int getWeekDay(String strYyyyMmDd){
		Calendar dt=Calendar.getInstance();

		int year = Integer.valueOf(strYyyyMmDd.substring(0,4)).intValue();
		int mon	= 0;
		int day	= 0;
		if	(strYyyyMmDd.length() == 8 ){
			mon = Integer.valueOf(strYyyyMmDd.substring(4,6)).intValue()-1;
			day = Integer.valueOf(strYyyyMmDd.substring(6,8)).intValue();
		}
		else{
			mon = Integer.valueOf(strYyyyMmDd.substring(4,5)).intValue()-1;
			day = Integer.valueOf(strYyyyMmDd.substring(5,7)).intValue();
		}
		dt.set(year,mon,day);

		return dt.get(java.util.Calendar.DAY_OF_WEEK);
		/* 일요일 = 1 ~~~ 토요일=7 */
	}

	// yyyy-mm-dd
	public static String getWeekdayStr(String date)
	{
		int weekday = getWeekDay(date.replaceAll("-", ""));

		weekday = weekday-1;		// 일요일 1

		String str = "일월화수목금토일";

		if(weekday==0)
			return "<font color=red>(일)</font>";
		if(weekday==6)
			return "<font color=blue>(토)</font>";

		return "("+str.substring(weekday, weekday+1)+")";

	}

	/**
	  *올해의 2월달이 윤달인지 판별
	 * 윤달이면 true, 윤달이 아니면 false를 리턴한다.
	 * @return boolean
	 */
	public static boolean isFebruary29(){
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.MONTH,2);
		temp.set(Calendar.DATE,1);
		int aa=temp.get(java.util.Calendar.DAY_OF_YEAR);
		if(aa==61)	return true;
		else return false;
	}

	/**
	 *해당년의 2월달이 윤달인지 판별
	 *윤달이면 true, 윤달이 아니면 false를 리턴한다.
	 * @param int year
	 * @return boolean
	 */
	public static boolean isFebruary29(int year){
		Calendar temp = Calendar.getInstance();
		temp.set(year,2,1);
		int aa=temp.get(java.util.Calendar.DAY_OF_YEAR);
		if(aa==61)	return true;
		else return false;
	}

	/**
	 *해당년 해당월의 말일을 구해서 리턴
	 * @param int year
	 * @param int month
	 * @return int
	 */
	public static int lastdayOfMonth(int year, int month){
		
		if (month<1||month>12){
			return 0;
		}
		else if (month==2){
			int a=february_days(year);
			return a;
		}
		else if (month==4||month==6||month==9||month==11) return 30;
		else return 31;
	}

	/**
	 * 음력을 양력으로 변환한다.
	 * 작성 날짜: [00-02-09 오후 3:40:2
	 * 반환 형식  "YYYYMMDD"
	 * @param String strMoonDate
	 * @param boolean bLeapMonth
	 * @return java.lang.String
	 */
	public static String moon2sun(String strMoonDate,boolean bLeapMonth ){
		
		try{
			String strReturnDate ="";
			int nMM[]  = {31,0,31,30,31,30,31,31,30,31,30,31};
			int nTemp  = 0;
			int nM1 = -1;
			int nM2 = -1;
			int nN2  = 0;
			int nTd  = 0;
			int nRYear =0;
			int nRMonth =0;
			int nRDay =0;


			if (strMoonDate.length() != 8 )
				return null;
			int nYear = Integer.parseInt(strMoonDate.substring(0,4));
			int nMonth = Integer.parseInt(strMoonDate.substring(4,6));
			int nDay = Integer.parseInt(strMoonDate.substring(6));

			if (nYear < 1881 )
				return null;

			if (bLeapMonth){
				if (nYY[nYear-1881][12]<1)
					return null;
				else if(nYY[nYear-1881][nMonth]<3)
					return null;
				else if(nYY[nYear-1881][nMonth]+26 < nDay )
					return null;
			}
			else{
				nTemp = nMonth -1;
				for(int i=0;i<12;i++)
				{
					if (nYY[nYear-1881][i] > 2 )
						nTemp++;
				}
				if (nDay > nYY[nYear-1881][nTemp] + 29)
					return null;
			}

			if (nYear != 1881){
				nM1 = nYear - 1882;
				for(int i =0 ; i<=nM1;i++){
					for (int j=0;j<=12;j++)
						nTd+=nYY[i][j];

					if (nYY[i][12] == 0)
						nTd += 336;
					else
						nTd += 362;
				}

			}

			nM1++;
			nN2 = nMonth -1;
			do{
				nM2++;
				if (nYY[nM1][nM2] > 2){
					nTd+=26+nYY[nM1][nM2] ;
					nN2++;
				}
				else if (nM2==nN2){
					break ;
				}
				else{
					nTd+= 28+nYY[nM1][nM2] ;
				}
			}
			while(true);

			if (bLeapMonth)
				nTd+= 28 + nYY[nM1][nM2];

			nTd += nDay + 29 ;
			nM1 = 1880 ;

			do{
				nM1++;
				nM2= 365;
				if (((nM1 % 400)==0) || (((nM1 % 100) != 0) &&((nM1 % 4) == 0)))
					nM2=366;
				if (nTd < nM2)
					break;

				nTd -= nM2;
			}
			while(true);

			nRYear = nM1;
			nMM[1] = nM2 - 337;   //2월..
			nM1 =0;

			do{

				if (nTd <= nMM[nM1])
					break;

				nTd -= nMM[nM1];
				nM1++;
			}
			while(true);

			nRMonth = nM1+1 ;
			nRDay     = nTd ;

			strReturnDate = String.valueOf(nRYear);
			if ( nRMonth < 10 )
				strReturnDate +="0";

			strReturnDate += String.valueOf(nRMonth);

			if ( nRDay < 10 )
				strReturnDate +="0";

			strReturnDate += String.valueOf(nRDay);

			return strReturnDate;
		}
		catch(Exception e ){
			return null;
		}
	}

	/**
	 * 입력한 해 직전(입력해의 윤달은 제외)까지의 윤달(년)의 수
	 * @param year int
	 * @return int
	 */
	public static int number_of_addyear(int year) {
		return (year-1)/4-(year-1)/100+(year-1)/400;
		//4의배수는 윤년, 100의 배수는 윤년아님, 그러나 400의 배수는 윤년


	}

	/**
	 * 음력을 양력으로 변환한다.
	 * 작성 날짜: [00-02-09 오후 3:40:2
	 * @param String strMoonDate
	 * @return java.lang.String
	 */
	public static String sun2moon(String strMoonDate){
		
		try{
			int nYear = 0 ;
			int nMonth = 0 ;
			int nDay  = 0 ;
			int aiSumMonth[] = { 31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

			if (strMoonDate.length() != 8){
				return null;
			}
			else{
				nYear = Integer.parseInt(strMoonDate.substring(0,4));
				nMonth = Integer.parseInt(strMoonDate.substring(4,6));
				nDay = Integer.parseInt(strMoonDate.substring(6));
			}

			if (nYear <1881 || nYear > 2043 || nMonth <1 || nMonth > 12) {
				return null;
			}
			int nTemp = 0;
			boolean bCheckYoun = false ;

			//		int iBetween  =1880*365 + 1880/4 - 1880/100 + 1880/400 + 30;
			int iBetween  = 0;
			int iBetween0 =0;
			int iBetween1 =686686;
			int iBetween2   =0;
			int iaDt[]=new int[163];
			int i =0 , j=0;
			int nMonth1 =0;
			int nMonth2 =0;


			String strReturnData ="";

			for(i=0; i<163; i++){
				iaDt[i] = 0;
				for(j=0; j<12; j++){
					switch(aiMoonDay[i*13 + j]){
						case 1:
						case 3:
							iaDt[i] = iaDt[i] + 29;
							break;
						case 2:
						case 4:
							iaDt[i] = iaDt[i] + 30;
					}
				}
				switch(aiMoonDay[i*13+12]){
					case 0:
						break;
					case 1:
					case 3:
						iaDt[i] = iaDt[i] + 29;
						break;
					case 2:
					case 4:
						iaDt[i] = iaDt[i] + 30;
						break;
				}
			}


			nTemp = (nYear-1);
			iBetween2 = nTemp*365 + nTemp/4 - nTemp/100 + nTemp/400;
			bCheckYoun = nYear%400==0 || nYear%100!=0 && nYear%4==0;

			if ( bCheckYoun)
				aiSumMonth[1] = 29;
			else
				aiSumMonth[1] = 28;
			if( nDay > aiSumMonth[nMonth-1] ) {
				return null;
			}

			for ( i =0 ;i<nMonth-1 ;i++){
				iBetween2 +=  aiSumMonth[i];
			}

			iBetween2 += nDay;

			/* ## 1881. 1. 30. - syear. smonth. sday. ## */
			iBetween  = iBetween2 - iBetween1 + 1;
			/* ## Lunar Year Caculation ## */
			iBetween0 = iaDt[0];

			for (  i=0 ;i< 163;i++){
				if ( iBetween <= iBetween0)
					break;
				iBetween0 += iaDt[i+1];
			}
			nYear = i+ 1881; /* Calculated Lunar Year */

			iBetween0 -= iaDt[i] ;

			iBetween	-= iBetween0 ;
			if (	aiMoonDay[i*13+12] != 0)
				nTemp =13;
			else
				nTemp = 12;
			nMonth2 = 0;
			for (j=0;j< nTemp;j++){
				nMonth2++;
				if (aiMoonDay[i*13+j] < 3){

					nMonth1 = aiMoonDay[i*13+j]  + 28;
				}
				else{
					nMonth1 = aiMoonDay[i*13+j]  + 26;
				}

				if (iBetween <= nMonth1)
					break;
				iBetween -= nMonth1 ;
			}
			nMonth = nMonth2 ;
			nDay    = iBetween ;
			strReturnData = String.valueOf(nYear);

			if (nMonth < 10) strReturnData +="0";
			strReturnData += String.valueOf(nMonth);

			if (nDay < 10) strReturnData +="0";
			strReturnData += String.valueOf(nDay);

			return strReturnData;
		}
		catch(Exception e){
			System.out.println("Exception");
			return null ;
		}
	}

	/**
	 * 특정 형식으로 날짜를 리턴하는 함수
	 *
	 * @param String str
	 * @param String flag
	 * @return String 
	 */
	public static String getDateFormat(String str, String flag) {

		String returnStr = "";

		if(str==null || str.length()<4)
			return str;

		// 020060822 과 같이 9자리로 넘어오는 경우
		if(str.length()==9 && str.charAt(0)=='0')
			str = str.substring(1);

		try {
			if ( flag.equals("/") || flag.equals(".") || flag.equals("-") ) {
				returnStr = str.substring(0,4) + flag + str.substring(4,6) + flag + str.substring(6,8);
			}

			else if(flag.equals("MD")){
				returnStr = str.substring(4,6) + "/" + str.substring(6);
			}

			else if(flag.equals("YM")){
				returnStr = str.substring(0,4) + "/" + str.substring(4,6);
			}
			else if(flag.equals("YYYY")){
				returnStr = str.substring(0,4);
			}
			else if(flag.equals("MM")){
				returnStr = str.substring(4,6);
			}
			else if(flag.equals("DD")){
				returnStr = str.substring(6,8);
			}
			else {
				returnStr = str.substring(0,4) + "년 " + str.substring(4,6) + "월 " + str.substring(6) + "일";
			}
		}catch(Exception e) {
			returnStr = str;
		}
		return returnStr;
	}
	
	
	/**
	 * 특정 형식으로 날짜를 리턴하는 함수
	 *
	 * @param String str
	 * @param String flag
	 * @return String 
	 */
	public static String getDateFormat2(String str, String flag) {

		String returnStr = "";

		if(str==null || str.length()<4)
			return str;

		// 020060822 과 같이 9자리로 넘어오는 경우
		if(str.length()==9 && str.charAt(0)=='0')
			str = str.substring(1);

		try {
			if ( flag.equals("/") || flag.equals(".") || flag.equals("-") ) {
				returnStr = str.substring(0,4) + flag + str.substring(5,7) + flag + str.substring(8,10);
			}

			else if(flag.equals("MD")){
				returnStr = str.substring(5,7) + "/" + str.substring(8);
			}

			else if(flag.equals("YM")){
				returnStr = str.substring(0,4) + "/" + str.substring(5,7);
			}
			else if(flag.equals("YYYY")){
				returnStr = str.substring(0,4);
			}
			else if(flag.equals("MM")){
				returnStr = str.substring(5,7);
			}
			else if(flag.equals("DD")){
				returnStr = str.substring(8,10);
			}
			else {
				returnStr = str.substring(0,4) + "년 " + str.substring(5,7) + "월 " + str.substring(8,10) + "일";
			}
		}catch(Exception e) {
			returnStr = str;
		}
		return returnStr;
	}


	/**
	 * 특정 형식으로 날짜를 리턴하는 함수
	 *
	 * @param String str
	 * @param String flag
	 * @param String type
	 * @return String 
	 */
	public static String getDateFormat(String str, String flag, String type) {

		String returnStr = "";
		
		returnStr = getDateFormat(str,flag);

		
		
		try {
			if(type.equals("YYYYMMDDHHMISS")){
				if(str.length()>12 ){
					returnStr += " "+str.substring(8,10) +":"+ str.substring(10,12) +":"+ str.substring(12,14);
				}
				else{
					type = "YYYYMMDDHHMI";
				}
			}

			if(type.equals("YYYYMMDDHHMI")){
				returnStr += " "+str.substring(8,10) +":"+ str.substring(10,12);
			}

		}catch(Exception e) {
			returnStr = str;
		}
		return returnStr;
	}


	/**
	 * 형식화된 날짜'열'을 얻는다.        
	 *
	 * @param format date format 
	 * @return 형식화된 날짜문자열
	 */
	public static String getFormattedTime(String format) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en", "US"));
		return sdf.format(now);
	}

	/**
	 *
	 * @param format date format
	 * @param day 가감할 일수
	 * @return 형식화된 날짜문자열
	 */
	public static String getFormattedTime(String format, int days) {
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DAY_OF_WEEK, days);
		java.util.Date date = ca.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en", "US"));
		return sdf.format(date);
	}

	/**
	 * 형식화된 날짜'열'을 얻는다.        
	 * 
	 * @param format date format 
	 * @param year
	 * @param month
	 * @param day
	 * @return 형식화된 날짜문자열
	 */
	public static String getFormattedTime(String format, int year, int month, int day) {
		Calendar ca = Calendar.getInstance();
		ca.set(year, month-1, day);
		java.util.Date date = ca.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en", "US"));
		return sdf.format(date);
	}

	/**
	 * 형식화된 날짜'열'을 얻는다.        
	 * 
	 * @param format date format 
	 * @param year
	 * @param month
	 * @param day
	 * @param days
	 * @return 형식화된 날짜문자열
	 */
	public static String getFormattedTime(String format, int year, int month, int day, int days) {
		Calendar ca = Calendar.getInstance();
		ca.set(year, month-1, day);
		ca.add(Calendar.DAY_OF_WEEK, days);
		java.util.Date date = ca.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(format, new Locale("en", "US"));
		return sdf.format(date);
	}

	/**
	 * <pre>
	 * 시작날짜와 종료날짜를 비교한다.
	 * </pre>
	 *
	 * @param startdate	시작 날짜 (YYYY-MM-DD HH:MI:SS)
	 * @param enddate	종료 날짜 (YYYY-MM-DD HH:MI:SS)
	 * @return 날짜비교결과 (정상:true, 종료일이 더 작을경우:false)
	 */
	public static boolean getDateComparison(String startdate, String enddate) {

		try{

			DateFormat fmt=DateFormat.getDateInstance();
			long start = fmt.parse(startdate).getTime();
			long end = fmt.parse(enddate).getTime();

			if(end < start) {
				return false;
			}
			else {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
	}
	
	/**
	 * 입력받은 날의 요일을 반환
	 * 
	 * @return
	 */
	public static int getDayOfTheWeek(int year, int month, int day){
		
		Calendar temp=Calendar.getInstance();
		temp.set(year, month-1, day);
		
        return temp.get(Calendar.DAY_OF_WEEK);
 
	}
	
	
	/**
	 * 입력받은 날짜를 Jan 28, 2010 형식으로 반환
	 * 
	 * @return
	 */
	public static String getDateFormat(String date){
		
		String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};
		
		String strYY = date.substring(0,4);
		String strMM = date.substring(5,7);
		String strDD = date.substring(8,10);
		
		if(strMM.substring(0,1) == "0"){
			strMM = month[Integer.parseInt(strMM.substring(1,2))-1];
		}else{
			strMM = month[Integer.parseInt(strMM)-1];
		}
		
		String strYYMMDD = strMM + " " + strDD + ", " + strYY;
		
		return strYYMMDD;
	}

	public static long getTimestamp()
	{
		return new Date().getTime();
	}

	// datetime 값을 출력하기 편하게 변경한다. 마지막에 .0 을 제거함
	public static String print(String dateStr)
	{
		if(dateStr==null)
			return "";

		if(dateStr.length()>19)
			return dateStr.substring(0,19);

		return dateStr;
	}

	public static String getCurrentTimeOld()
	{
		String ret = null;
		Date now = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ret = sdf.format(now);

		return ret;
	}
	
	public static String getCurrentTimeOld2(String zone)
	{
		String ret = null;
		
		GregorianCalendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if(zone!=null) {
			
			TimeZone tz = TimeZone.getTimeZone(zone);

			calendar.setTimeZone(tz);
			sdf.setTimeZone(tz);

		}

		ret = sdf.format(calendar.getTime());

		return ret;
	}
	
	public static String get2DaysBeforeTime()
	{
		String ret = null;
		Date now = new Date();

		now = new Date(now.getTime()-2*24*60*60*1000);		// 2일전 시간
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");

		sdf.setTimeZone(tz);

		ret = sdf.format(now);

		return ret;
	}

	public static String getTodayJP()
	{
		String ret = null;
		Date now = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");

		sdf.setTimeZone(tz);

		ret = sdf.format(now);

		return ret;
	}
	public static String getTomorrowJP()
	{
		String ret = null;
		Date now = new Date();
		
		now = new Date(now.getTime()+1*24*60*60*1000);		// 1일후 시간

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");

		sdf.setTimeZone(tz);

		ret = sdf.format(now);

		return ret;
	}
	
	public static String getCurrentTime(String zone)
	{
		String ret = null;
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if(zone!=null) {
			
			TimeZone tz = TimeZone.getTimeZone(zone);

			/*
			if(zone.equals("PDT")) {		// PDT 는 PST로 계산하고 daylight을 따로 처리함

				System.out.println("PDT");
				tz = TimeZone.getTimeZone("PST");

				if(tz.inDaylightTime(now)) {
				System.out.println("PDT daylight");
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					cal.add(Calendar.HOUR, -1);
					now = cal.getTime();
				}

			}
			System.out.println(tz.getDisplayName());
			*/

			sdf.setTimeZone(tz);
		}

		ret = sdf.format(now);

		return ret;
	}
	
	public static String getCurrentTime()
	{
		return getCurrentTime(null);
	}
	
	public static String getHourBeforeTime()
	{
		return getHourBeforeTime(null);
	}
	public static String getHourBeforeTime(String zone)
	{
		String ret = null;
		Date now = new Date();
		now = new Date(now.getTime() - 60 * 60 * 1000);		// 1시간전 (milliseconds 기준)
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if(zone!=null) {
			
			TimeZone tz = TimeZone.getTimeZone(zone);

			/*
			if(zone.equals("PDT")) {		// PDT 는 PST로 계산하고 daylight을 따로 처리함

				System.out.println("PDT");
				tz = TimeZone.getTimeZone("PST");

				if(tz.inDaylightTime(now)) {
				System.out.println("PDT daylight");
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					cal.add(Calendar.HOUR, -1);
					now = cal.getTime();
				}

			}
			System.out.println(tz.getDisplayName());
			*/

			sdf.setTimeZone(tz);
		}

		ret = sdf.format(now);

		return ret;
	}

	// server : US/JP/KR
	public static String getUTCDate(String dateStr, String hourStr, String minuteStr, String server) {

		int year = Integer.parseInt(dateStr.substring(0,4));
		int month = Integer.parseInt(dateStr.substring(5,7));
		int day = Integer.parseInt(dateStr.substring(8,10));
		int hour = Integer.parseInt(hourStr);
		int minute = Integer.parseInt(minuteStr);

		String zone = "Asia/Seoul";
		if(server.equals("US"))
			zone = "PST";


		GregorianCalendar cal = new GregorianCalendar(year, month-1, day, hour, minute, 0);
		cal.setTimeZone(TimeZone.getTimeZone(zone));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return sdf.format(cal.getTime());
	}

	public static int getYear(){
		int now_year;
		Calendar now = Calendar.getInstance();

		now_year = now.get(now.YEAR);

		return now_year;
	}
	
	public static int getMonth(){
		Calendar now = Calendar.getInstance();

		return now.get(now.MONTH) + 1;		// MONTH : 0 base

	}
}
