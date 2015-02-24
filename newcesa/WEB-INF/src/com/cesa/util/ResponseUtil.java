/*============================================================================
 *	Response 객체를 Map에 담고 반환한다.
 *	data 는 복호화를 하고 Map에 담는다.	
 ============================================================================*/
package com.cesa.util;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import net.sf.json.*;
import com.cesa.common.*;
import com.cesa.db.*;

public class ResponseUtil{

	Logger log = Logger.getLogger(ResponseUtil.class);

	private HashMap list = null;
	private HashMap encList = null;

	private HttpServletResponse response = null;
	private JspWriter out = null;
	

	public ResponseUtil(HttpServletResponse response, JspWriter out) throws Exception
	{
		this.response = response;
		this.out = out;

		init();
	}

	private void init() throws Exception
	{
		list = new HashMap();
		encList = new HashMap();

	}

	public void put(String name, String value) {
		put(name, value, false);
	}

	// 암호화 여부를 인자로 전달
	public void put(String name, String value, boolean encryptRequire)
	{

		if(encryptRequire==true) 
			encList.put(name, value);
		else
			list.put(name, value);
	}

	public void put(String name, long value, boolean encryptRequire)
	{
		put(name, String.valueOf(value), encryptRequire);
	}

	public String toString()
	{

		printLog();

		String encData = "";
		JSONObject json = new JSONObject();
		
		String name = null;
		Iterator names = list.keySet().iterator();
		
		while(names.hasNext()) {

			name = (String)names.next();

			json.put(name, (String)list.get(name));
			
		}

		// 암호화할 값을 data 필드에 추가한다.
	
		try {
			encData = _encToString();		// for test

			if(encData.length()>0) {		// Data가 있을 경우에만 암호화하고, data 필드에 추가한다.
				//; 	//for test
				encData = AesUtil.encrypt(encData);
				json.put("data", encData);
			}
		}catch(Exception e) {
			log.fatal(e);
		}
		

		return json.toString();
	}

	// 암호화할 값을 만든다.
	private String _encToString()
	{
		JSONObject json = new JSONObject();
		boolean dataExist = false;
		
		String name = null;
		Iterator names = encList.keySet().iterator();
		
		while(names.hasNext()) {

			dataExist = true;

			name = (String)names.next();

			json.put(name, (String)encList.get(name));

		}

		// 데이터가 하나도 없으면 반환하지 않는다.
		if(dataExist==true)
			return json.toString();
		else
			return "";
	}
	
	public void printLog()
	{
		StringBuffer sbuf = new StringBuffer();
		
		String encData = "";
		
		String name = null;
		Iterator names = list.keySet().iterator();
		
		while(names.hasNext()) {

			name = (String)names.next();

			//log.debug(name + " : " + (String)list.get(name));
			
		}

		log.debug("--- Response [enc] : "+_encToString());

	}
}

