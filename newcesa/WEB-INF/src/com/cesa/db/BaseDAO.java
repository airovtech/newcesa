/*============================================================================
 * Copyright(c) 2007 Dntech7
 * 
 * @BusinessType : Common
 * @File : BaseDAO.java
 * @FileName : DAO Super Class
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public abstract class BaseDAO {

    private static DAOFactory daoFactory = DAOFactory.getInstance();


	/**
	 * Constructor
	 */
    public BaseDAO() {

		super();
	}

    /**
     * DAOFactory으로 부터 daoName으로 지정된 인스턴스를 조회한다.
	 *
     * @param daoName 조회할 서비스의 이름
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
    public static BaseDAO lookupInstance(String daoName) {
        return daoFactory.getDAO(daoName);
    }

    /**
     * Connection 을 닫는다.
	 *
     * @param con Connection
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
    protected static void close(Connection con) throws Exception {
		
			if(con != null)	con.close();
	}

    /**
     * PreparedStatement를 닫는다.
	 *
     * @param pstmt PreparedStatement
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(PreparedStatement pstmt) throws Exception {
		
			if(pstmt != null)	pstmt.close();
	}

    /**
     * Statement를 닫는다.
	 *
     * @param stmt Statement
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(Statement stmt) throws Exception {
		
			if(stmt != null)	stmt.close();
	}

    /**
     * ResultSet을 닫는다.
	 *
     * @param rs ResultSet
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(ResultSet rs) throws Exception {
		
			if(rs != null)	rs.close();
	}

    /**
     * Connection, PreparedResultSet을 닫는다.
	 *
     * @param con Connection
	 * @param pstmt PreparedStatement
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(Connection con, PreparedStatement pstmt) throws Exception {

		close(con, pstmt, null);
	}

    /**
     * Connection, ResultSet을 닫는다.
	 *
     * @param con Connection
	 * @param stmt Statement
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(Connection con, Statement stmt) throws Exception {

		close(con, stmt, null);
	}

    /**
     * Connection, PreparedResultSet, ResultSet을 닫는다.
	 *
     * @param con Connection
	 * @param pstmt PreparedStatement
	 * @param rs ResultSet
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws Exception {

		close(con);
		close(pstmt);
		close(rs);
	}

    /**
     * Connection, ResultSet, ResultSet을 닫는다.
	 *
     * @param con Connection
	 * @param stmt Statement
	 * @param rs ResultSet
	 *
     * @return daoName으로 지정된 DAO 인스턴스
     */
	protected static void close(Connection con, Statement stmt, ResultSet rs) throws Exception {

		close(con);
		close(stmt);
		close(rs);
	}

    /**
     * 현재 Timestamp를 반환한다.
	 *
     * @return Timestamp
     */
	public static java.sql.Timestamp getTimestamp() {

		Calendar cal = Calendar.getInstance();
		java.util.Date date = cal.getTime();

		return new java.sql.Timestamp(date.getTime());
	}

}
