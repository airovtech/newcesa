package com.cesa.dao;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.cesa.common.*;
import com.cesa.db.*;
import java.util.*;
import java.sql.*;

public class ProjectDAO extends BaseDAO {

	Logger log = Logger.getLogger(ProjectDAO.class);

	public ProjectDAO() {
		super();
	}

	/**
     * ProjectDAO instance를 생성한다.
     *
     * @return ProjectDAO
     */
    public static ProjectDAO getInstance() {
        return (ProjectDAO)lookupInstance(ProjectDAO.class.getName());
    }


	/**
	* 목록을 조회한다.
	* @param void
	* @return RowSetMapper
	*/
	public RowSetMapper getProjectList(String groupSeq, int pageSize, int currPage){
		if( log.isDebugEnabled() ) {
			//log.debug("getProjectList() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();
		StringBuffer whereQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.projectList"));
			
			whereQuery.append(" WHERE A.deleteYN=0 \n");
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
				//log.debug("getProjectList() End");
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
	public RowSetMapper getProjectDetail(String seq){
		if( log.isDebugEnabled() ) {
			//log.debug("getProjectDetail() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.projectDetail"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());
			db.setString(1, seq);
			db.execute(query);

			if( log.isDebugEnabled() ) {
				//log.debug("getProjectDetail() End");
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
	 * @param ArrayList params
	 * @return boolean 
	 */
	public String regProject(List params){

		boolean check_result = false;
		String lastInsertId = null;

		if( log.isDebugEnabled() ) {
			//log.debug("regProject() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		//lastInsert ID 를 가져오기위해 conn 을 여기서 가져온다.
		Connection con = ConnectionManager.getConnection();

		try {

			sbufQuery.append(QueryContext.getInstance().get("project.regProject"));

			RowSetMapper db = new RowSetMapper(con);
			QueryManager query = new QueryManager(sbufQuery.toString());

			for(int i = 1; i <= params.size() ; i++){
				db.setString(i, (String)params.get(i-1));
			}

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
	 * @param String parmas
	 * @return boolean 
	 */
	public boolean modProject(List params){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("modProject() Start");
		}
		
		StringBuffer sbufQuery = new StringBuffer();
		try {

			sbufQuery.append(QueryContext.getInstance().get("project.modProject"));


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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delProject(String seq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delProject() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.delProject"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, seq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delProject() End");
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
	 * 목록
	 *
	 * @param
	 */
	public RowSetMapper projectActivityList(String projectSeq) throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("projectActivityList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("project.projectActivityList"));


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("projectActivityList() End!");
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
	 * 정보를 등록한다.
	 * @param String seq
	 * @return int
	 */
	public boolean regProjectActivity(String activity, String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regProjectActivity() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.regActivity"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, activity);
			db.setString(2, projectSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("regProjectActivity() End");
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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delProjectActivity(String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delProjectActivity() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.delActivity"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delProjectActivity() End");
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
	 * 목록
	 *
	 * @param
	 */
	public RowSetMapper projectWordList(String projectSeq) throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("projectWordList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("project.projectWordList"));


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("projectWordList() End!");
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
	 * 정보를 등록한다.
	 * @param String seq
	 * @return int
	 */
	public boolean regProjectWord(String word, String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regProjectWord() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.regWord"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, word);
			db.setString(2, projectSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("regProjectWord() End");
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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delProjectWord(String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delProjectWord() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.delWord"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delProjectWord() End");
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
	 * 목록
	 *
	 * @param
	 */
	public RowSetMapper projectActivityWordList(String projectSeq) throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("projectActivityWordList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("project.projectActivityWordList"));


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("projectActivityWordList() End!");
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
	 * 목록
	 *
	 * @param
	 */
	public RowSetMapper projectActivityWordFrontList(String projectSeq, String activityIndex) throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("projectActivityWordFrontList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("project.projectActivityWordFrontList"));


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);
			db.setString(2, activityIndex);

			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("projectActivityWordFrontList() End!");
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
	 * 정보를 등록한다.
	 * @param String seq
	 * @return int
	 */
	public boolean regProjectActivityWord(String checkedActivity, String checkedWord, String activity, String word, String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("regProjectActivityWord() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.regActivityWord"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, checkedActivity);
			db.setString(2, checkedWord);
			db.setString(3, activity);
			db.setString(4, word);
			db.setString(5, projectSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("regProjectActivityWord() End");
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
	 * 정보를 삭제한다.
	 * @param String seq
	 * @return int
	 */
	public boolean delProjectActivityWord(String projectSeq){
		boolean check_result = false;
		if( log.isDebugEnabled() ) {
			//log.debug("delProjectActivityWord() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {
			sbufQuery.append(QueryContext.getInstance().get("project.delActivityWord"));
			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			if(db.executeUpdate(query) == 1){
				check_result = true;
			}
			if( log.isDebugEnabled() ) {
				//log.debug("delProjectActivityWord() End");
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
	 * 목록
	 *
	 * @param
	 */
	public RowSetMapper projectPropertiesList(String projectSeq) throws DataAccessException {

    	if( log.isDebugEnabled() ) {
			//log.debug("projectPropertiesList() Start!");
    	}

		StringBuffer sbufQuery = new StringBuffer();
        try {

			sbufQuery.append(QueryContext.getInstance().get("project.projectPropertiesList"));


			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			db.setString(1, projectSeq);

			db.execute(query);

    		if(log.isDebugEnabled()) {
    			//log.debug("projectPropertiesList() End!");
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
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean regProjectProperties(List params){

		boolean check_result = false;

		if( log.isDebugEnabled() ) {
			//log.debug("regProjectProperties() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {

			sbufQuery.append(QueryContext.getInstance().get("project.regProjectProperties"));

			RowSetMapper db = new RowSetMapper();
			QueryManager query = new QueryManager(sbufQuery.toString());

			for(int i = 1; i <= params.size() ; i++){
				//log.debug((String)params.get(i-1));
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
	 * 수정한다.
	 * @param ArrayList params
	 * @return boolean 
	 */
	public boolean modProjectProperties(List params){

		boolean check_result = false;

		if( log.isDebugEnabled() ) {
			//log.debug("modProjectProperties() Start");
		}
		StringBuffer sbufQuery = new StringBuffer();

		try {

			sbufQuery.append(QueryContext.getInstance().get("project.modProjectProperties"));

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

}
