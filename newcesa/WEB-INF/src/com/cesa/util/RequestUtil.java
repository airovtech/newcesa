/*============================================================================
 *	Request 객체를 Map에 담고 반환한다.
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

public class RequestUtil{

	Logger log = Logger.getLogger(RequestUtil.class);

	private HashMap list = null;

	private HttpServletRequest request = null;
	private JspWriter out = null;
	

	public RequestUtil(HttpServletRequest request, JspWriter out, boolean encryptRequired) throws Exception
	{
		this.request = request;
		this.out = out;

		if(encryptRequired == true) {		// 암호화가 필수적인 경우
			initEncrypt();
		} else {
			initNoEncrypt();
		}

		//printLog(request.getRemoteAddr(), request.getRequestURL().toString());

	}
	
	private void initEncrypt() throws Exception
	{
		list = new HashMap();

		String region	= request.getParameter("region");
		String locale	= request.getParameter("locale");
		String os_type	= request.getParameter("os_type");
		String version	= request.getParameter("version");

		// region, os_type, version, data 변수를 처리함 data만 복호화
		list.put("region", region);
		list.put("locale", locale);
		list.put("os_type", os_type);
		list.put("version", version);

		String data = request.getParameter("data");
		String name = null;

		if(data!=null) {

			String decStr = AesUtil.decrypt(data);

			//log.debug("--- original : "+data);
			log.debug("["+request.getRemoteAddr()+"] ["+request.getRequestURI()+"] region("+region+"), locale("+locale+"), os_type("+os_type+"), version("+version+"), decrypt : "+decStr);
			log.debug(request.getRequestURI());
			log.debug(request.getRequestURL());
			log.debug(request.getServletPath());

			try {

				JSONObject json = JSONObject.fromObject(decStr);

				//HashMap decList = (HashMap)JSONObject.toBean(json, HashMap.class);
				//Iterator i = decList.keySet().iterator();

				Iterator i = json.keySet().iterator();

				while(i.hasNext()) {

					name = (String)i.next();
					list.put(name, json.get(name).toString());
				}

			}catch(JSONException e) { 
				// data 값이 JSON 객체가 아닐경우, 그대로 변수에 저장

				list.put("data", decStr);
			}
		}

	}

	private void initNoEncrypt() throws Exception
	{
		list = new HashMap();

		Enumeration e = request.getParameterNames();

		String name = null; 
		String value = null;

		StringBuffer sbuf = new StringBuffer();

		while(e.hasMoreElements()) {

			name = (String)e.nextElement();
			list.put(name, request.getParameter(name));

			sbuf.append("&"+name+"="+request.getParameter(name));
			
		}
			
		log.debug("["+request.getRemoteAddr()+"] ["+request.getRequestURL()+"] no-decrypt : "+sbuf.toString());
	}

	public void printJsp() throws Exception
	{

		String name = null;
		Iterator names = list.keySet().iterator();

		out.println("--------------- Request Parameters start --------------<br/>");
		while(names.hasNext()) {

			name = (String)names.next();
			
			out.println(name + " : " + list.get(name) + "<br/>");
		}
		out.println("--------------- Request Parameters end  --------------<br/>");


	}
	
	public void printLog(String ip, String url) throws Exception
	{

		String name = null;
		Iterator names = list.keySet().iterator();

		log.debug("--------------- Request Parameters start ["+ip+"] ("+url+") --------------");
		while(names.hasNext()) {

			name = (String)names.next();
			
			log.debug("["+ip+"] " + name + " : " + list.get(name) );
		}
		log.debug("--------------- Request Parameters end  --------------");


	}

	public String get(String name) {

		String value = (String)list.get(name);

		if(value==null)
			return "";

		return value;
	}
	
	public int getInt(String name) {

		String value = (String)list.get(name);

		if(value==null)
			return 0;

		try {
			return Integer.parseInt(value);
		}catch(Exception e) {
		}

		return 0;
	}

}

