package com.cesa.util;


import java.util.Iterator;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.log4j.Logger;

/**
 * XML Configuration load.
 * 
 * @author Administrator
 *
 */
public class XMLConfiguration extends BaseConfiguration {
	
	private static Logger logger = Logger.getLogger(XMLConfiguration.class);
	
	private String basePath = null;
	
	private String configFilePath = null;
	
	/**
	 * Constructor
	 * 
	 * @param fileName
	 * @throws ConfigurationException
	 */
	public XMLConfiguration(){
	}
	
	/**
	 * XML Configuration load.
	 * 
	 * @param fileName
	 */
	public void load(){
		try{
			ConfigurationFactory factory = new ConfigurationFactory();
			factory.setBasePath(basePath);
			factory.setConfigurationFileName(configFilePath);
			logger.info("configFilePath : " + configFilePath);
			Configuration config = factory.getConfiguration();
			
			Iterator ks = config.getKeys();
			while(ks.hasNext()){
				String s = String.valueOf(ks.next());
				this.setProperty(s, config.getString(s));
			}
		}
		catch (ConfigurationException ce){
			logger.fatal("configFilePath : " + configFilePath);
			logger.fatal(ce, ce);
		}
		catch (Exception e){
			logger.fatal(e, e);
		}
	}

	/**
	 * @return the cofigFilePath
	 */
	public String getConfigFilePath() {
		return configFilePath;
	}

	/**
	 * @param cofigFilePath the cofigFilePath to set
	 */
	public void setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
	}

	/**
	 * @return the basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * @param basePath the basePath to set
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	
}
