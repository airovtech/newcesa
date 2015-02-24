package com.cesa.dao;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.cesa.common.*;
import com.cesa.db.*;
import java.util.*;

public class AdminDAO extends BaseDAO {

	Logger log = Logger.getLogger(AdminDAO.class);

	public AdminDAO() {
		super();
	}

	/**
     * AdminDAO instance를 생성한다.
     *
     * @return AdminDAO
     */
    public static AdminDAO getInstance() {
        return (AdminDAO)lookupInstance(AdminDAO.class.getName());
    }

	/**
	 * 관리자 sample 페이지 목록
	 *
	 * @param id 사용자ID
	 * @return MemberVO
	 */
	public RowSetMapper adminList() throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("adminList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("admin.adminList"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("adminList() End!");
    		}

    		return db;

		} catch (DBConnectedException dce) {
			throw new DataAccessException(dce.getMessage(), dce);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
			throw new DataAccessException(e.getMessage(), e);
		}
	}
	

	/**
	*
	* 특정 계정이 비밀번호와 일치하는지 확인한다.(관리자 계정 체크)
	*
	* @param adminid, password
	* @return boolean 
	*/
	public String isAdmin(String adminid, String password) {
		String check_result = "";

		if( log.isDebugEnabled() ) {
			//log.debug("isAdmin() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try{
			sbufQuery.append(QueryContext.getInstance().get("admin.checkAdmin"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, adminid);
			db.execute(query);

			if( !db.next() ) {
				// 해당 계정이 없음.
				check_result = "fail_1";
			}
			else {
				if(password.equals(db.getString("password"))){
					// 해당 계정과 비밀번호가 일치하므로,  마지막 로그인 일시 업데이트 한다.
					check_result = "success";
					setAdminLoginUpdate(adminid);

				}
				else {
					// 해당 계정과 비밀번호가 불일치 함.
					check_result = "fail_2";
				}
			}

		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
		}
		

	return check_result;
	}

	/**
	* 관리자의 마지막 로그인 시간을 업데이트한다.
	*
	* @param adminid
	*
	*/
	public void setAdminLoginUpdate(String adminid){
		if( log.isDebugEnabled() ) {
			//log.debug("setAdminLoginUpdate() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {
			sbufQuery.append(QueryContext.getInstance().get("admin.updateAdminLogin"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, adminid);
			db.executeUpdate(query);

		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
		}
	}


	/**
	* 관리자 목록을 조회한다.
	* @param void
	* @return RowSetMapper
	*/
	public RowSetMapper getAdminList(String groupSeq, int pageSize, int currPage){
		if( log.isDebugEnabled() ) {
			//log.debug("getAdminList() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		StringBuffer whereQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("admin.adminList"));

			whereQuery.append("WHERE A.deleteYN = 0 \n");
			if(groupSeq!=null && groupSeq.trim().length()>0){
				whereQuery.append(" AND A.user_group=? \n");
			}


			sbufQuery.replace(sbufQuery.indexOf("$$WHERE$$"), sbufQuery.indexOf("$$WHERE$$")+9, whereQuery.toString());


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString(), pageSize, currPage);

			if(groupSeq!=null && groupSeq.trim().length()>0){
				db.setString(1, groupSeq);
			}

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getAdminList() End");
			}
			return db;
		} catch (DBConnectedException dce) {
			throw new DataAccessException(dce.getMessage(), dce);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * 관리자 상세 정보를 조회한다.
	 * @param String seq
	 * @return RowSetMapper
	 */
	public RowSetMapper getAdminDetail(String seq){
		if( log.isDebugEnabled() ) {
			//log.debug("getAdminDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("admin.adminDetail"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getAdminDetail() End");
			}

			return db;

		} catch (DBConnectedException dce) {
			throw new DataAccessException(dce.getMessage(), dce);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * ID를 조회한다.
	 * @param String id
	 * @return RowSetMapper
	 */
	public RowSetMapper chkAdminId(String id){
		if( log.isDebugEnabled() ) {
			//log.debug("chkAdminId() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("admin.checkAdmin"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, id);
			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("chkAdminId() End");
			}

			return db;

		} catch (DBConnectedException dce) {
			throw new DataAccessException(dce.getMessage(), dce);
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage(), e);
		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
			throw new DataAccessException(e.getMessage(), e);
		}
	}

	/**
	 * admin 관리자를  등록한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean regAdmin(List params){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regAdmin() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("admin.regAdmin"));

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			for(int i = 1; i <= params.size() ; i++){
				db.setString(i, (String)params.get(i-1));
			}
			if(db.executeUpdate(query) == 1){
				check_result = true;
			}


		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				check_result = false;
				log.debug("Exception : " + e);
			}
		}

		return check_result;
	}
	
	/**
	 * 관리자 정보를 수정한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean modAdmin(List params){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("modAdmin() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try {
			if(params.size() == 3){
				sbufQuery.append(QueryContext.getInstance().get("admin.modAdmin1"));
			}
			else{ // password 수정
				sbufQuery.append(QueryContext.getInstance().get("admin.modAdmin2"));
			}


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			for(int i = 1; i <= params.size() ; i++){
				db.setString(i, (String)params.get(i-1));
			}
			if(db.executeUpdate(query) == 1){
				check_result = true;
			}

		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				check_result = false;
				log.debug("Exception : " + e);
			}
		}

		return check_result;
	}

	/**
	 * 관리자 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delAdmin(String seq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delAdmin() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("admin.delAdmin"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delAdmin() End");
			}

		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				check_result = false;
				log.debug("Exception : " + e);
			}
		}
		return check_result;
	}
}
