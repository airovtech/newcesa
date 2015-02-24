
/*============================================================================
 * File Name : DataAccessException.java
 * package   : com.adlinker.common.db
 * Desc      : Data Access 관련 Exception
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
import org.apache.log4j.Logger;


public class DataAccessException extends RuntimeException {

    /**
     * Constructor
     */
	public DataAccessException() {

		super();
	}

    /**
     * Constructor
	 *
	 * @param message 메세지
     */
	public DataAccessException(String message) {

		super(message);
	}

    /**
     * Constructor
	 *
	 * @param cause Throwable
     */
	public DataAccessException(Throwable cause) {

		super(cause);
	}

    /**
     * Constructor
	 *
	 * @param message 메세지
	 * @param cause Throwable
     */
	public DataAccessException(String message, Throwable cause) {

		super(message, cause);
	}

}
