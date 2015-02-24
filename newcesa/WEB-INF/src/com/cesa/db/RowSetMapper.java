/*============================================================================
 * Copyright(c) 2007 Dntech7
 * 
 * @BusinessType : Common
 * @File : RowSetMapper.java
 * @FileName : CachedRowSet을 확장하여 Mapping 한 클래스
 *
 * Note:
 *
 * Change history
 * @LastModifyDate : 200703092007-04-13
 * @LastModifier   : Dntech7
 * @LastVersion    : 1.0
 *   2007-03-09 Dntech7
 *      1.0    최초생성 
 ============================================================================*/
package com.cesa.db;

import java.io.*;
import java.sql.*;
import org.apache.log4j.Logger;
import com.sun.rowset.*;
import com.cesa.common.*;
import java.util.*;

public class RowSetMapper extends CachedRowSetImpl {

	private static Logger log = Logger.getLogger(RowSetMapper.class);

	private QueryManager queryManager;

	private Connection tConn = null;

	private boolean setCommit;

	/**
	 * Constructor
	 */
	public RowSetMapper() throws Exception {

		super();

		this.queryManager = null;
		this.setCommit = true;

	}
	
	/**
	 * Constructor
	 */
	public RowSetMapper(Connection conn) throws Exception {
		super();

		this.tConn = conn;
		this.queryManager = null;
		this.setCommit = true;

	}

	/**
	 * Query를 실행한다.
	 *
	 * @param queryManager QueryManager
	 */
	public void execute(QueryManager queryManager) throws SQLException{
		
		this.queryManager = queryManager;
		this.execute();
	}

	/**
	 * Query를 실행한다.
	 *
	 * @param queryManager QueryManager
	 */
	public void executeStatement(QueryManager queryManager) throws Exception{
		this.queryManager = queryManager;

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
 
		try {

       		con = ConnectionManager.getConnection();
       		stmt = con.createStatement();

			String strQuery = "";
			if(this.queryManager == null)
				strQuery = this.getCommand();
			else	 {
				strQuery = this.queryManager.getSql();
				if(this.queryManager.isPage())
					strQuery = this.queryManager.getPageQuery();
			}

			//ClassLogWriter.log("query", strQuery);
			rs = stmt.executeQuery(strQuery);

       		this.populate(rs);

		} catch (Exception e) {
			throw e;
		} finally {

			if(rs !=null)  try { rs.close(); }catch(Exception e) {}
			if(stmt !=null)  try { stmt.close(); }catch(Exception e) {}
			if(con !=null)  try { con.close(); }catch(Exception e) {}

		}

	}


	/**
	 * Clob Field 를 update 한다. (기존버전)
	 *
	 * @param query query
	 * @param data data
	 */
	public void executeClobUpdate(String query, String data) throws Exception{
		this.queryManager = queryManager;

		Connection con = null;
		PreparedStatement pstmt = null;


       	
		try {
			con = ConnectionManager.getConnection();
			pstmt = con.prepareStatement(query);
			
			StringReader sr = new StringReader(data);
			pstmt.setCharacterStream(1, sr, data.length());

			//ClassLogWriter.log("query", query);
			pstmt.executeUpdate();


		} catch (Exception ex){
			throw ex;
		} finally {
			if(pstmt !=null)  try {pstmt.close(); }catch(Exception e) {}
			if(con !=null)  try {con.close(); }catch(Exception e) {}
		}

	}
	/**
	 * Reset한다.
	 */
	public void reset() {
		queryManager = null;

	
		try {
			clearParameters();
		} catch (Exception e) {}
	}

	/**
	 * Connection을 맺는다.
	 */
    private Connection connect() throws SQLException{
		return ConnectionManager.getConnection();
    }

	/**
	 * Query를 실행한다.
	 */
    public void execute() throws SQLException{

		Connection conn = null;

		try {
			if(tConn==null){
				conn = connect();
			}
			else{
				conn = tConn;
			}

			if (conn == null) {
				throw new SQLException("RowSetMapper execute : DBConnect faild");
			}

			this.execute(conn);

		} catch (SQLException se) {
			throw se;
		} finally {
			if ( conn != null )
			{
				try {
					conn.close(); 
				} catch(Exception e) {}
				conn = null;
			}
		}
	}

