package org.fri.ws;

import java.util.Date;

import org.apache.log4j.Logger;

import classes.DBManager;

public class ScscfWS {

	public static Logger log =Logger.getLogger(ScscfWS.class);
	public static  String CONFIRMED    			  = "CONFIRMED";
	public static  String INITIAL       		  = "INITIAL";
	public static  String TERMINATED    		  = "TERMINATED";	

	/*
	 *  UE
	 */
	/**
	 * @param ueid
	 * @param time
	 * @param address
	 * @param status
	 * @param special
	 * @param domain
	 * @param kek
	 * @return
	 */
	
	public int ueInsertInfo(String ueid,Date time,String address,String status,String special,String domain,String kek){
		if(DBManager.UeInfo_insertToDB(ueid, time, address, status, special, domain, kek))
		{
			if(!ueid.contains("heart"))
			log.info("[SCSCF-WS] - [INSERT] - UeInfo to DB.  ["+true+"], [ueid]:"+ueid+", [status]:"+status);
			return 0;
		}
		else{
			if(!ueid.contains("heart"))
			log.error("[SCSCF-WS] - [INSERT] - UeInfo to DB.  ["+false+"], [ueid] :"+ueid+", [status]:"+status);
			return -1;		
		}
	}
	/**
	 * @param id
	 * @return
	 */
	public int ueDeleteInfo(String ueid){
	   // log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		if(!ueid.contains("heart"))
		log.info("[SCSCF-WS] - [DELETE] - UeInfo to DB.  ["+true+"], [ueid] :"+ueid);
		if(DBManager.UeInfo_deleteFromHss(ueid))
			return 0;		
		else return -1;	
	}
	
	/**
	 * @param ueid
	 * @return
	 */
	public String ueSelectInfo(String ueid){
		log.info("[SCSCF-WS] - [SELECT] - UeInfo from DB.  ["+true+"], [ueid] :"+ueid);
		return DBManager.UeInfo_selectFromHss(ueid);
	}


	/**
	 * @param ueid
	 * @param time
	 * @param address
	 * @param status
	 * @param special
	 * @param domain
	 * @param kek
	 * @return
	 */
	public int ueUpdateInfo(String ueid,Date time,String address,String status,String special,String domain,String kek){
		
		if(DBManager.UeInfo_updateToHss(ueid, time, address, status, special, domain, kek))
		{
			log.info("[SCSCF-WS] - [UPDATE] - UeInfo to DB.  ["+true+"], [ueid]:"+ueid+", [status]:"+status);
			return 0;
		}
		else{
			log.error("[SCSCF-WS] - [UPDATE] - UeInfo to DB.  ["+false+"], [ueid] :"+ueid+", [status]:"+status);
			return -1;		
		}
	}


	/*
	 *  TEK
	 */
	/**
	 * @param ids
	 * @param tek
	 * @return
	 */
	public int tekInsert(String callid,String caller, String callee,String tek){
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		if( DBManager.TekInfo_insertToHss(callid,caller,callee, tek))
		{
			log.info("[SCSCF-WS] - [INSERT] - Tek to DB.  ["+true+"], [callid]:"+callid);
			return 0;
		}
		else{
			log.error("[SCSCF-WS] - [INSERT] - Tek to DB.  ["+false+"], [callid] :"+callid);
			return -1;		
		}
	}
	/**
	 * @param ids
	 * @return
	 */
	public int tekDelete(String callid){
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		if(DBManager.TekInfo_deleteFromHss(callid))
		{
			log.info("[SCSCF-WS] - [DELETE] - TekInfo from DB.  ["+true+"], [callid] :"+callid);
			return 0;
		}
		else{
			log.error("[SCSCF-WS] - [DELETE] - TekInfo from DB.  ["+false+"], [callid] :"+callid);
			return -1;	
		}
	}

	/**
	 * @param ids
	 * @return
	 */
	public String tekSelect(String callid){
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		String tek=  DBManager.TekInfo_selectFromHss(callid);
		if(null!=tek)
		{
			log.info("[SCSCF-WS] - [SELECT] - TekInfo from DB.  ["+true+"], [callid] :"+callid);
			return tek;
		}else{
			log.error("[SCSCF-WS] - [SELECT] - TekInfo from DB.  ["+false+"], [callid] :"+callid);
		}
		return null;
	}

	/*
	 *    CallLog
	 */

	/**
	 * @param callid
	 * @param SipState
	 * @param caller
	 * @param callee
	 */
	public void callLogInit(String callid,  String caller, String callee){	
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		if(-1 == DBManager.checkCallLogExistedByCallid(callid))
		{
			log.info("[SCSCF-WS] - [INSERT] - CallLog to DB.  ["+true+"], [callid] :"+callid+", [caller] :"+caller+", [callee] :"+callee);
			DBManager.CallLog_init(callid, caller, callee);		
		}
	}

	/**
	 * @param callid
	 * @param SipState
	 */
	public int callLogUpdate(String callid,String SipState){
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		// 0: existed
		int check = DBManager.checkCallLogExistedByCallid(callid);
		log.info("[SCSCF-WS] - [UPDATE] - CallLog existed? (0: existed) =  ["+check+"], [callid] :"+callid);
		if(0 == check){
			log.info("[SCSCF-WS] - [UPDATE] - CallLog to DB.  ["+true+"], [callid] :"+callid+", [SipState] :"+SipState);
			if(SipState.equals(CONFIRMED))
			{
				DBManager.CallLog_setBegain(callid);
				return 0;
			}else if(SipState.equals(TERMINATED))
			{
				int ret = DBManager.CallLog_setEnd(callid);					
				return ret;
			}			
		}
		return -1;		
	}

	/**
	 * @param callid
	 * @param phrase
	 */
	public void callLogAbort(String callid, String phrase){	
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		if(0 == DBManager.checkCallLogExistedByCallid(callid)){
			log.info("[SCSCF-WS] - [ABORT] - CallLog to DB.  ["+true+"], [callid] :"+callid+", [phrase] :"+phrase);
			DBManager.CallLog_setAbort(callid, phrase);
		}
	}

	/*
	 * Recorder  key
	 */
	/**
	 * @param ueid
	 * @return
	 */
	public String recKeySelect(String ueid){
		//log.info("[SCSCF-WS] - [from]: "+wsUtils.getClientIpAxis());
		//log.info("[SCSCF-WS] - [SELECT] - Recorder Key from DB.  ["+true+"], [ueid] :"+ueid);
		return (String) DBManager.Recorder_selectFromHss(ueid);
	}	
}
