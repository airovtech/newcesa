/*============================================================================
 * @BusinessType : Common
 * @File : FetchURL.java
 * @FileName : URL 의 내용을 가져오는 클래스 
 *
 * Note:
 *
 * Change history
 * @LastModifyDate : 20050220
 * @LastModifier   : 
 * @LastVersion    : 1.0
 *   2005-01-03 최초생성
 ============================================================================*/
package com.cesa.util;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.cert.Certificate;


/**
 * url 을 입력받아서 해당 URL 의 값을 가져오고, 
 * post 및 file fetching 을 수행한다.  
 *
 * @version 1.0
 */
public class FetchURL
{
	/**
	 * 쿠키값 Vector 
	 */
    public Vector cookieValue;
	/**
	 * 쿠키값(;로 합쳐져 있는 형태)
	 */
    public StringBuffer mergedCookie=null;

	/**
	 * 생성자 
	 */
    public FetchURL()
	{
        cookieValue=new Vector();
    }
    
    /**
	 * 전달받은 URL 로 POST 방식으로 데이터를 전송한다. 
	 *
	 * @param actionURL post할 대상 URL
	 * @param postStr post할 데이터
	 */
    public String post(String actionURL, String postStr)
	{

        try{
            URL url = new URL(actionURL);
            URLConnection urlConn = url.openConnection();

            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false);
            ((HttpURLConnection)urlConn).setFollowRedirects(false);
            ((HttpURLConnection)urlConn).setRequestMethod("POST");

            urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            urlConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)");
            
            urlConn.connect();
            
            // POST Data to Server        
            OutputStreamWriter out = new OutputStreamWriter(urlConn.getOutputStream());
            out.write(postStr);
            out.flush();
            out.close();
            
            // Fetch Cookie value 
            boolean foundCookieField=false;
            String headerName=null;
            for(int i=0; i < 30; i++){ 
                headerName = urlConn.getHeaderFieldKey(i);
				System.out.println(headerName+":"+urlConn.getHeaderField(i));
                if(headerName != null && headerName.toLowerCase().equals("set-cookie")){
                    cookieValue.add(urlConn.getHeaderField(i));
                    foundCookieField=true;
                }
                else{
                    if(foundCookieField) break;
                }
            }

