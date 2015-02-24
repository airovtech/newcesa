package com.cesa.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 웹 인스턴스가 시작될 때, 수행되는 초기화 서블릿<br>
 * <br>
 * 
 * InitServlet 을 설정하고 있는 web.xml 을 보면,<br>
 * <pre>
 * ...
 * &lt;servlet&gt;
 * &lt;servlet-name&gt;AFFL2 Init&lt;/servlet-name&gt;
 * &lt;servlet-class&gt;com.hyundai.affl.InitServlet&lt;/servlet-class&gt;
 *
 * &lt;init-param&gt;
 *   &lt;param-name&gt;init-file&lt;/param-name&gt;
 *   &lt;param-value&gt;/WEB-INF/conf/hyundai.properties&lt;/param-value&gt;
 * &lt;/init-param&gt;
 * 
 * &lt;load-on-startup&gt;1&lt;/load-on-startup&gt;
 * &lt;/servlet&gt;
 * ...
 * </pre>
 * 
 * 여기서 보는 것처럼 init-file 을 지정해야 한다. 파일이 존재하지 않거나,
 * 읽을 수 없는 상태인 경우, 사이트 전반적인 에러가 발생 할 수 있음으로,
 * 반드시 확인하기 바란다.<br>
 * <br>
 * 
 * init-file 로 지정한 프로퍼티 파일을 로드하여, <b>query.config</b> 값을 읽어서,
 * QueryContext 를 로드하도록 되어 있다. 따라서, QueryContext 를 설정하는 파일을 바꾸거나,
 * key 값을 바꾸려면, 아래의 QueryContext의 query.config 부분을 수정해야 한다.<br>
 * <br>
 * 
 * 현재는 "query.config" 라는 값을 사용하도록 되어 있으니, init-file 로 지정된 파일에는
 * 반드시 query.config 키값과 파일이름이 설정되어 있어야 한다.
 * 그렇지 않으면, 쿼리를 수행할 수 없다.<br>
 * <br>
 * 
 * @version	1.0
 * @author	woo jin ho, 2008-10-22
 *
 */
public class InitServlet extends HttpServlet {
	
	
	Logger log = Logger.getLogger(InitServlet.class);
    
    public void init() {
    	
    	String prefix = getServletContext().getRealPath("/");
        String file = getInitParameter("init-file");
       
        String path = prefix + file;
        
        //-------------------------------------------------------------------
    	// log4j 로그 세팅
        // log4j.properties 를 별도로 두지 않고, 하나의 프로퍼티 파일에서 처리하도록 했다.
        //-------------------------------------------------------------------
    	PropertyConfigurator.configure( path );
    	
    	log.info("InitServlet Start!");
    	log.info("prefix : " + prefix);
    	log.info("path : " + path);

        //-------------------------------------------------------------------
    	// Application Configration Loading
        //-------------------------------------------------------------------
	
    	log.info("--------------------------------------------------------------");
    	log.info("Application Config Loading...");
    	log.info( path );
    	log.info("SiteContext Loading...");
    	SiteContext sc = SiteContext.getInstance( path );
    	log.info(sc.toString());

        //-------------------------------------------------------------------
    	// query.properties 에 해당하는 값을 읽어서 QueryContext 를 설정한다.
    	// 다른 키값을 사용하러면, 이 부분을 수정하도록 한다.
        //-------------------------------------------------------------------
    	log.info("QueryContext Loading...");
    	log.info( prefix );
    	QueryContext qc = QueryContext.getInstance();
    	log.debug( qc.toString() );


    	log.info("--------------------------------------------------------------");
    }
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    	throws ServletException, IOException {
    	super.doGet(req, res);
    }
}
