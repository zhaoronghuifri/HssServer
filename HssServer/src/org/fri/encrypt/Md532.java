package org.fri.encrypt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * MD5 锟姐法
*/
/**
 * MD5类
 * @author zhaoronghui
 *
 */
public class Md532 {
    
    // 全锟斤拷锟斤拷锟斤拷
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public Md532() {
    }

    // 锟斤拷锟斤拷锟斤拷式为锟斤拷锟街革拷锟街凤拷
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        // System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 锟斤拷锟斤拷锟斤拷式只为锟斤拷锟斤拷
    @SuppressWarnings("unused")
	private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转锟斤拷锟街斤拷锟斤拷锟斤拷为16锟斤拷锟斤拷锟街达拷
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     *********************************************************************
     *@ Method   锛�  
     *@ Para in  锛�    @param strObj
     *@ Para in  锛�    @return
     *@ Return   锛�
     *@ Date     锛�2014-10-21 涓婂崍9:51:09 by zhaoronghui
     *@ Dscpt    锛�
     ********************************************************************* 
     **/  
    public static String AesMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
           
            resultString =byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
        }
        return resultString;
    }

    
    /**
     *********************************************************************
     *@ Method   锛�  
     *@ Para in  锛�    @param strObj
     *@ Para in  锛�    @return
     *@ Return   锛�
     *@ Date     锛�2014-10-21 涓婂崍9:51:09 by zhaoronghui
     *@ Dscpt    锛�
     ********************************************************************* 
     **/  
    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");           
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
        }
        return resultString;
    }

    @SuppressWarnings("static-access")
	public static void main(String[] args) {
        Md532 getMD5 = new Md532();
        System.out.println(getMD5.GetMD5Code("zhaoronghui"));
    }
}