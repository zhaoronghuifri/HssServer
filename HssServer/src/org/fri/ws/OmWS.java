package org.fri.ws;

import java.util.Date;

import org.apache.log4j.Logger;
import org.fri.utils.wsUtils;

import classes.DBManager;

/**
 * OM 接口类
 * @author Administrator
 *
 */
public class OmWS{	
	/*
	 *  生成日志对象log
	 */
	//public static Logger ScscfWS.log =  Logger.getLogger(OmWS.class);

	/**
	 * @return
	 */
	/**
	 * 查询UE信息的总数
	 * @param start
	 * @param interval
	 * @param begain
	 * @param end
	 * @return
	 */
	public long selectUeInfoTotalNum(int start,int interval,Date begain,Date end){	
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		return DBManager.UeInfo_selectTotalNum(start,interval,begain,end);
	}
	/**
	 * 该方法查询单个Ue的VoIP信息，查询到包括 ueid用户号，time:上一次注册时间，address:注册的SIP地址，status:当前的注册注销状态（ON:在线，OFF：离线），special:是否特殊用户（黑名单：YES 白名单：NO），domain:注册归属的PCSCF域名或SIP地址
	 * @param ueid  : UE号码，通常为手机号
	 * @return      ：返回查询记录，类型为String，内容为UeInfo的json格式，例如： 
	 * "ueid":"18222701435","time":"2016-04-13 00:12:24.0","address":"sip:18222701435@223.104.227.225:22777","status":"ON","special":"NO","domain":"sip:123.56.126.70","kek":"2f555a09ff392ef4c12a1060eeab08fd"
	 * 
	 */
	public String selectUeInfo(String ueid){			
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - UeInfo by Ueid from HSS.  ["+true+"], [ueid] :"+ueid);
		return DBManager.UeInfo_selectFromHss(ueid);	
	}
	/**
	 * 	 * 该方法查询Ue的VoIP信息，查询到包括 ueid用户号，time:上一次注册时间，address:注册的SIP地址，status:当前的注册注销状态（ON:在线，OFF：离线），special:是否特殊用户（黑名单：YES 白名单：NO），domain:注册归属的PCSCF域名或SIP地址
	 * @param start    :  start表示分页时的起始标记，默认从1开始。；查询第101~200中间，则start设置为101; 默认0  - 不分页
	 * @param interval ：  interval表示查询从第start个（包含第start个）开始累计 interval的记录; 默认 0  - 不分页			
	 * @param begain   ： 设定查询的的起始时间； 默认null - 不以时间段筛选
	 * @param end      ： 设定查询的的截止时间 ；默认null - 不以时间段筛选
	 * @return         ：  默认返回全部结果的数组
	 */
	public String[] selectUeInfos(int start,int interval,Date begain,Date end){	
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - UeInfo from HSS.  ["+true+"], [begain] :"+begain+",[end]:"+end);
		return 	DBManager.UeInfo_selectFromHss(start-1, interval, begain, end);		
	}	
	/**
	 * @return
	 */
	public long selectCallLogsTotalNum(int start,int interval,Date begain,Date end){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		return DBManager.CallLog_selectTotalNum(start,interval,begain,end);
	}
	/**
	 * 该方法查询ID为 ueid 的UE的系统内所有呼叫记录，包括作为主叫和被叫的记录
	 * @param ueid   : 用户号码，需要查询记录的号码
	 * @return       ：返回查询记录，类型为String数组，数组内容为呼叫记录的的json格式，
	 * 返回值的属性包括：
	 * callid: SIP信令的callid
	 * caller: 主叫号码
	 * callee: 被叫号码
	 * status: 通话状态 INITIAL/CONFIRMMED/TERMINATED
	 * begain: 通话开始时间  格式：YY-MM-DD hh:mm:ss SSS   例如：2016-04-13 11:07:49.0
	 * end:    通话截止时间
	 * phrase: 通话描述信息 正常通话为NORMAL,其他分别由REJECTED/FAILED/CANCEL等
	 * total : 通话时长 00:00:00 格式
	 * flow  : 通话数据流量  xxx KB
	 * 例如： 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLog(String ueid){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs to HSS.  ["+true+"], [ueid] :"+ueid);
		return DBManager.CallLog_selectByUeidFromHss(ueid);
	}
	/**
	 * 该方法查询呼叫记录。
	 * 查询记录，类型为String数组，数组内容为呼叫记录的的json格式，
	 * 返回值的属性包括：
	 * callid: SIP信令的callid
	 * caller: 主叫号码
	 * callee: 被叫号码
	 * status: 通话状态 INITIAL/CONFIRMMED/TERMINATED
	 * begain: 通话开始时间  格式：YY-MM-DD hh:mm:ss SSS   例如：2016-04-13 11:07:49.0
	 * end:    通话截止时间
	 * phrase: 通话描述信息 正常通话为NORMAL,其他分别由REJECTED/FAILED/CANCEL等
	 * total : 通话时长 00:00:00 格式
	 * flow  : 通话数据流量  xxx KB
	 * 例如： 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 *
	 * @param start    :  start表示分页时的起始标记，默认从1开始。；查询第101~200中间，则start设置为101; 默认0  - 不分页
	 * @param interval ：  interval表示查询从第start个（包含第start个）开始累计 interval的记录; 默认 0  - 不分页			
	 * @param begain   ： 设定查询的的起始时间； 默认null - 不以时间段筛选
	 * @param end      ： 设定查询的的截止时间 ；默认null - 不以时间段筛选
	 * @return         ：  默认返回全部结果的数组
	 */
	public String[] selectCallLogs(int start,int interval,Date begain,Date end){	
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLog  from HSS.  ["+true+"], [begain] :"+begain+",[end]:"+end);
		return 	DBManager.CallLog_selectFromHss(start-1, interval, begain, end);	
	}


