
/*============================================================================
 * File Name : BaseFactory.java
 * package   : com.adlinker.common
 * Desc      : Properties 파일을 통해 클래스를 생성해주는 Factory
 * Auther    : 우진호
 * Date      : 2007-01-02 최초작성
 * Copyright (c) 2007 dnt7.com. All Rights Reserved.
 *
 * 수정내역
 *
 ============================================================================*/

package com.cesa.base;

import java.lang.reflect.Constructor;
import org.apache.log4j.Logger;

public abstract class BaseFactory {

	protected static final Logger logger = Logger.getLogger(BaseFactory.class);

    /**
     * className에 해당하는 클래스를 생성한다.
     *
     * @param className 클래스의 이름
     * @return className에 해당하는 Class 객체
     */
    protected Class getClass(String className) {

    	if(logger.isDebugEnabled()) {
    		//logger.debug("className="+className);
		}

        Class clazz = null;
        try {
        	clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            logger.error("Can't find the class "+className, e);
        }
        return clazz;
    }

    /**
     * className에 해당하는 객체를 생성한다.<br>
     * className에 지정된 객체는 default contructor가 정의되어 있어야 한다.
     *
     * @param className 클래스의 이름
     * @return className에 해당하는 Class 객체
     */
    protected Object getObject(String className) {

        Class clazz = getClass(className);
        Object instance = null;
        if (clazz != null) {

            try {
                instance=clazz.newInstance();
            } catch (Throwable e) {
                logger.error("Can't create class", e);
            }
        }
        return instance;
    }

    /**
     * className에 해당하는 객체를 생성한다.<br>
     * 해당 객체의 constructor가 parameter를 가질 경우 사용한다.
     *
     * @param className 객체의 이름
     * @param parameterTypes constructor의 parameter types
     * @param params constructor의 parameter values
     * @return className에 해당하는 객체
     */
    protected Object getObject(String className, Class[] parameterTypes,Object[] params) {

        Class clazz = getClass(className);
        Object instance=null;
        if (clazz!=null) {
            try {
                Constructor constructor = clazz.getConstructor(parameterTypes);
                instance = constructor.newInstance(params);
            } catch (Throwable e) {
                logger.error("Can't create class",e);
            }
        }
        return instance;
    }
}
