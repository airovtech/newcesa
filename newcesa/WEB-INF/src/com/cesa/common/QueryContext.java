package com.cesa.common;

import java.util.Iterator;
import org.apache.log4j.Logger;
import com.cesa.util.XMLConfiguration;

/**
 * QueryContext class
 * SqlMapConfig에 정의된 SQL문을 읽어 사용한다.
 *
 * @version	1.0
 * @author	moon jong deok, 2008-10-22
 */
public class QueryContext {
	
	private static Logger logger = Logger.getLogger(QueryContext.class);
	
	/**
	 * 싱글톤. 프로퍼티 내용을 담고 있다.
	 */
    private static QueryContext queryContext = null;
	
    /**
     * XML Configuration 내용을 담고 있다.
     */
	public static XMLConfiguration config;
	
	/**
     * 싱글톤으로 생성하도록 한다.
     * @return
     */
    public static synchronized QueryContext getInstance() {
        if ( queryContext == null ) {
        	queryContext = new QueryContext();
        }
        
        return queryContext;
    }
	
    /**
     * 설정파일을 로드 한다.
     *
     */
	private QueryContext () {
		if(config==null){
			String basePath = SiteContext.getInstance().get("query.path");
			String cofigFilePath = SiteContext.getInstance().get("query.config");
			config = new XMLConfiguration();
			config.setBasePath(basePath);
			config.setConfigFilePath(cofigFilePath);
			config.load();
				
			Iterator ks = config.getKeys();
			logger.debug("------------------- Print Key --------------------------");
			while(ks.hasNext()){
				String s = String.valueOf(ks.next());
				logger.debug("qid=["+s+"]");
				logger.debug("\r" + config.getString(s));
			}
			logger.debug("--------------------------------------------------------");
		}
	}
	
	/**
	 * 컨피그를 리로드 한다.
	 *
	 */
	public synchronized void reloadConfig(){
		config = null;
		queryContext = new QueryContext();
	}
	
	/**
	 * Query
	 * @param key
	 * @return
	 */
	public String get(String key){
		return config.getString(key);
	}
}
