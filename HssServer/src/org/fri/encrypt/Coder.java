/**     
 ***************************************************************
 * @ Doc name  :   HMACSHA1.java     
 * @ Include in:   org.fri.encrypt  
 * @ Right by  :   copyright(c) 2014,Rights Reserved  
 * @ Commpany  :   The first Research Institute
 * @ Author    :    rhzhao 
 * @ Version   :   v1.0  
 * @ Date      :   2014-7-23 濞戞挸锕ゅ畷锟�0:23:04  
 ***************************************************************
 */
package org.fri.encrypt;


/**  
 ***************************************************************   
 *@ Class          :   HMACSHA1                                
 *@ Description    :                                              
 *@ Author         :                                              
 *@ Version        :   v1.0                                       
 *@ Date           :   2014-7-23 濞戞挸锕ゅ畷锟�0:23:04      
 ******************************************************************  
 **/
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * 闁糕晞娅ｉ、鍛村礉閻樿尙妲曠紓浣稿濞嗭拷
 * 
 * @author 婵﹣鐒﹂悥锟�
 * @version 1.0
 * @since 1.0
 */
public abstract class Coder {
	
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_DES = "DES";
	public static final String KEY_MAC = "HmacMD5";
	/**
	 * MAC缂佺姵顨濈涵鍫曞矗椤栫儑鎷峰ù鐘劙缁楀懏寰勫槌栫�哥紒鐘愁殱绾拷
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	
	/**
	 * BASE64閻熸瑱绲介惁锟�
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64闁告梻濮撮惁锟�
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * MD5闁告梻濮撮惁锟�
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		return md5.digest();

	}

	/**
	 * SHA闁告梻濮撮惁锟�
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();

	}

	/**
	 * HMAC闁告梻濮撮惁锟�
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	private static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);	
	}




	
	/**
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String DESencrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return strs;
	}



	
	/**
	 * @param data
	 * @param key
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String DESdecrypt(String data, String key) throws IOException,Exception {

		if (data == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] buf = decoder.decodeBuffer(data);
		byte[] bt = decrypt(buf,key.getBytes());
		return new String(bt);
	}



	/**
	 * 加密接口
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(KEY_DES);
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);

	}



	/**
	 *********************************************************************
	 *@ Method   闁挎冻鎷� 
	 *@ Para in  闁挎冻鎷�   
	 *@ Return   闁挎冻鎷�
	 *@ Date     闁挎冻鎷�014 2014-7-25 濞戞挸顑呭畷锟�:24:44  
	 *@ Othor    闁挎冻鎷�
	 ********************************************************************* 
	 */  
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {	
		SecureRandom sr = new SecureRandom();		
		DESKeySpec dks = new DESKeySpec(key);	
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_DES);
		SecretKey securekey = keyFactory.generateSecret(dks);		
		Cipher cipher = Cipher.getInstance(KEY_DES);	
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);

	}



	/**********************************************************************
	 ***                      Base64                                    ***
	 ************************************************************************/

	public static String Base64Encrypt(byte[] inputStr){
		//System.err.println("闁告鍠愰弸锟�" + inputStr);
		byte[] inputData = inputStr;
		String code = null;
		try {
			code = Coder.encryptBASE64(inputData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}

	public static String Base64Decrypt(String codeStr) throws Exception{
		//System.err.println("闁告鍠愰弸锟�" + codeStr);
		byte[] output = Coder.decryptBASE64(codeStr);
		String outputStr = new String(output);
		//System.err.println("BASE64閻熸瑱绲介惁鎴﹀触閿燂拷 + outputStr);
		return outputStr;
	}



	/**********************************************************************
	 ***                     MD5                                         ***
	 ************************************************************************/

	public static String MD5Encrypt(String inputStr) throws Exception{	
		byte[] inputData = inputStr.getBytes();
		BigInteger md5 = new BigInteger(Coder.encryptMD5(inputData));		
		return md5.toString(16);
	}
	
	/**********************************************************************
	 ***                     SHA                                          ***
	 ************************************************************************/
	public static byte[] SHAEncrypt(String inputStr) {
		//System.err.println("before:" + inputStr);
		byte[] inputData = null;
		try {
			inputData = inputStr.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	//	BigInteger sha = null;
	/*	try {
			sha = new BigInteger(encryptSHA(inputData));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		byte[] t = null;
		try {
			t = encryptSHA(inputData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//sha.toByteArray();
		
		/*
		 *  ex 6 byte
		 */
		
		byte[] temp = new byte[6];
		for(int i=0;i<6;i++){
			temp[i] = t[i];
		}
		
		//System.err.println("SHA-1:" + temp.length);
		/*try {
			return new String(temp,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.err.println("SHA:" + AES.parseByte2HexStr(temp));
		//return sha.toString(32);
		return temp;
	}

	/***********************************************************************
	 ***                    HMAC                                         ***
	 ************************************************************************/
	
	public static String HMACEncrypt(String inputStr) throws Exception{
		//System.err.println("inputStr:" + inputStr);
		byte[] inputData = inputStr.getBytes();
		BigInteger mac = new BigInteger(Coder.encryptHMAC(inputData, inputStr));
		//System.err.println("HMAC:" + mac.toString(16));
		return mac.toString(16);
	}
}

