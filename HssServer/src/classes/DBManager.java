package classes;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * HSS 数据库访问接口类
 * @author Andy
 *
 */
public class DBManager {  
	/**
	 * 日志对象
	 */
	public static Logger log = Logger.getRootLogger();//(DBManager.class);	


	public static void main(String args[]) throws InterruptedException{
		for(int i=0;i<1000;i++)
		{
			UeInfo_insertToDB("test_", new Date(), "localhost", "ON", "NO", "localhost", "zoiwdgqiwghdqwhd");
			System.err.println(UeInfo_selectFromHss("test_"));
			
			CallLog_init("1111","18911070385","18611068603");
			CallLog_setAbort("1111", "488");
			
			TekInfo_insertToHss("8888", "18911070385","18611068603", "tek-"+String.valueOf(i));
			System.err.println("----TEK:"+TekInfo_selectFromHss("8888"));
		}
		//}
		//	CallLog_init("1111","18911070385","18611068603");
		//	boolean ret = CallLog_checkAbnormalByStatusFromHss("18911070385");
		//  CallLog_checkAbnormalByStatusFromHss("18611068603");

		//  CallLog_init("fgdfhdfhdfh","18911070385","18601185682");
		//System.out.println(ret);
		//	CallLog_setAbort("1111", "488");
	}
	/**
	 * 向HSS中添加一条UE信息记录
	 * @param ueid
	 * @param time
	 * @param address
	 * @param status
	 * @param special
	 * @param domain
	 * @param kek
	 * @return
	 */
	public static boolean UeInfo_insertToDB(String ueid,Date time,String address,String status,String special,String domain,String kek) {
		new UeInfoDBManager().insertUeInfoToDB(ueid, time, address, status, special, domain, kek);
		return true;			
	}

	/**
	 * 查询总UE数据条数
	 * @param start
	 * @param interval
	 * @param begain
	 * @param end
	 * @return
	 */
	public static long  UeInfo_selectTotalNum(int start,int interval,Date begain,Date end){	
		return  new UeInfoDBManager().selectUeInfoTotalNumFromDB(start,interval,begain,end);	
	}

	/**
	 * @param ueid
	 * @return
	 */
	public static String  UeInfo_selectFromHss(String ueid){			
		return  new UeInfoDBManager().selectUeInfoFromDB(ueid);		
	}

	/**
	 * @param start
	 * @param interval
	 * @param begain
	 * @param end
	 * @return
	 */
	public static String[] UeInfo_selectFromHss(int start,int interval,Date begain,Date end){			
		return new UeInfoDBManager().selectUeInfosFromHss(start, interval, begain, end)	;
	}
	/**
	 * @param ueid
	 * @return
	 */
	public static boolean UeInfo_deleteFromHss(String ueid){				
		return new UeInfoDBManager().deleteUeInfoFromDB( ueid);		
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
	public static  boolean UeInfo_updateToHss(String ueid,Date time,String address,String status,String special,String domain,String kek){	
		return new UeInfoDBManager().updateUeInfoToDB(ueid, time, address, status, special, domain, kek);		
	}

	/**
	 * @param ueinfo
	 * @return
	 */
	public static  boolean UeInfo_updateToHss(UeInfo ueinfo){	
		return new UeInfoDBManager().updateUeInfoToDB(ueinfo);		
	}

	/**
	 * @param ueid
	 * @param status
	 */
	public static  void UeInfo_updateStatusToHss(String ueid,String status){	
		new UeInfoDBManager().updateUeStatusInfoToDB(ueid, status);		
	}
	/**
	 * @param ueid
	 * @param special
	 * @return
	 */
	public static  void UeInfo_updateSpecialToHss(String ueid,String special){	
		new UeInfoDBManager().updateUeSpecialInfoToDB(ueid, special);		
	}


	/**
	 * @param ids
	 * @param tek
	 * @return
	 */
	public static boolean TekInfo_insertToHss(String callid,String caller, String callee,String tek) {		
		new TekInfoDBManager().insertTekInfoToDB(callid,caller,callee,tek);
		return true;		
	}


	/**
	 * @param ids
	 * @return
	 */
	public static String TekInfo_selectFromHss(String callid){
		return (new TekInfoDBManager().selectTekInfoFromDB(callid));
	}
	/**
	 * @param ids
	 * @return
	 */
	public static boolean TekInfo_deleteFromHss(String callid){		
		return new TekInfoDBManager().deleteTekInfoFromDB(callid);		
	}


	/******************************
	 * 			call log	creation   *
	 *****************************/
	public static void CallLog_init(String callid,String caller,String callee){
		new CallLogDBManager().insertCallInit(callid,  caller, callee);
	}

	public static void CallLog_setAbort(String callid,String phrase){
		new CallLogDBManager().setCallAbort(callid,phrase);
	}

	public static void CallLog_setBegain(String callid){
		new CallLogDBManager().setCallBegain(callid);
	}

	public static int CallLog_setEnd(String callid){
		return new CallLogDBManager().setCallEnd(callid);
	}
	/******************************
	 * 		call log	select *
	 *****************************/
	public static String[]  CallLog_selectFromHss(int start, int interval,Date begain,Date end){		
		return  new CallLogDBManager().selectCallLogsFromHss(start, interval, begain, end)	;
	}
	public static String[]  CallLog_selectByUeidFromHss(String ueid){		
		return  new CallLogDBManager().selectCallLogsById(ueid);		
	}
	public static String[]  CallLog_selectByTimeFromHss(boolean timeacs){		
		return  new CallLogDBManager().selectCallLogsByTime(timeacs);	
	}

	public static String[]  CallLog_selectByDurationFromHss(boolean duration){		
		return  new CallLogDBManager().selectCallLogsByDuration(duration);		
	}

	public static String[]  CallLog_selectByFlowFromHss(boolean dataflow){		
		return  new CallLogDBManager().selectCallLogsByFlow(dataflow);		
	}

	public static boolean  CallLog_checkAbnormalByStatusFromHss(String ueid){		
		return  new CallLogDBManager().checkAbnormalCallLogsByStatus(ueid);		
	}

	public static long  CallLog_selectTotalNum(int start,int interval,Date begain,Date end){		
		return  new CallLogDBManager().selectCallLogsTotal(start,interval,begain,end);	
	}

	/**
	 * @param ids
	 * @return
	 */
	public static Object Recorder_setToHss(String ueid,String opt){
		return new RecorderDBManager().setToDB(ueid, opt);	
	}
	/**
	 * @param ids
	 * @return
	 */
	public static Object Recorder_selectFromHss(String ueid){
		return new RecorderDBManager().selectFromDB(ueid);		
	}


	/**
	 * @param callid
	 * @return
	 */
	public static int checkCallLogExistedByCallid(String callid){
		return  new CallLogDBManager().checkExistedByCallid(callid);
	}


}  
