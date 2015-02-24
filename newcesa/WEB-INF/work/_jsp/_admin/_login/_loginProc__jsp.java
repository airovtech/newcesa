/*
 * JSP generated by Resin-3.1.9 (built Mon, 13 Apr 2009 11:09:12 PDT)
 */

package _jsp._admin._login;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import javax.naming.*;
import net.sf.json.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Map.Entry;
import com.cesa.common.*;
import com.cesa.dao.*;
import com.cesa.util.*;
import com.cesa.db.*;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.codec.digest.DigestUtils;

public class _loginProc__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html; charset=UTF-8");
    request.setCharacterEncoding("UTF-8");
    try {
      
	/*============================================================================
	 * @ Description : Free app party Admin Login
	 *
	 * \u00ec\u009e\u0091\u00ec\u0084\u00b1\u00ec\u009d\u00bc : 2011.04.14
	 * \u00ec\u009e\u0091\u00ec\u0084\u00b1\u00ec\u009e\u0090 :	\u00ec\u00b5\u009c\u00ed\u0098\u0095\u00eb\u00b2\u0094
	 ============================================================================*/

      out.write(_jsp_string0, 0, _jsp_string0.length);
      

	//Cache \uc5c6\uc74c
	response.setDateHeader("Expires", -1);
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-store"); //HTTP 1.0
	response.setHeader("Cache-Control","no-cashe"); //HTTP 1.1

	request.setCharacterEncoding("utf-8");

	String remoteAddr =request.getRemoteAddr();

	//\uc138\uc158 timeout \uc124\uc815
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

      out.write(_jsp_string1, 0, _jsp_string1.length);
      
	// Params
	String adminid = StringUtils.defaultString(request.getParameter("adminid"));
	String password = StringUtils.defaultString(request.getParameter("password"));

	// MD5
	password = DigestUtils.md5Hex(password);

	// Vars
	RowSetMapper cRowSet = null;
	String check_result = "";

	// \uad00\ub9ac\uc790 \uc544\ub514\uc774\uc640 \ube44\ubc00\ubc88\ud638\ub97c \ud655\uc778\ud55c\ub2e4.
	check_result = AdminDAO.getInstance().isAdmin(adminid, password);

	if(check_result.equals("success")){
		//\uae30\ubcf8\uc815\ubcf4\ub97c \uac00\uc838\uc628\ub2e4.
		cRowSet = AdminDAO.getInstance().chkAdminId(adminid);

		// session \uc601\uc5ed\uc5d0 adminid\ub97c \uc800\uc7a5\ud55c\ub2e4.
		session.setAttribute("adminID", adminid);
		
		if(cRowSet.next()){
			session.setAttribute("sPermission", StringUtils.defaultString(cRowSet.getString("permission"),""));
			session.setAttribute("sGroup", StringUtils.defaultString(cRowSet.getString("user_group"),""));
		}

		session.setMaxInactiveInterval(60*60*24);	// session time : 24\uc2dc\uac04
		out.println(session.getMaxInactiveInterval());
		response.sendRedirect("../main.jsp");
	}
	else {
		response.sendRedirect("login.jsp?check_result="+check_result);
	}


      out.write(_jsp_string2, 0, _jsp_string2.length);
      
	}
	catch(Exception e){
		log.fatal(e.getMessage(), e);
	}
	finally{
	}

      out.write(_jsp_string1, 0, _jsp_string1.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_application.getJspApplicationContext().freePageContext(pageContext);
    }
  }

  private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

  public java.util.ArrayList _caucho_getDependList()
  {
    return _caucho_depends;
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;
    if (com.caucho.server.util.CauchoSystem.getVersionId() != 5783496155892875126L)
      return true;
    for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
      com.caucho.vfs.Dependency depend;
      depend = (com.caucho.vfs.Dependency) _caucho_depends.get(i);
      if (depend.isModified())
        return true;
    }
    return false;
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    com.caucho.server.webapp.WebApp webApp
      = (com.caucho.server.webapp.WebApp) config.getServletContext();
    super.init(config);
    com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("admin/login/loginProc.jsp"), -7027680625346826242L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("include/header.jsp"), 7226424049310249664L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("include/footer.jsp"), -7402735715276527379L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string1;
  private final static char []_jsp_string2;
  private final static char []_jsp_string0;
  static {
    _jsp_string1 = "\n\r\n".toCharArray();
    _jsp_string2 = "\r\n".toCharArray();
    _jsp_string0 = "\r\n\r\n".toCharArray();
  }
}