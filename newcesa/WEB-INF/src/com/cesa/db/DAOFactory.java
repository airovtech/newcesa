/*============================================================================
 * Copyright(c) 2007 Dntech7
 * 
 * @BusinessType : Common
 * @File : DAOFactory.java
 * @FileName : DAO를 생성해 준다. 모든 DAO는 DAOFactory를 통해 생성해야 한다.
 *
 * Note:
 *
 * Change history
 * @LastModifyDate : 20070309
 * @LastModifier   : Dntech7
 * @LastVersion    : 1.0
 *   2007-03-09 Dntech7
 *      1.0    최초생성 
 ============================================================================*/
package com.cesa.db;
import com.cesa.common.*;
import com.cesa.base.*;

public class DAOFactory extends BaseFactory {

    final static DAOFactory instance =  getDAOFactory();

    public DAOFactory() {

		super();
	}

    /**
     * DAOFactory 인스턴스를 반환한다.
	 *
     * @return DAOFactory
     */
    public static DAOFactory getInstance() {
        return instance;
    }

    /**
     * key에 해당하는 DAO의 instance를 조회한다.
     *
     * @param key 객체의 key
     * @return DAO의 instance
     */
	public BaseDAO getDAO(String key) {
		return (BaseDAO)getObject(key);
    }

    /**
     * DAOFactory를 반환한다.
     *
     * @return DAOFactory
     */
    private static DAOFactory getDAOFactory() {

    	return new DAOFactory();
    }
}
