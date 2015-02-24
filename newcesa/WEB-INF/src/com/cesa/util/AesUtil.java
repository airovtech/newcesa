package com.cesa.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.*;
import java.security.*;
import org.apache.log4j.Logger;


public class AesUtil {
	
	private static Logger log = Logger.getLogger(AesUtil.class);
    
    public static String key = "201403DNTLOLGUID";
		
	static byte [] iv = new byte [] 
							{
								0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
								0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
							};
    
    /**
     * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
     *
     * @param hex    hex string
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }

        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }

    /**
     * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
     *
     * @param ba        byte[]
     * @return
     */
    public static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer(ba.length * 2);
        String hexNumber;
        for (int x = 0; x < ba.length; x++) {
            hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

    /**
     * AES 방식의 암호화
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String encrypt(String message) throws Exception {

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, params);

        byte[] encrypted = cipher.doFinal(message.getBytes("UTF-8"));
        return byteArrayToHex(encrypted);
    }

    /**
     * AES 방식의 복호화
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String decrypt(String encrypted) throws Exception {
        //KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //kgen.init(128);
        // use key coss2
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
		params.init(new IvParameterSpec(iv));
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, params);
        

        byte[] original = cipher.doFinal(hexToByteArray(encrypted));
        String originalString = new String(original, "UTF-8");
        return originalString;
    }
}
