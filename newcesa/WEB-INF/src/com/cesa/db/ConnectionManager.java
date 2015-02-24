
/*============================================================================
 * File Name : ConnectionManager.java
 * package   : com.adlinker.common.db
 * Desc      : DB Connection Manager
 * Auther    : 김효원
 * Date      : 2007/11/23 최초작성
 * Copyright (c) 2007 dnt7.com. All Rights Reserved.
 *
 * 수정내역
 *
 ============================================================================*/

package com.cesa.db;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.rowset.*;
import org.apache.log4j.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.cesa.common.*;


public class ConnectionManager {


	private static Logger log = Logger.getLogger(ConnectionManager.class);


    private static InitialContext initialContext;
	private static Map cache;

	static {

		try {
			initialContext = new InitialContext();
			cache = Collections.synchronizedMap(new HashMap());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Constructor
	 */
	private ConnectionManager() {

	}

	/**
	 * DataSource를 반환한다.
	 *
	 * @return DataSource
	 */
	public static DataSource getDataSource() throws DBConnectedException {
		SiteContext sc = SiteContext.getInstance();

		DataSource dataSource = null;
		try {
			if(cache.containsKey(sc.get("datasource.cesa"))) {
				dataSource = (DataSource) cache.get(sc.get("datasource.cesa"));
			} else {
				dataSource = (DataSource) initialContext.lookup(sc.get("datasource.cesa"));
				cache.put(sc.get("datasource.cesa"), dataSource);
			}
		} catch (NamingException en) {
			en.printStackTrace();
		}

		return dataSource;
	}

	/**
	 * Connection를 반환한다.
	 *
	 * @return Connection
	 */
	public static Connection getConnection() throws DBConnectedException {

		try {
			return getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBConnectedException(e.getMessage(), e);
		}

	}

	/**
	 * Connection을 닫는다.
	 *
	 * @param con Connection
	 */
	public static void close(Connection con) throws DBConnectedException {
		try {
			if(con != null)	con.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * PreparedStatement를 닫는다.
	 *
	 * @param pstmt PreparedStatement
	 */
	public static void close(PreparedStatement pstmt) throws DBConnectedException {
		try {
			if(pstmt != null)	pstmt.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * Statement를 닫는다.
	 *
	 * @param stmt Statement
	 */
	public static void close(Statement stmt) throws DBConnectedException {
		try {
			if(stmt != null)	stmt.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * ResultSet을 닫는다.
	 *
	 * @param rs ResultSet
	 */
	public static void close(ResultSet rs) throws DBConnectedException {
		try {
			if(rs != null)	rs.close();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * Connection, PreparedStatement를 닫는다.
	 *
	 * @param con Connection
	 * @param pstmt PreparedStatement
	 */
	public static void close(Connection con, PreparedStatement pstmt) throws DBConnectedException {

		close(con, pstmt, null);
	}

	/**
	 * Connection, Statement를 닫는다.
	 *
	 * @param con Connection
	 * @param stmt Statement
	 */
	public static void close(Connection con, Statement stmt) throws DBConnectedException {

		close(con, stmt, null);
	}

	/**
	 * Connection, PreparedStatement, ResultSet을 닫는다.
	 *
	 * @param con Connection
	 * @param pstmt PreparedStatement
	 * @param rs ResultSet
	 */
	public static void close(Connection con, PreparedStatement pstmt, ResultSet rs) throws DBConnectedException {

		close(con);
		close(pstmt);
		close(rs);
	}

	/**
	 * Connection, Statement, ResultSet을 닫는다.
	 *
	 * @param con Connection
	 * @param stmt Statement
	 * @param rs ResultSet
	 */
	public static void close(Connection con, Statement stmt, ResultSet rs) throws DBConnectedException {

		close(con);
		close(stmt);
		close(rs);
	}

}