			return getContent(urlConn);
        }
        catch(Exception e){
            System.out.println(e);
        }

		return "";
    }
    
	/**
	 * <pre>
	 * 인터넷에서 파일을 다운로드 받아서, 로컬경로에 저장한다. 
	 * 파일이 존재하는 경우 기존 파일을 삭제한다. 
	 * </pre>
	 *
	 * @param aUrl 다운로드 받을 URL 
	 * @param storePath 저장할디렉토리
	 * @param fileName 저장할파일이름
	 * @return 저장된파일경로(FULL PATH)
	 */
    public String getFile(String aUrl, String storePath, String fileName) throws Exception
	{
        URL url;
        URLConnection urlConn=null;
		InputStream in = null;
		FileOutputStream out = null;
        
        try{
			// check space exists in url
			char c=0;
			int urlLength=aUrl.length();
			StringBuffer realURL = new StringBuffer();
			for(int k=0; k<urlLength; k++){
				if((c=aUrl.charAt(k))==' ') realURL.append("%20");
				else realURL.append(c);
			}

			// check storepath , if no then create
			File path = new File(storePath);
			if(!path.isDirectory()) path.mkdirs();

			int i = 0;
			String outfilename = storePath + "/" + fileName;
			File outfile = new File(outfilename);

			// 파일이 존재하면 지운다. 
			if(outfile.exists()) outfile.delete();

            url = new URL(realURL.toString());
            urlConn = url.openConnection();
            urlConn.setDoOutput(true);
            
            // set content type 
			urlConn.setRequestProperty("Content-Type", "application/octet-stream");
            urlConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)");
            ((HttpURLConnection)urlConn).setFollowRedirects(false);

            // open in stream and read file 
            in = urlConn.getInputStream();
			out = new FileOutputStream(outfile);

			int len = 0;
			byte [] b = new byte[1024];
            
            while((len=in.read(b)) != -1){
				out.write(b, 0, len);
            }
			return outfilename;
        }
		finally{
			// close stream
			try{
				if(in != null) in.close();
				if(out != null) out.close();
			}
			catch(Exception e){}
		}
    }

	/**
	 * URL 의 CONTENT 를 가져온다. 
	 *
	 * @param aUrl 웹페이지 URL
	 * @return 웹페이지내용(HTML)
	 */
    public String getContent(String aUrl) throws Exception
	{

		if(aUrl.toLowerCase().contains("https")){
			return getHttpsContent(aUrl);
		}

		URL url = new URL(aUrl);
		URLConnection urlConn = url.openConnection();
		((HttpURLConnection)urlConn).setFollowRedirects(false);
		((HttpURLConnection)urlConn).setInstanceFollowRedirects(false);

		// 헤더를 셋팅한다. 
		urlConn.setDoOutput(true);
		urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		urlConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)");

		// 쿠키를 셋팅한다. 
		if(mergedCookie==null){
			mergedCookie=new StringBuffer();
			for(int i=0; i<cookieValue.size(); i++){
				if(i>0) mergedCookie.append(";");
				mergedCookie.append(cookieValue.elementAt(i).toString());
			}
		}
		if(mergedCookie != null) urlConn.setRequestProperty("Cookie", mergedCookie.toString());

		// Location Header 를 처리한다. 
		String location = urlConn.getHeaderField("Location");
		if(location != null)
		{
			if(location.startsWith("http")) location = location;
			else if(location.startsWith("/")) location = aUrl.substring(0, aUrl.substring(8).indexOf("/")) + "/"+location;
			else location = aUrl.substring(0, aUrl.lastIndexOf("/")) + "/" + location;

			// URL 을 한글화 처리한다. 
			location = new String(location.getBytes("8859_1"), "euc-kr");

			// URL 에 공백이 있으면 공백을 처리한다. 
			location = replace(location, " ", "%20");

			return getContent(location);
		}
		// JavaScript Location(self.location='') 은 처리하지 않는다. 
		
		return getContent(urlConn);
    }

	/**
	 * HTTPS URL 의 CONTENT 를 가져온다. 
	 *
	 * @param aUrl 웹페이지 URL
	 * @return 웹페이지내용(HTML)
	 */
    public String getHttpsContent(String aUrl) throws Exception
	{
		URL url = new URL(aUrl);
		HttpsURLConnection urlConn = (HttpsURLConnection)url.openConnection();
            
		urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		urlConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0)");

		print_https_cert(urlConn);
		
		return getContent(urlConn);
    }

	private void print_https_cert(HttpsURLConnection con){

		if(con!=null){

			try {

				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for(Certificate cert : certs){
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}

		}

	}

	/**
	 * 루프를 돌면서 getContent 를 실행한다. ( 여러번 실행해야 정상적인 결과를 리턴하는 경우도 있으므로 )
	 * @param url 가져올 URL
	 * @return HTML Content 
	 */
	public String getContentLoop(String url) throws Exception
	{
		String s = null;

		for(int i=0; i<10; i++){
			s = getContent(url);
			if(s != null && s.length() > 100) break;
		}
		return s;
	}

	/**
	 * URL 의 CONTENT 를 가져온다. 
	 *
	 * @param con 웹페이지 <code>URLConnection</code> 객체
	 * @return 웹페이지내용(HTML)
	 */
    public String getContent(URLConnection con) throws Exception
	{
        // read response
        String inputLine;
        StringBuffer sbuf = new StringBuffer();
        
        if(con == null){
            return null;
        }

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((inputLine = in.readLine()) != null){
			sbuf.append(inputLine);
			sbuf.append('\n');
		} 
		in.close();

        return sbuf.toString().trim();
    }

	/**
	 * HTTPS URL 의 CONTENT 를 가져온다. 
	 *
	 * @param con 웹페이지 <code>URLConnection</code> 객체
	 * @return 웹페이지내용(HTML)
	 */
    public String getContent(HttpsURLConnection con) throws Exception
	{
        // read response
        String inputLine;
        StringBuffer sbuf = new StringBuffer();
        
        if(con == null){
            return null;
        }

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		while ((inputLine = in.readLine()) != null){
			sbuf.append(inputLine);
			sbuf.append('\n');
		} 
		in.close();

        return sbuf.toString().trim();
    }

	/**
	 * <pre>
	 * 특정 문자열에서 어떤 문자열을 찾아        
	 * 원하는 문자열로 치환한다.         
	 * ex) Replace("sssadaf", "ada", "$");        
	 * </pre>
	 */
    public static String replace(String line, String oldString, String newString)         
    {        
        int index = 0;        
        int newLen = 0;        
        int oldLen = 0;        
        
        newLen = newString.length();        
        oldLen = oldString.length();        
        
        while((index = line.indexOf(oldString, index)) >= 0){        
            line = line.substring(0, index) + newString + line.substring(index+oldLen);        
            index += newLen;        
        }        
        return line;        
    }        
}