	/**
	 * 该方法查询系统所有的呼叫记录，并根据入参指定返回值的排序是否按照时间降序
	 * @param timeacs   : 是否按照通话发生时间降序排列查询的记录结果，true:是，false:否
	 * @return          ：返回查询记录，类型为String数组，数组内容为呼叫记录的的json格式，
	 * 返回值的属性包括：
	 * callid: SIP信令的callid
	 * caller: 主叫号码
	 * callee: 被叫号码
	 * status: 通话状态 INITIAL/CONFIRMMED/TERMINATED
	 * begain: 通话开始时间  格式：YY-MM-DD hh:mm:ss SSS   例如：2016-04-13 11:07:49.0
	 * end:    通话截止时间
	 * phrase: 通话描述信息 正常通话为NORMAL,其他分别由REJECTED/FAILED/CANCEL等
	 * total : 通话时长 00:00:00 格式
	 * flow  : 通话数据流量  xxx KB
	 * 例如： 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLogByTimeFromHss(boolean timeacs){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs by Timestamp from HSS.  ["+true+"], [decs?] :"+timeacs);
		return DBManager.CallLog_selectByTimeFromHss(timeacs);
	}
	/**
	 * 该方法查询系统所有的呼叫记录，并根据入参指定返回值的排序是否按照通话时长降序
	 * @param duration   : 是否按照通话时长大小降序排列查询的记录结果，true:是，false:否
	 * @return           ：返回查询记录，类型为String数组，数组内容为呼叫记录的的json格式，
	 * 返回值的属性包括：
	 * callid: SIP信令的callid
	 * caller: 主叫号码
	 * callee: 被叫号码
	 * status: 通话状态 INITIAL/CONFIRMMED/TERMINATED
	 * begain: 通话开始时间  格式：YY-MM-DD hh:mm:ss SSS   例如：2016-04-13 11:07:49.0
	 * end:    通话截止时间
	 * phrase: 通话描述信息 正常通话为NORMAL,其他分别由REJECTED/FAILED/CANCEL等
	 * total : 通话时长 00:00:00 格式
	 * flow  : 通话数据流量  xxx KB
	 * 例如： 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLogByDurationFromHss(boolean duration){
		ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs by Duration from HSS.  ["+true+"], [decs?] :"+duration);
		return DBManager.CallLog_selectByDurationFromHss(duration);
	}
	/**
	 * 该方法查询系统所有的呼叫记录，并根据入参指定返回值的排序是否按照数据流量小号大小的降序
	 * @param dataflow   : 是否按照通话数据流量消耗大小降序排列查询的记录结果，true:是，false:否
	 * @return           ：返回查询记录，类型为String数组，数组内容为呼叫记录的的json格式，
	 * 返回值的属性包括：
	 * callid: SIP信令的callid
	 * caller: 主叫号码
	 * callee: 被叫号码
	 * status: 通话状态 INITIAL/CONFIRMMED/TERMINATED
	 * begain: 通话开始时间  格式：YY-MM-DD hh:mm:ss SSS   例如：2016-04-13 11:07:49.0
	 * end:    通话截止时间
	 * phrase: 通话描述信息 正常通话为NORMAL,其他分别由REJECTED/FAILED/CANCEL等
	 * total : 通话时长 00:00:00 格式
	 * flow  : 通话数据流量  xxx KB
	 * 例如： 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLogByFlowFromHss(boolean dataflow){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs by DataFlow from HSS.  ["+true+"], [decs?] :"+dataflow);
		return DBManager.CallLog_selectByFlowFromHss(dataflow);
	}	

	/**
	 * 该方法设置Ue是否录音，
	 * @param ueid  : UE号码，通常为手机号
	 * @param opt   : 设置录音打开，YES； 设置录音关闭，NO
	 * @return      ：返回修改后的录音开关信息用以检测设置是否生效
	 */
	public String setUeRecorderInHss(String ueid,String opt){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SET] - Ue Recorder Key to HSS.  ["+true+"], [ueid] :"+ueid+", [opt] :"+opt);
		return (String) DBManager.Recorder_setToHss(ueid, opt);
	}		


}
