package com.cesa.dao;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.cesa.common.*;
import com.cesa.db.*;
import java.util.*;
import java.sql.*;

public class VocabularyDAO extends BaseDAO {

	Logger log = Logger.getLogger(VocabularyDAO.class);

	public VocabularyDAO() {
		super();
	}

	/**
     * VocabularyDAO instance를 생성한다.
     *
     * @return VocabularyDAO
     */
    public static VocabularyDAO getInstance() {
        return (VocabularyDAO)lookupInstance(VocabularyDAO.class.getName());
    }

	/**
	 * sample 페이지 목록
	 *
	 * @param id 사용자ID
	 * @return MemberVO
	 */
	public RowSetMapper vocabularyList() throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("vocabularyList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("vocabulary.vocabularyList"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("vocabularyList() End!");
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
	* 목록을 조회한다.
	* @param void
	* @return RowSetMapper
	*/
	public RowSetMapper getVocabularyList(String groupSeq){
		if( log.isDebugEnabled() ) {
			//log.debug("getVocabularyList() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		StringBuffer whereQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.vocabularyList"));
			
			whereQuery.append(" WHERE A.deleteYN=0 \n");
			if(groupSeq!=null && groupSeq.trim().length()>0){
				whereQuery.append(" AND A.user_group=? \n");
			}

			sbufQuery.replace(sbufQuery.indexOf("$$WHERE$$"), sbufQuery.indexOf("$$WHERE$$")+9, whereQuery.toString());

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			if(groupSeq!=null && groupSeq.trim().length()>0){
				db.setString(1, groupSeq);
			}

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getVocabularyList() End");
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
	 * 상세 정보를 조회한다.
	 * @param String seq
	 * @return RowSetMapper
	 */
	public RowSetMapper getVocabularyDetail(String seq){
		if( log.isDebugEnabled() ) {
			//log.debug("getVocabularyDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.vocabularyDetail"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getVocabularyDetail() End");
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
	 * word를 조회한다.
	 * @param String id
	 * @return RowSetMapper
	 */
	public RowSetMapper chkVocabulary(String groupSeq, String word){
		if( log.isDebugEnabled() ) {
			//log.debug("chkVocabulary() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.checkVocabulary"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, groupSeq);
			db.setString(2, word);
			db.execute(query);

			if( log.isDebugEnabled() ) {
				//log.debug("chkVocabulary() End");
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
	 * word를 등록한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean regVocabulary(List params){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regVocabulary() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("vocabulary.regVocabulary"));

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
	 * 정보를 수정한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean modVocabulary(List params){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("modVocabulary() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("vocabulary.modVocabulary"));

			//log.debug(sbufQuery.toString());

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			for(int i = 1; i <= params.size() ; i++){
				db.setString(i, (String)params.get(i-1));
				//log.debug("ddd : "+(String)params.get(i-1));
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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delVocabulary(String seq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delVocabulary() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.delVocabulary"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delVocabulary() End");
			}

		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				check_result = false;
				log.debug("Exception : " + e);
			}
		}
		return check_result;
	}
	

	/**
	* 목록을 조회한다.
	* @param void
	* @return RowSetMapper
	*/
	public RowSetMapper getGroupList(String groupSeq){
		if( log.isDebugEnabled() ) {
			//log.debug("getVocabularyList() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		StringBuffer whereQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.groupList"));
			
			whereQuery.append(" WHERE A.deleteYN=0 \n");
			if(groupSeq!=null && groupSeq.trim().length()>0){
				whereQuery.append(" AND A.user_group=? \n");
			}

			sbufQuery.replace(sbufQuery.indexOf("$$WHERE$$"), sbufQuery.indexOf("$$WHERE$$")+9, whereQuery.toString());

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			if(groupSeq!=null && groupSeq.trim().length()>0){
				db.setString(1, groupSeq);
			}

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getVocabularyList() End");
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
	 * 상세 정보를 조회한다.
	 * @param String seq
	 * @return RowSetMapper
	 */
	public RowSetMapper getGroupDetail(String seq){
		if( log.isDebugEnabled() ) {
			//log.debug("getGroupDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.groupDetail"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getGroupDetail() End");
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
	 * word 그룹을 등록한다.
	 * @param String groupName, String groupSeq
	 * @return String 
	 */
	public String regGroup(String groupName, String groupSeq){
		boolean check_result = false;
		String lastInsertId = null;

		//lastInsert ID 를 가져오기위해 conn 을 여기서 가져온다.
		Connection con = ConnectionManager.getConnection();

		if( log.isDebugEnabled() ) {
			//log.debug("regGroup() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("vocabulary.regGroup"));

			RowSetMapper db = new RowSetMapper(con);
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, groupName);
			db.setString(2, groupSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;

				sbufQuery = new StringBuffer();
				sbufQuery.append(QueryContext.getInstance().get("vocabulary.regGroupLastInsertId"));
				db = new RowSetMapper(con);
				query = new QueryManager(sbufQuery.toString());

				db.execute(query);
				if(db.next()){
					log.debug("----- seq : "+db.getString(1));

					lastInsertId = db.getString(1);
				}
			}

		} catch(Exception e){
			if( log.isDebugEnabled() ) {
				check_result = false;
				log.debug("Exception : " + e);
			}
		}

		return lastInsertId;
	}


	/**
	 * 정보를 수정한다.
	 * @param String groupName, String groupSeq
	 * @return boolean 
	 */
	public boolean modGroup(String groupName, String groupSeq, String seq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("modGroup() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("vocabulary.modGroup"));

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, groupName);
			db.setString(2, groupSeq);
			db.setString(3, seq);

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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delGroup(String seq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delGroup() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.delGroup"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delGroup() End");
			}

		} catch (Exception e) {
			if( log.isDebugEnabled() ) {
				check_result = false;
				log.debug("Exception : " + e);
			}
		}
		return check_result;
	}
	


	/**
	* 목록을 조회한다.
	* @param void
	* @return RowSetMapper
	*/
	public RowSetMapper getGroupSubList(String vocabularyGroupSeq){
		if( log.isDebugEnabled() ) {
			//log.debug("getGroupSubList() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.groupSubList"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());


			db.setString(1, vocabularyGroupSeq);

			db.execute(query);
			if( log.isDebugEnabled() ) {
				//log.debug("getGroupSubList() End");
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
	 * 등록한다.
	 * @param String groupName, String groupSeq
	 * @return boolean 
	 */
	public boolean regGroupSub(String vocabularySeq, String vocabularyGroupSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regGroupSub() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("vocabulary.regGroupSub"));

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, vocabularySeq);
			db.setString(2, vocabularyGroupSeq);

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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delGroupSub(String vocabularyGroupSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delGroupSub() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("vocabulary.delGroupSub"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, vocabularyGroupSeq);
			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delGroupSub() End");
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
