package com.cesa.db;

import java.io.*;
import java.sql.*;
import java.util.*;
import com.cesa.common.*;
import com.cesa.util.*;
import javax.servlet.http.*;
import net.sf.json.*;
import org.apache.log4j.Logger;


public class QueryCreator {
	
	Logger log = Logger.getLogger(QueryCreator.class);

	public static int INSERT = 1;
	public static int UPDATE = 2;

	// datetime field는 null update 를 위하여 별도로 관리
	private static String [] DATETIME_FILEDS = {"END_DATE", "ALARM_DATE", "COMPLETE_DATE"};

	int type = 0;
	String table = null;

	ArrayList keyNames = null;
	ArrayList keyValues = null;

	ArrayList names = null;
	ArrayList values = null;

	String whereQuery = null;

	public QueryCreator(int type, String table, JSONObject json)
	{
		this.type = type;
		this.table = table;

		keyNames = new ArrayList();
		keyValues = new ArrayList();

		names = new ArrayList();
		values = new ArrayList();
			
		Iterator i = json.keySet().iterator();

		String name = null;
		String value = null;

		while(i.hasNext()) {

			name = (String)i.next();
			value = json.get(name).toString();

			// value 에 값이 있을때만 처리 (datetime필드를 따로 처리하면서 무조건 추가)
			// int 값에 ''이 들어오면 문제가 생길 수 있음
			if(value.length()>=0) {
				names.add(name);

				if(name.endsWith("_DATE"))
					values.add(WebUtil.convertDate(value));
				else
					values.add(value);
			}
		}
	}

	public void addKey(String name, String value)
	{
		keyNames.add(name);
		keyValues.add(value);

		try {
			// 키로 추가된 경우 field에서 삭제한다.
			delete(name);
		} catch(Exception e) {}
	}

	// where를 직접 지정하는 경우 
	// 이 경우 addKey에서 입력된 값은 Binding에만 사용한다.
	public void setWhereQuery(String whereQuery) {

		this.whereQuery = whereQuery;
	}

	public void add(String name, String value)
	{
		names.add(name);
		values.add(value);
	}

	public RowSetMapper getRowSetMapper() throws Exception
	{
		RowSetMapper db = new RowSetMapper();

		int realIdx = 0;
		int idx = 0;
		for(idx=0; idx<values.size(); idx++) {

			// 필드가 datetime 이면서 값이 없으면 binding을 하지 않음
			if(isDatetime((String)names.get(idx)) && ((String)values.get(idx)).length()==0) {

				//log.debug("field ("+names.get(idx)+") is datetime, no binding, set null");

			} else {
				db.setString(realIdx+1, (String)values.get(idx));
				//log.debug("index : "+(realIdx+1) + " ("+(String)values.get(idx)+")");
				realIdx++;
			}

		}

		if(type==UPDATE) {

			for(int i=0; i<keyValues.size(); i++) {
				db.setString(realIdx+1, (String)keyValues.get(i));
				realIdx++;
			}
		}

		return db;
	}


	public String getQuery()
	{
		if(type==INSERT)
			return _getInsertQuery();
		else if(type==UPDATE)
			return _getUpdateQuery();

		return null;
	}

	private String _getInsertQuery() 
	{
		
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ");
		sql.append(table);
		sql.append(" (");

		int realIdx = 0;		// datetime필드는 무시하기 때문에 별도로 count를 처리
		for(int i=0; i<names.size(); i++) {


			// datetime 필드이고 값이 빈 경우 insert 하지 않음
			if(isDatetime((String)names.get(i)) && ((String)values.get(i)).length()==0) {

			} else {
				if(realIdx>0) sql.append(",");
				sql.append(names.get(i));
				realIdx++;
			}

		}

		sql.append(") VALUES (");
		
		realIdx = 0;
		for(int i=0; i<values.size(); i++) {

			
			// datetime 필드이고 값이 빈 경우 insert 하지 않음
			if(isDatetime((String)names.get(i)) && ((String)values.get(i)).length()==0) {

			} else {
				if(realIdx>0) sql.append(",");
				sql.append(" ? ");
				realIdx++;
			}


		}

		sql.append(")");

		return sql.toString();

	}

	private String _getUpdateQuery()
	{
		if(keyNames.size()==0)
			return null;
		
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ");
		sql.append(table);
		sql.append(" SET ");

		for(int i=0; i<names.size(); i++) {

			if(i>0) sql.append(",");
			
			// datetime 필드이고 값이 빈 경우 insert 하지 않음
			if(isDatetime((String)names.get(i)) && ((String)values.get(i)).length()==0) {
				//log.debug("------------------ query is null setting");
				sql.append(names.get(i) + " = null ");
			} else {
				sql.append(names.get(i) + " = ? ");
			}

		}


		sql.append(" WHERE ");
		if(whereQuery==null) {			// where 구문이 직접 정의되지 않은 경우

			for(int i=0; i<keyNames.size(); i++) {
				if(i>0) sql.append(" AND ");
				sql.append(keyNames.get(i) + " = ? ");
			}
		} else {						// where 구문이 직접 정의된 경우
			
			sql.append(whereQuery);
		}
		
		//log.debug("----------"+sql.toString());

		return sql.toString();
	}

	public void delete(String name) throws Exception
	{
		int idx = names.indexOf(name);

		if(idx==-1)
			throw new Exception("QureyCreator delete field not exist");

		names.remove(idx);
		values.remove(idx);

	}

	// field가 datetime 인지 반환
	private boolean isDatetime(String field) {

		for(int i=0; i<DATETIME_FILEDS.length; i++) {
			if(field.equals(DATETIME_FILEDS[i]))
				return true;
		}

		return false;
	}
}

