package com.cesa.common;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.cesa.base.TObject;
import com.cesa.util.PropertiesManager;

/**
 * 전체 시스템 관련 환경변수값을 가지고 있는 클래스<br>
 * <br>
 * 
 * InitServlet 에서 SiteContext 를 생성한다. web.xml 에 기술된 것처럼
 * init-file 이라는 파라미터로 정의된 파일을 읽어 들인다.<br>
 * <br>
 * 
 * web.xml 의 내용을 보자.<br>
 * 
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
 * 웹 인스턴스 또는 웹 컨텍스트가 로딩되면서 web.xml 을 참조하고,
 * 여기에 선언된 것처럼 com.hyundai.affl.InitServlet 을 호출하고,
 * 파라미터로 init-file 의 값을 가져와서 해당 파일을 로드하게 된다.
 * 예제에서는 /WEB-INF/conf/hyundai.properties 파일을 읽게 된다.
 * 프로퍼티(hyudau.properties) 파일은 commons-configuration 에서 정의 하는 프러퍼티 파일의 
 * 문법을 지키면 된다.<br>
 * <br>
 * 
 * 일반적으로 SiteContext 를 참조할 때는, 다음과 같은 방식으로 사용한다.<br>
 * <pre>
 * ...
 * import com.hyundai.affl.common.SiteContext;
 * ...
 * SiteContext sc = SiteContext.getInstance();
 * ...
 * </pre>
 * 일반적인 싱글톤 객체를 가져다가 쓰는 방식과 별반 다르지 않다.
 * 다만, 함수 getInstance(filename) 의 사용에는 주의가 필요하다.<br>
 * <pre>
 * <b>//이 함수는 반드시 최초 클래스 생성시 한번만 사용해야 한다.</b> 
 * SiteContext sc = SiteContext.getIntance( filename );
 * </pre>
 * 
 * 이는 commons-configuration 의 특징 때문으로 객체를 생성하면서
 * 파일을 지정하지 않으면, 대상 파일에 변경이 있어도 인식하지 못하는 문제점을
 * 해결하려고만 만든 함수이기 때문이다.
 * 따라서, 반드시 최소 생성시에만 사용하도록 한다.<br>
 *  <br>
 * 
 * @version 1.0
 * @author moon jong deok, 2008-10-22
 */
public class SiteContext extends TObject{
	
    private static Logger log = Logger.getLogger(SiteContext.class);
    
    /**
     * 싱글톤. SiteContext
     */
    private static SiteContext siteContext = null;
    
    /**
     * 프로퍼티를 담고 있는 변수.
     */
    PropertiesManager prop = null; 
    
    /**
     * 싱글톤으로 생성하도록 한다.
     * @return
     */
    public static synchronized SiteContext getInstance() {
        if ( siteContext == null ) {
            siteContext = new SiteContext();
        }

        return siteContext;
    }
    
    /**
     * 싱글톤으로 생성하면서, 파일이름을 지정하도록 할 때, 사용한다.<br>
     * <br>
     * 
     * 대부분의 사용은 SiteContext.getInstance() 와 같은 방식으로 사용되지만,
     * commons-configuration 의 특성으로 생성할 때, 파일을 주지 않으면,
     * 런타임에서 파일의 수정내용이 반영되지 않는 점을 고려하여, 최초 생성시에만
     * 사용하는 별도의 getInstance() 함수를 만들었다.<br>
     * <br>
     * 
     * 따라서, 일반적인 사용에서는 사용하면 안되며, com.hyundai.affl.InitServlet 과 같은
     * 초기화 관련 클래스 및 서블릿에서만 단 한번 호출되도록 프로그래밍 해야 한다.
     * @param filename
     * @return SiteContext 객체
     */
    public static synchronized SiteContext getInstance( String filename ) {
        if ( siteContext == null ) {
            siteContext = new SiteContext( filename );
        }

        return siteContext;
    }
    
    /**
     * InitConfig 파일에 설정된 프러퍼티 파일을 읽어온다.
     * 싱글톤으로 직접 생성할 수 없다.
     */
    private SiteContext( ) {
    	super();
   		prop = new PropertiesManager();
    }
    
    /**
     * getInstance(filename) 함수에서 사용하는 별도의 생성자.<br>
     * commons-configuration 의 특성 상, 생성시에 파일을 설정해야 함으로,
     * 이를 수용하기 위해 파일 이름을 받는 별도의 생성자를 만들었다. 
     * @param filename
     */
    private SiteContext( String filename ) {
    	super();
    	try {
    		prop = new PropertiesManager( filename );
    	} catch ( Exception e ) {
    		log.fatal( e );
    	}
    }
    
    /**
     * 새로운 파일로 설정한다.<br>
     * 파일을 로드는 하지만, 설정 파일에 변경이 있을 때, 자동으로 로드되지 않는다.
     * 따라서, 꼭 필요한 경우만 사용하도록 하며, 사용 시에 충분한 테스트가 필요하다.
     * @param path
     */
    public synchronized void set( String path ) {
        try { 
			prop.load( path );
		} catch ( Exception e ) {
		    log.debug( e );
		}  
		
    }
    
    /**
     * key 에 해당하는 스트링 값을 리턴한다.
     * @param key
     * @return
     */
    public String		get( String key )				{ return prop.getString(key);	}
    
    /**
     * key 에 해당하는 정수 값을 리턴한다.
     * @param key
     * @return
     */
    public int			getInt( String key )			{ return prop.getInt(key);		}
    
    /** 
     * key 에 해당하는 float 값을 리턴한다.
     * @param key
     * @return
     */
    public float		getFloat( String key )		{ return prop.getFloat(key);		}
    
    /**
     * key 에 해당하는 double 값을 리턴한다.
     * @param key
     * @return
     */
    public double	getDouble( String key )	{ return prop.getDouble(key);	}

	public void set(String key, String value){
		prop.setProperty(key, value);
		/*
		try{
			prop.save();
		}
		catch(Exception e) {
			log.fatal(e, e);
		}
		*/
	}
    
    /**
     * PropertiesManager 값을 리턴한다.
     * @return
     */
    public PropertiesManager getProperties() { return prop; }
    
    /**
     * 현재 SiteContext 의 값을 스트링으로 변환한다.
     * 주로 디버그 용으로 사용된다.
     */
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	
    	Object obj = null;
    	Iterator en = prop.getKeys();
        while ( en.hasNext()  ) {
            obj = en.next();
            sb.append( obj + "=" +prop.getString((String)obj) + "\n");
        }
        
        return sb.toString();
    }
    
    /**
     * 웹 상에서 테스트로 SiteContext 의 값을 확인할 때, 사용한다.
     * toString() 으로 하면, 한 줄로 붙어 버려 도저히 볼 수가 없다. ^^;
     * @return String HTML 
     */
    public String toHtml(){
    	StringBuffer sb = new StringBuffer();
    	
    	Object obj = null;
    	Iterator en = prop.getKeys();
        while ( en.hasNext()  ) {
            obj = en.next();
            sb.append( obj + "=" +prop.getString((String)obj) +"<br>\n" );
        }
        
        return sb.toString();
    }
}
