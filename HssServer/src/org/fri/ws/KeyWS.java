package org.fri.ws;

import org.fri.encrypt.Md532;
import org.fri.utils.utilMethod;
import org.fri.utils.wsUtils;

import classes.DBManager;

/**
 * Key webservice 类
 * @author zhaoronghui
 *
 */
public class KeyWS {
	/*
	 *  生成日志对象log
	 */
	//public static Logger ScscfWS.log =Logger.getLogger(KeyWS.class);
	/**
	 * 获取tek
	 * @param callerid
	 * @param calleeid
	 * @param callid
	 * @param checkInfo
	 * @return
	 */
	public String getTek(String callerid ,String calleeid, String callid){
		try {
			//ScscfWS.log.info("[KeyWS] -  [from]: "+wsUtils.getClientIpAxis()+", [paras]: "+callerid+","+calleeid+","+callid);
			//String ids = callerid+":"+calleeid+":"+callid;			
			String hisTek = DBManager.TekInfo_selectFromHss(callid); //TekInf.getHistoricTek(ids);	
			//System.err.println("hss -hisTek:"+hisTek);	
			//hisTek = wsUtils.reverseStr(hisTek);
			if(null == hisTek)
			{
				ScscfWS.log.info("[KeyWS] - Response a null. Cause historic TEK is null or else error. ");
				return null;
			}			
			/*
			 *   create a trans encrypted TEK
			 */
			/*
			 * 1、MD5
			 */
			String md5Sonicom = Md532.GetMD5Code("sonicom");
			//System.err.println("md5Sonicom:"+md5Sonicom.length());
			/*
			 * 2、session key
			 */
			String sessionKey = wsUtils.createSessionKey(callerid, calleeid, callid, md5Sonicom);			
			//	System.err.println("sessionKey:"+sessionKey.length());	
			/*
			 * 3、 16 bit check  + base64
			 */
			String transTek = wsUtils.createTransTek(callerid, sessionKey,hisTek);			
			//transTek = Coder.Base64Encrypt(transTek.getBytes());		
			ScscfWS.log.info("[KeyWS] - Response a Tek. [paras]: "+transTek.trim());
			return transTek.trim();
		} catch (Exception e) {utilMethod.logExceptions(e);	}
		return null;
	}


}
