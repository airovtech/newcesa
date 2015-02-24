package com.cesa.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;


/**
 * PropertiesManager class
 * Commons Configuration 의 PropertiesConfiguration 을 확장한다.
 * <br/>
 * 
 * delimiter 는 기본적으로 ';' 로 설정된다. 
 * 따라서, 프로퍼티 파일을 만들 때, ';' 는 사용할 수 없으며,
 * 반드시 사용하려면 '\;' 와 같이 이스케이프 처리하면 사용가능하다.
 * 
 * @version	1.0
 * @author	moon jong deok, 2008-10-22
 */
public class PropertiesManager extends PropertiesConfiguration {

	private char delimiter = ';';
	
	public PropertiesManager() {
		setEncoding("UTF-8");
		setDelimiter( delimiter );
	}
	
	/**
	 * 파일을 읽어 들인다. 세미콜론이 기본 delimiter 이다.
	 * 
	 * @param filename
	 * @throws ConfigurationException
	 */
	public PropertiesManager( String filename )
		throws ConfigurationException
	{
		super( filename );
		setEncoding("UTF-8");
		setDelimiter( delimiter );
		setReloadingStrategy( new FileChangedReloadingStrategy() );
	}
}
