/**     
 ***************************************************************
 * @ Doc name  :   AES.java     
 * @ Include in:   org.fri.encrypt  
 * @ Right by  :   copyright(c) 2014,Rights Reserved  
 * @ Commpany  :   The first Research Institute
 * @ Author    :    rhzhao 
 * @ Version   :   v1.0  
 * @ Date      :   2014-7-25 ï¿½ï¿½ï¿½ï¿½5:33:53  
 ***************************************************************
 */
package org.fri.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


/**  
 ***************************************************************   
 *@ Class          :   AES                                
 *@ Description    :   AES¼ÓÃÜÀà                           
 *@ Author         :                                              
 *@ Version        :   v1.0                                       
 *@ Date           :   2014-7-25     
 ******************************************************************  
 **/
public class AES {



	//public static Logger alog = Logger.getRootLogger();//(AES.class);
	/**     
	 * create a instance  AES.          
	 */
	public AES() {
		// TODO Auto-generated constructor stub
		//	InitLog.initLog.info("[AES]: AES Initialized.");
	}

	/** 
	 * 
	 *  
	 * @param content 
	 * @param password  
	 * @return 
	 */  
	public static byte[] encrypt(byte[] content, byte[] password) {  
		try {             
			KeyGenerator kgen = KeyGenerator.getInstance("AES");  
			kgen.init(128, new SecureRandom(password));  
			SecretKey secretKey = kgen.generateKey();  
			byte[] enCodeFormat = secretKey.getEncoded();  
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
			Cipher cipher = Cipher.getInstance("AES");  
			byte[] byteContent =  content;
			cipher.init(Cipher.ENCRYPT_MODE, key);   
			byte[] result = cipher.doFinal(byteContent);  
			return result; 
		} catch (NoSuchAlgorithmException e) {  	        
		} catch (NoSuchPaddingException e) {  
		} catch (InvalidKeyException e) {  
		} catch (IllegalBlockSizeException e) {  
		} catch (BadPaddingException e) {  
		}  
		return null;  
	}  


	/** 
	 * ï¿½ï¿½ï¿½ï¿½ 
	 * 
	 * @param content ï¿½ï¿½Òªï¿½ï¿½ï¿½Üµï¿½ï¿½ï¿½ï¿½ï¿½ 
	 * @param password  ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ 
	 * @return 
	 */  
	public static byte[] encrypt2(byte[] content, byte[] password) {  
		try {  
			SecretKeySpec key = new SecretKeySpec(password, "AES");  
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");  
			byte[] byteContent = content;  
			cipher.init(Cipher.ENCRYPT_MODE, key);// ï¿½ï¿½Ê¼ï¿½ï¿½   
			byte[] result = cipher.doFinal(byteContent);  
			return result; // ï¿½ï¿½ï¿½ï¿½   
		} catch (NoSuchAlgorithmException e) {  
		} catch (NoSuchPaddingException e) {  
		} catch (InvalidKeyException e) {  
		} catch (IllegalBlockSizeException e) {  
		} catch (BadPaddingException e) {  
		}  
		return null;  
	}  

	/**
	 * @param content  
	 * @param password 
	 * @return 
	 */  
	public static byte[] decrypt(byte[] content, String password) {  
		try {  
			KeyGenerator kgen = KeyGenerator.getInstance("AES");  
			kgen.init(128, new SecureRandom(password.getBytes()));  
			SecretKey secretKey = kgen.generateKey();  
			byte[] enCodeFormat = secretKey.getEncoded();  
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);// 
			byte[] result = cipher.doFinal(content);  
			return result; // 
		} catch (NoSuchAlgorithmException e) {  
		} catch (NoSuchPaddingException e) {  
		} catch (InvalidKeyException e) {  
		} catch (IllegalBlockSizeException e) {  
		} catch (BadPaddingException e) {  
		}  
		return null;  
	}  



	public static String parseByte2HexStr(byte buf[]) {  
		StringBuffer sb = new StringBuffer();  	      
		for (int i = 0; i < buf.length; i++) {  
			String hex = Integer.toHexString(buf[i] & 0xFF);  
			if (hex.length() == 1) {  
				hex = '0' + hex;  
			}  
			sb.append(hex.toUpperCase());  	              
		}  	   
		return sb.toString();  
	}  


	public static byte[] parseHexStr2Byte(String hexStr) {  
		if (hexStr.length() < 1)  
			return null;  
		byte[] result = new byte[hexStr.length()/2];  
		for (int i = 0;i< hexStr.length()/2; i++) {  
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
			result[i] = (byte) (high * 16 + low);  
		}  
		return result;  
	}  


	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	private static byte charToByte(char c) {  
		return (byte) "0123456789ABCDEF".indexOf(c);  
	}  

	/** 
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
		if (hexString == null || hexString.equals("")) {  
			return null;  
		}  
		hexString = hexString.toUpperCase();  
		int length = hexString.length() / 2;  
		char[] hexChars = hexString.toCharArray();  
		byte[] d = new byte[length];  
		for (int i = 0; i < length; i++) {  
			int pos = i * 2;  
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
		}  
		return d;  
	} 



	/**
	 *********************************************************************
	 *@ Method    
	 *@ Para in      @param content
	 *@ Para in      @param password
	 *@ Para in      @return
	 *@ Para in      @throws UnsupportedEncodingException
	 *@ Return   
	 *@ Date     2014-10-17 8:35:13 by zhaoronghui
	 *@ Dscpt    
	 ********************************************************************* 
	 **/  
	public static byte[] AESEncrypt(byte[] content,byte[] password) throws UnsupportedEncodingException{	

		//System.out.println("content length:"+content.length);
		//System.out.println("password length:"+password.length);
		byte[] encryptResult = encrypt2(content, password); 
		//System.out.println("encryptResult.length:" + encryptResult.length);
		//String encryptResultStr =  parseByte2HexStr(encryptResult);//new String(encryptResult,"ISO-8859-1");
		//System.out.println("encryptResult.length:" + encryptResult.length);
		//System.out.println("encryptResultStr.length:" + encryptResultStr.length());  
		return encryptResult;
	}


	/**
	 * @param content
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] AESEncrypt4CheckBit(byte[] content,byte[] password) throws UnsupportedEncodingException{	
		//ï¿½ï¿½ï¿½ï¿½   
		//System.out.println("ï¿½ï¿½ï¿½ï¿½Ç°ï¿½ï¿½" + content);  
		byte[] encryptResult = encrypt2(content, password); 
		//System.out.println("encryptResult.length:" + encryptResult.length);
		byte[] temp = new byte[6];
		/*
		 *  ex 6 bits
		 */
		for(int i=0;i<6;i++)
			temp[i] = encryptResult[i];		

		return temp;
	}



	/**
	 * ½âÃÜ
	 * @param encryptResultStr
	 * @param password
	 * @return
	 */
	public static String AESDecrypt(String encryptResultStr,String password){
		//ï¿½ï¿½ï¿½ï¿½   
		byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
		byte[] decryptResult = decrypt(decryptFrom,password);  
		//System.out.println("ï¿½ï¿½ï¿½Üºï¿½" + new String(decryptResult));
		return new String(decryptResult);
	}

}
