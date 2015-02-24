
/*============================================================================
 * File Name : DBConnectedException.java
 * package   : com.adlinker.common.db
 * Desc      : Query Handling 
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
import com.cesa.common.*;



public class DBConnectedException extends RuntimeException {

    /**
     * Constructor
     */
	public DBConnectedException() {

		super();
	}

    /**
     * Constructor
     *
     * @param message 메세지
     */
	public DBConnectedException(String message) {

		super(message);
	}

    /**
     * Constructor
     *
     * @param cause Throwable
     */
	public DBConnectedException(Throwable cause) {

		super(cause);
	}

    /**
     * Constructor
     *
	 * @param message 메세지
     * @param cause Throwable
     */
	public DBConnectedException(String message, Throwable cause) {

		super(message, cause);
	}

}
