/*============================================================================
 * Copyright(c) 2007 Dntech7
 * 
 * @BusinessType : Common
 * @File : QueryManager.java
 * @FileName : Query Handling 
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

import java.io.*;
import java.sql.*;
import com.cesa.common.*;
import javax.servlet.http.*;


public class QueryManager {

	private String sql;
	private Object[] parameters;
 
	private boolean bPage;


	// 쿼리된 자료의 수가 전체 몇페이지인지..
	private int maxPageSize;
	private int maxRowSize;

	// 한페이지에 출력할 자료의수
	private int pageSize;

	// 몇번째 페이지를 출력할것인지;
	private int currPage;

    /**
     * Constructor
	 *
	 * @param sql 쿼리
     */
	public QueryManager(String sql) {

		setQueryManager(sql, -1, -1);
	}

    /**
     * Constructor
	 *
	 * @param sql 쿼리
	 * @param pageSize 한페이지에 포함될 갯수
	 * @param currPage 현재 페이지
     */
	public QueryManager(String sql, int pageSize, int currPage) {

		setQueryManager(sql, pageSize, currPage);
	}

    /**
     * Constructor
	 *
	 * @param sql 쿼리
	 * @param pageSize 한페이지에 포함될 갯수
	 * @param request HttpServletRequest
     */
	public QueryManager(String sql, int pageSize, HttpServletRequest request) {

		setQueryManager(sql, pageSize, this.getCurrentPage(request));
	}

    /**
     * 현재 페이지를 반환
	 *
	 * @param request HttpServletRequest
	 * @return int
     */
	private int getCurrentPage(HttpServletRequest request) {
		return request.getParameter("currentPage")	==	null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
	}

    /**
     * 쿼리를 설정한다.
	 *
	 * @param sql 쿼리
     */
	public void setQueryManager(String sql) {

		setQueryManager(sql, -1, -1);
	}

    /**
     * 변수를 설정한다.
	 *
	 * @param sql 쿼리
	 * @param pageSize 한페이지에 포함될 갯수
	 * @param request HttpServletRequest
     */
	public void setQueryManager(String sql, int pageSize, HttpServletRequest request) {

		setQueryManager(sql, pageSize, this.getCurrentPage(request));
	}

    /**
     * 변수를 설정한다.
	 *
	 * @param sql 쿼리
	 * @param pageSize 한페이지에 포함될 갯수
	 * @param currPage 현재 페이지
     */
	public void setQueryManager(String sql, int pageSize, int currPage) {

		init();

		this.pageSize = pageSize;
		this.currPage = currPage;
		if(pageSize > 0) {
			this.bPage = true;
		}

		this.sql = sql;

		this.parameters = null;
	}

    /**
     * 변수를 초기화한다.
     */
	public void init() {

		this.sql = "";
		this.parameters = null;

		this.bPage = false;

		this.maxPageSize = -1;
		this.maxRowSize = 0;
		// 한페이지에 출력할 자료의수
		this.pageSize = -1;

		// 몇번째 페이지를 출력할것인지;
		this.currPage = -1;
	}

    /**
     * 인자 갯수를 반환한다.
	 *
	 * @return int
     */
	public int getParameterSize() {
		return this.parameters == null ? 0 : this.parameters.length;
	}

    /**
     * 인자를 반환한다.
	 *
	 * @param idx 인자 번호
	 * @return Object
     */
	public Object getParameter(int idx) throws Exception {

		if(idx >= this.getParameterSize())
			throw new Exception("INDEX값이 파라미터의 개수보다 큼니다.");

		return this.parameters[idx];
	}

    /**
     * 인자를 설정한다.
	 *
	 * @param obj 인자 리스트
     */
	public void setParameters(Object[] obj) {
		this.parameters = obj;
	}

    /**
     * 쿼리를 반환한다.
	 *
	 * @return String
     */
	public String getSql() {
		return this.sql;
	}

    /**
     * 쿼리를 출력한다.
	 *
	 * @return String
     */
	public String print() {

		String sqlStr = sql;
		StringBuffer buf = new StringBuffer();
		Object oVec = null;

		try {
		
			for(int i, seq = 0; (i = sqlStr.indexOf("?")) >= 0; seq++) {
				buf.append(sqlStr.substring(0, i));
				oVec = getParameter(seq);
				if(oVec instanceof String)
					buf.append("'"+oVec.toString()+"'");
				else
					buf.append(oVec.toString());
				sqlStr = sqlStr.substring(i + "?".length());
			}
			buf.append(sqlStr);

			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}


    /**
     * String 객체로 만든다.
	 *
	 * @return String
     */
	public String toString() {

		try {
			StringBuffer strBuff = new StringBuffer();

			strBuff.append("QueryManager SQL : ").append(this.sql).append("\n");

			if(parameters != null) {
				strBuff.append("QueryManager Parameters : [");
				for(int i = 0; i < parameters.length; i++) {
					strBuff.append(parameters[i].toString()).append(", ");
				}
				return strBuff.toString().substring(0, strBuff.toString().lastIndexOf(",")) + "]\n";
			} else
				return strBuff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

    /**
     * page 타입인지 반환한다.
	 *
	 * @return boolean
     */
	public boolean isPage() {
		return bPage;
	}

	/**
	 * currPage을 리턴합니다.
	 *
	 * @return int
	 */
	public int getCurrPage() {
		return currPage;
	}

	/**
	 * 현재페이지를 설정한다.
	 * 
	 * @param currPage 설정하려는 currPage입니다.
	 */
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	/**
	 * @return pageSize을 리턴합니다.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize 설정하려는 pageSize입니다.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @param sql 설정하려는 sql입니다.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return maxPageSize을 리턴합니다.
	 */
	public int getMaxPageSize() {
		return maxPageSize;
	}

	/**
	 * @param maxPageSize 설정하려는 maxPageSize입니다.
	 */
	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	/**
	 * @return maxRowSize을 리턴합니다.
	 */
	public int getMaxRowSize() {
		return maxRowSize;
	}

	/**
	 * @param maxRowSize 설정하려는 maxRowSize입니다.
	 */
	public void setMaxRowSize(int maxRowSize) {
		this.maxRowSize = maxRowSize;
	}

	/**
	 * 페이지 쿼리를 반환한다.
	 *
	 * @return String
	 */
	public String getPageQuery() {

		if(this.getSql().equals("")) return "";

		/*
		return new StringBuffer()
							.append("select * from ")
							.append("		(select sub_query.*, ceil(rownum/").append(this.getPageSize()).append(") page from ")
							.append("			( ").append(this.getSql()).append("			) sub_query ")
							.append("		) ")
							.append("where page = ").append(this.getCurrPage())
						.toString();
		*/
		String newQuery = new StringBuffer()
							.append(this.getSql())
							.append(" limit "+((this.getCurrPage()-1) * this.getPageSize()))
							.append(" , "+this.getPageSize())
						.toString();

		//System.out.println(newQuery);

		return newQuery;
	}

	/**
	 * split 쿼리를 반환한다.
	 *
	 * @return String
	 */
	public String getQuerySplitFrom() {

		int selectIdx = 0;
		int fromIdx = 0;
		int idx = 0;
		int pos = 0;
		String orgString = this.getSql().trim();					// 원본 문자열
		String tmpString = this.getSql().toLowerCase().trim();	// 소문자로 변환한 문자열

		if(tmpString.equals("")) return "";

		while(idx < tmpString.length()) {
			selectIdx = tmpString.indexOf("select");
			fromIdx = tmpString.indexOf("from");
			if(selectIdx < fromIdx) {
				pos++;
				if(selectIdx == -1)
					idx = fromIdx + 4;
				else idx = selectIdx + 6;
			} else {
				pos--;
				idx = fromIdx + 4;
			}

			tmpString = tmpString.substring(idx);
			orgString = orgString.substring(idx);

			if(selectIdx == -1) {
				fromIdx = tmpString.indexOf("from");
				if(fromIdx > 0)
				{
					tmpString = tmpString.substring(fromIdx+4);
					orgString = orgString.substring(fromIdx+4);
				}
				break;
			}

			if(pos == 0) {
				break;
			}
		}
		tmpString = "from" + tmpString;
		orgString = "from" + orgString;

		int wherePos = tmpString.lastIndexOf("where ");
		if(wherePos==-1)		// 없는 경우 where + tab 으로 찾는다.
			wherePos = tmpString.lastIndexOf("where	");

		String tmpWhere = "";
		if(wherePos!=-1)
			tmpWhere = tmpString.substring(wherePos);


		if(tmpWhere.indexOf("order ") > 0)
			return orgString.substring(0, tmpString.indexOf("order "));
//			return tmpString.substring(0, tmpString.indexOf("order "));
		else
			return orgString;
//			return tmpString;

	}
}
