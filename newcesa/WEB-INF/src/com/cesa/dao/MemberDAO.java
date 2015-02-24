package com.cesa.dao;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.cesa.common.*;
import com.cesa.db.*;
import java.util.*;

public class MemberDAO extends BaseDAO {

	Logger log = Logger.getLogger(MemberDAO.class);

	public MemberDAO() {
		super();
	}

	/**
     * MemberDAO instance를 생성한다.
     *
     * @return MemberDAO
     */
    public static MemberDAO getInstance() {
        return (MemberDAO)lookupInstance(MemberDAO.class.getName());
    }

	/**
	*
	* 특정 계정이 비밀번호와 일치하는지 확인한다.
	*
	* @param memberId, memberPasswd, projectSeq
	* @return String 
	*/
	public String isMember(String memberId , String memberPasswd, String projectSeq) {

		String check_result = "";

		if( log.isDebugEnabled() ) {
			//log.debug("isMember() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try{
			sbufQuery.append(QueryContext.getInstance().get("member.memberCheck"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, memberId);
			db.setString(2, projectSeq);

			db.execute(query);

			if( !db.next()) {
				// 해당 계정이 없음. 등록
				if(regMember(projectSeq, memberId, memberPasswd)){
					check_result = "success";
				}
				else{
					check_result = "fail";
				}
			}
			else {
				if(memberPasswd.equals(db.getString("password"))){
					// 해당 계정과 비밀번호가 일치하므로,  마지막 로그인 일시 업데이트 한다.
					check_result = "success";
					//setMemberLoginUpdate(db.getString("seq"));
				}
				else {
					// 해당 계정과 비밀번호가 불일치 함.
					check_result = "fail";
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
	public void setMemberLoginUpdate(String seq){
		if( log.isDebugEnabled() ) {
			//log.debug("setMemberLoginUpdate() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {
			sbufQuery.append(QueryContext.getInstance().get("member.updateMemberLogin"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);

			db.executeUpdate(query);

		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
		}
	}

	/**
	 * 관리자 상세 정보를 조회한다.
	 * @param String seq
	 * @return RowSetMapper
	 */
	public RowSetMapper getMemberDetail(String seq){
		if( log.isDebugEnabled() ) {
			//log.debug("getMemberDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("member.memberDetail"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getMemberDetail() End");
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
	 * @param String memberId, String memberPasswd, String projectSeq
	 * @return RowSetMapper
	 */
	public RowSetMapper getMemberDetail(String memberId, String memberPasswd, String projectSeq){
		if( log.isDebugEnabled() ) {
			//log.debug("getMemberDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("member.memberDetail2"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, memberId);
			db.setString(2, memberPasswd);
			db.setString(3, projectSeq);

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getMemberDetail() End");
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
	 * member 등록한다.
	 * @param String memberId, String memberPasswd, String projectSeq
	 * @return boolean 
	 */
	public boolean regMember(String projectSeq, String memberId, String memberPasswd){

		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regMember() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("member.regMember"));

			RowSetMapper db = new RowSetMapper();

			QueryManager query = new QueryManager(sbufQuery.toString());
			
			db.setString(1, projectSeq);
			db.setString(2, memberId);
			db.setString(3, memberPasswd);
			
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
	 * 체크된 내용을 저장한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean regMemberCheckValue(List params){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regMemberCheckValue() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("member.regMemberCheckValue"));



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
	 * 회원이 체크한 내용을 가져온다.
	 * @param String memberId, String activity, String projectSeq
	 * @return RowSetMapper
	 */
	public RowSetMapper getMemberCheckValueDetail(String memberId, String activity, String projectSeq){
		if( log.isDebugEnabled() ) {
			//log.debug("getMemberCheckValueDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("member.memberCheckValueList"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			
			db.setString(1, projectSeq);
			db.setString(2, activity);
			db.setString(3, memberId);

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getMemberCheckValueDetail() End");
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
	 * 체크된 내용을 삭제한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean delMemberCheckValue(String memberId, String activity, String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delMemberCheckValue() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("member.delMemberCheckValue"));

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());


			db.setString(1, projectSeq);
			db.setString(2, activity);
			db.setString(3, memberId);

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
	 * 회의 프로퍼티를 가져온다.
	 * @param String projectSeq
	 * @return RowSetMapper
	 */
	public RowSetMapper getMemberPropertiesList( String projectSeq){
		if( log.isDebugEnabled() ) {
			//log.debug("getMemberPropertiesList() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("member.memberPropertiesList"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			
			db.setString(1, projectSeq);

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getMemberPropertiesList() End");
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
	 * 회의 프로퍼티를 저장한다.
	 * @param String projectSeq
	 * @return boolean
	 */
	public boolean regMemberProperties(List params){

		boolean check_result = false;

		if( log.isDebugEnabled() ) {
			//log.debug("regMemberProperties() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {

			sbufQuery.append(QueryContext.getInstance().get("member.regMemberProperties"));

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
	 * 회의 프로퍼티를 저장한다.
	 * @param String memberid, String projectSeq
	 * @return boolean
	 */
	public boolean delMemberProperties(String memberid, String projectSeq){

		boolean check_result = false;

		if( log.isDebugEnabled() ) {
			//log.debug("delMemberProperties() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {

			sbufQuery.append(QueryContext.getInstance().get("member.delMemberProperties"));

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);
			db.setString(2, memberid);

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
	 * 체크된 내용을 삭제한다.
	 * @param ArrayList params
	 * @return int 
	 */
	public int getMemberCheckedStatPoint(String projectSeq, String memberid, String activity, String word){
		int result = 0;
		if( log.isDebugEnabled() ) {
			//log.debug("getMemberCheckedStatPoint() Start");
		}

		int memberLength = 0;
		
		StringBuffer sbufQuery = new StringBuffer();
		StringBuffer whereQuery = new StringBuffer();

		int index = 1;
		try {



			sbufQuery.append(QueryContext.getInstance().get("member.memberCheckedStatPoint"));

			whereQuery.append("WHERE project_seq=? AND activity=? AND word=? \n");
			if(memberid!=null && memberid.trim().length()>0){
				
				memberLength = memberid.split(",").length;
				whereQuery.append(" AND memberid In (");
				for(int i=0;i<memberLength;i++){
					if(i>0){
						whereQuery.append(", ");
					}
					whereQuery.append("?");
				}
				whereQuery.append(") \n");

			}


			sbufQuery.replace(sbufQuery.indexOf("$$WHERE$$"), sbufQuery.indexOf("$$WHERE$$")+9, whereQuery.toString());

			//log.debug(sbufQuery);

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());


			db.setString(index++, projectSeq);
			db.setString(index++, activity);
			db.setString(index++, word);

			if(memberid!=null && memberid.trim().length()>0){
				for(int i=0;i<memberLength;i++){
					//whereQuery.append("?");
					db.setString(index++, memberid.split(",")[i]);
				}
			}


			db.execute(query);

			if(db.next()){
				result = db.getInt(1);
			}



		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				log.debug("Exception : " + e);
			}
		}

		return result;
	}


	/**
	 * 기타 점수를 가져온다.
	 * @param String seq
	 * @return RowSetMapper
	 */
	public RowSetMapper getMemberCheckedEtcStatPoint(String projectSeq, String activity){
		if( log.isDebugEnabled() ) {
			//log.debug("getMemberCheckedEtcStatPoint() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("member.memberCheckedEtcStatPoint"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);
			db.setString(2, activity);

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getMemberCheckedEtcStatPoint() End");
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


}