	/**
	 * MaxPage를 설정한다.
	 *
	 * @param conn Connection
	 */
	private void setMaxPage(Connection conn) throws SQLException{

		if(this.queryManager == null)	return;
		if(!this.queryManager.isPage()) return;

		if (this.queryManager.getPageSize() < 0) {
			this.queryManager.setMaxRowSize(this.size());
			return;
		}

		PreparedStatement pstmt = null;
		ResultSet result = null;
		String strQuery = "";
		
		try {

			if (conn == null) {
				return;
			}

			strQuery = new StringBuffer()
											.append("select ceil(count(*)/")
											.append(this.queryManager.getPageSize())
											.append("), count(*) ")
											.append(this.queryManager.getQuerySplitFrom())
										.toString();

			pstmt = conn.prepareStatement(strQuery);

            if(strQuery.indexOf("?") != -1) {
				Object[] parameters = this.getParams();
				for(int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i+1, parameters[i]);
				}
            }

			//ClassLogWriter.log("query", strQuery);
			result = pstmt.executeQuery();

			if(result.next()) {

				this.queryManager.setMaxPageSize(result.getInt(1));
				this.queryManager.setMaxRowSize(result.getInt(2));
			}

			return;

		} catch (SQLException se) {
			throw se;
		} finally {
			if ( result != null )
			{
				try { result.close(); } catch(Exception e) {}
				result = null;
			}
			if ( pstmt != null )
			{
				try { pstmt.close(); } catch(Exception e) {}
				pstmt = null;
			}
		}
	}

	/**
	 * Query를 실행한다.
	 *
	 * @param conn Connection
	 */
	public void execute(Connection conn) throws SQLException{

		close();

		PreparedStatement pstmt = null;
		ResultSet result = null;

		
		try {


			String strQuery = "";
			if(this.queryManager == null)
				strQuery = this.getCommand();
			else	 {
				strQuery = this.queryManager.getSql();
				if(this.queryManager.isPage())
					strQuery = this.queryManager.getPageQuery();
			}

			pstmt = conn.prepareStatement(strQuery);

            if(strQuery.indexOf("?") != -1) {
    			Object[] parameters = this.getParams();
    			this.queryManager.setParameters(this.getParams());

				for(int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i+1, parameters[i]);
				}
            }

			//ClassLogWriter.log("query", strQuery);
			result = pstmt.executeQuery();

			this.populate(result);
		
			if(this.queryManager != null && this.queryManager.isPage())
			{
				this.setMaxPage(conn);
			}

		} catch (SQLException se) {
			throw se;
		} finally {

			if ( result != null ) {
				try { result.close(); } catch(Exception rse) {}
				result = null;
			}

			if ( pstmt != null ) {
				try { pstmt.close(); } catch(Exception ste) {}
				pstmt = null;
			}

		}

	}

	/**
	 * Update를 실행한다.
	 *
	 * @return int
	 */
	public int executeUpdate()  throws Exception {

		close();

		if(queryManager == null)
			throw new SQLException("QueryManager 가 정의되지 않았습니다.");

		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {

			if(tConn==null)
				conn = connect();
			else
				conn = tConn;

			pstmt = conn.prepareStatement(this.queryManager.getSql());

            if(this.queryManager.getSql().indexOf("?") != -1) {
				Object[] parameters = this.getParams();
				this.queryManager.setParameters(parameters);
				for(int i = 0; i < parameters.length; i++) {

					if(parameters[i] instanceof String) {
						String strVal = (String)parameters[i];
						if(strVal.length() < 500)
							pstmt.setString(i+1, strVal);
						else {
							StringReader sr = new StringReader(strVal);
			        		pstmt.setCharacterStream(i+1, sr, strVal.length());
			        	}

					} else {

						if(parameters[i]!=null){
							pstmt.setObject(i+1, parameters[i]);
						}
						else{
							pstmt.setObject(i+1, "");
						}
					}


				}
			}

			//ClassLogWriter.log("query", this.queryManager.getSql());
			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
            throw e;
		} finally {
			if ( pstmt != null )
			{
				try { pstmt.close(); } catch(Exception e) {}
				pstmt = null;
			}
			if ( conn != null && tConn==null)
			{
				try { conn.close(); } catch(Exception e) {}
				conn = null;
			}
		}

		return cnt;
	}

	/**
	 * Update를 실행한다.
	 *
	 * @param queryManager QueryManager
	 * @return int
	 */
	public int executeUpdate(QueryManager queryManager) throws Exception {

		this.queryManager = null;
		this.queryManager = queryManager;

		return executeUpdate();
	}

	/**
	 * 출력한다.
	 *
	 * @return String
	 */
	public String print()
	{
		if(this.queryManager == null)
			return this.getCommand();
		else
			return this.queryManager.print();
	}

	/**
	 * queryManager 리턴합니다.
	 *
	 * @return QueryManager
	 */
	public QueryManager getQueryManager() {
		return queryManager;
	}

	/**
	 * QueryManager를 설정한다.
	 *
	 * @param queryManager 설정하려는 queryManager.
	 */
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public String getString(String columnName) throws java.sql.SQLException
	{
		String value = super.getString(columnName);

		if(value==null)
			value = "";

		return value;
	}
}
