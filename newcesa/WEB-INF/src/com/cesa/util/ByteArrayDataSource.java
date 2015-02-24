package com.cesa.util;

import java.io.*;
import javax.activation.*;

/**
 * DataSource interface를 을 구현한 데이터 소스 

 * @version 1.0
 */
public class ByteArrayDataSource implements DataSource {
    private byte[] data;	// data
    private String type;	// content-type

    /** 
	 * <pre>
	 * 생성자
	 * <code>InputStream</code>으로 <code>DataSource</code>를 만든다. 
	 * </pre>
	 *
	 * @param is InputStream
	 * @param type content-type
	 */
    public ByteArrayDataSource(InputStream is, String type) {
        this.type = type;
        try 
		{ 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
			int ch;

			while ((ch = is.read()) != -1) os.write(ch);
			data = os.toByteArray();

        } 
		catch(IOException ioex){}
    }

    /** 
	 * <pre>
	 * 생성자
	 * byte array 로 <code>DataSource</code>를 만든다. 
	 * </pre>
	 *
	 * @param data 데이터(byte[])
	 * @param type content-type
	 */
    public ByteArrayDataSource(byte[] data, String type) {
        this.data = data;
		this.type = type;
    }

    /** 
	 * <pre>
	 * 생성자
	 * String 으로 <code>DataSource</code>를 만든다. 
	 * </pre>
	 *
	 * @param data 데이터(String)
	 * @param type content-type
	 */
    public ByteArrayDataSource(String data, String type) {
		try {
			// String 을 byte array 로 받는다. 
			this.data = data.getBytes("iso-8859-1");
		} 
		catch(UnsupportedEncodingException uex){}
		this.type = type;
    }

    /**
	 * <pre>
     * 데이터에 대해 <code>InputStream</code>를 리턴한다. 
     * Note - a new stream must be returned each time.
	 * </pre>
	 *
	 * @return InputStream 새로운스트림
	 * @exception IOException 데이터가없는 경우
     */
    public InputStream getInputStream() throws IOException {
		if (data == null)
			throw new IOException("no data");
		return new ByteArrayInputStream(data);
    }

	/**
	 * <pre>
	 * OutputStream 은 가져올 수 없다. 
	 * </pre>
	 * @exception IOException 항상 Exception 을 return 한다. 
	 */
    public OutputStream getOutputStream() throws IOException {
		throw new IOException("cannot do this");
    }

	/**
	 * content-type 을 얻는다. 
	 *
	 * @return content-type
	 */
    public String getContentType() {
        return type;
    }


	/**
	 * name 을 얻는다. 
	 *
	 * @return 'paran shopping'
	 */
    public String getName() {
        return "paran shopping";
    }
}
