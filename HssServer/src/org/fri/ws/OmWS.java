package org.fri.ws;

import java.util.Date;

import org.apache.log4j.Logger;
import org.fri.utils.wsUtils;

import classes.DBManager;

/**
 * OM �ӿ���
 * @author Administrator
 *
 */
public class OmWS{	
	/*
	 *  ������־����log
	 */
	//public static Logger ScscfWS.log =  Logger.getLogger(OmWS.class);

	/**
	 * @return
	 */
	/**
	 * ��ѯUE��Ϣ������
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
	 * �÷�����ѯ����Ue��VoIP��Ϣ����ѯ������ ueid�û��ţ�time:��һ��ע��ʱ�䣬address:ע���SIP��ַ��status:��ǰ��ע��ע��״̬��ON:���ߣ�OFF�����ߣ���special:�Ƿ������û�����������YES ��������NO����domain:ע�������PCSCF������SIP��ַ
	 * @param ueid  : UE���룬ͨ��Ϊ�ֻ���
	 * @return      �����ز�ѯ��¼������ΪString������ΪUeInfo��json��ʽ�����磺 
	 * "ueid":"18222701435","time":"2016-04-13 00:12:24.0","address":"sip:18222701435@223.104.227.225:22777","status":"ON","special":"NO","domain":"sip:123.56.126.70","kek":"2f555a09ff392ef4c12a1060eeab08fd"
	 * 
	 */
	public String selectUeInfo(String ueid){			
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - UeInfo by Ueid from HSS.  ["+true+"], [ueid] :"+ueid);
		return DBManager.UeInfo_selectFromHss(ueid);	
	}
	/**
	 * 	 * �÷�����ѯUe��VoIP��Ϣ����ѯ������ ueid�û��ţ�time:��һ��ע��ʱ�䣬address:ע���SIP��ַ��status:��ǰ��ע��ע��״̬��ON:���ߣ�OFF�����ߣ���special:�Ƿ������û�����������YES ��������NO����domain:ע�������PCSCF������SIP��ַ
	 * @param start    :  start��ʾ��ҳʱ����ʼ��ǣ�Ĭ�ϴ�1��ʼ������ѯ��101~200�м䣬��start����Ϊ101; Ĭ��0  - ����ҳ
	 * @param interval ��  interval��ʾ��ѯ�ӵ�start����������start������ʼ�ۼ� interval�ļ�¼; Ĭ�� 0  - ����ҳ			
	 * @param begain   �� �趨��ѯ�ĵ���ʼʱ�䣻 Ĭ��null - ����ʱ���ɸѡ
	 * @param end      �� �趨��ѯ�ĵĽ�ֹʱ�� ��Ĭ��null - ����ʱ���ɸѡ
	 * @return         ��  Ĭ�Ϸ���ȫ�����������
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
	 * �÷�����ѯIDΪ ueid ��UE��ϵͳ�����к��м�¼��������Ϊ���кͱ��еļ�¼
	 * @param ueid   : �û����룬��Ҫ��ѯ��¼�ĺ���
	 * @return       �����ز�ѯ��¼������ΪString���飬��������Ϊ���м�¼�ĵ�json��ʽ��
	 * ����ֵ�����԰�����
	 * callid: SIP�����callid
	 * caller: ���к���
	 * callee: ���к���
	 * status: ͨ��״̬ INITIAL/CONFIRMMED/TERMINATED
	 * begain: ͨ����ʼʱ��  ��ʽ��YY-MM-DD hh:mm:ss SSS   ���磺2016-04-13 11:07:49.0
	 * end:    ͨ����ֹʱ��
	 * phrase: ͨ��������Ϣ ����ͨ��ΪNORMAL,�����ֱ���REJECTED/FAILED/CANCEL��
	 * total : ͨ��ʱ�� 00:00:00 ��ʽ
	 * flow  : ͨ����������  xxx KB
	 * ���磺 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLog(String ueid){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs to HSS.  ["+true+"], [ueid] :"+ueid);
		return DBManager.CallLog_selectByUeidFromHss(ueid);
	}
	/**
	 * �÷�����ѯ���м�¼��
	 * ��ѯ��¼������ΪString���飬��������Ϊ���м�¼�ĵ�json��ʽ��
	 * ����ֵ�����԰�����
	 * callid: SIP�����callid
	 * caller: ���к���
	 * callee: ���к���
	 * status: ͨ��״̬ INITIAL/CONFIRMMED/TERMINATED
	 * begain: ͨ����ʼʱ��  ��ʽ��YY-MM-DD hh:mm:ss SSS   ���磺2016-04-13 11:07:49.0
	 * end:    ͨ����ֹʱ��
	 * phrase: ͨ��������Ϣ ����ͨ��ΪNORMAL,�����ֱ���REJECTED/FAILED/CANCEL��
	 * total : ͨ��ʱ�� 00:00:00 ��ʽ
	 * flow  : ͨ����������  xxx KB
	 * ���磺 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 *
	 * @param start    :  start��ʾ��ҳʱ����ʼ��ǣ�Ĭ�ϴ�1��ʼ������ѯ��101~200�м䣬��start����Ϊ101; Ĭ��0  - ����ҳ
	 * @param interval ��  interval��ʾ��ѯ�ӵ�start����������start������ʼ�ۼ� interval�ļ�¼; Ĭ�� 0  - ����ҳ			
	 * @param begain   �� �趨��ѯ�ĵ���ʼʱ�䣻 Ĭ��null - ����ʱ���ɸѡ
	 * @param end      �� �趨��ѯ�ĵĽ�ֹʱ�� ��Ĭ��null - ����ʱ���ɸѡ
	 * @return         ��  Ĭ�Ϸ���ȫ�����������
	 */
	public String[] selectCallLogs(int start,int interval,Date begain,Date end){	
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLog  from HSS.  ["+true+"], [begain] :"+begain+",[end]:"+end);
		return 	DBManager.CallLog_selectFromHss(start-1, interval, begain, end);	
	}


	/**
	 * �÷�����ѯϵͳ���еĺ��м�¼�����������ָ������ֵ�������Ƿ���ʱ�併��
	 * @param timeacs   : �Ƿ���ͨ������ʱ�併�����в�ѯ�ļ�¼�����true:�ǣ�false:��
	 * @return          �����ز�ѯ��¼������ΪString���飬��������Ϊ���м�¼�ĵ�json��ʽ��
	 * ����ֵ�����԰�����
	 * callid: SIP�����callid
	 * caller: ���к���
	 * callee: ���к���
	 * status: ͨ��״̬ INITIAL/CONFIRMMED/TERMINATED
	 * begain: ͨ����ʼʱ��  ��ʽ��YY-MM-DD hh:mm:ss SSS   ���磺2016-04-13 11:07:49.0
	 * end:    ͨ����ֹʱ��
	 * phrase: ͨ��������Ϣ ����ͨ��ΪNORMAL,�����ֱ���REJECTED/FAILED/CANCEL��
	 * total : ͨ��ʱ�� 00:00:00 ��ʽ
	 * flow  : ͨ����������  xxx KB
	 * ���磺 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLogByTimeFromHss(boolean timeacs){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs by Timestamp from HSS.  ["+true+"], [decs?] :"+timeacs);
		return DBManager.CallLog_selectByTimeFromHss(timeacs);
	}
	/**
	 * �÷�����ѯϵͳ���еĺ��м�¼�����������ָ������ֵ�������Ƿ���ͨ��ʱ������
	 * @param duration   : �Ƿ���ͨ��ʱ����С�������в�ѯ�ļ�¼�����true:�ǣ�false:��
	 * @return           �����ز�ѯ��¼������ΪString���飬��������Ϊ���м�¼�ĵ�json��ʽ��
	 * ����ֵ�����԰�����
	 * callid: SIP�����callid
	 * caller: ���к���
	 * callee: ���к���
	 * status: ͨ��״̬ INITIAL/CONFIRMMED/TERMINATED
	 * begain: ͨ����ʼʱ��  ��ʽ��YY-MM-DD hh:mm:ss SSS   ���磺2016-04-13 11:07:49.0
	 * end:    ͨ����ֹʱ��
	 * phrase: ͨ��������Ϣ ����ͨ��ΪNORMAL,�����ֱ���REJECTED/FAILED/CANCEL��
	 * total : ͨ��ʱ�� 00:00:00 ��ʽ
	 * flow  : ͨ����������  xxx KB
	 * ���磺 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLogByDurationFromHss(boolean duration){
		ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs by Duration from HSS.  ["+true+"], [decs?] :"+duration);
		return DBManager.CallLog_selectByDurationFromHss(duration);
	}
	/**
	 * �÷�����ѯϵͳ���еĺ��м�¼�����������ָ������ֵ�������Ƿ�����������С�Ŵ�С�Ľ���
	 * @param dataflow   : �Ƿ���ͨ�������������Ĵ�С�������в�ѯ�ļ�¼�����true:�ǣ�false:��
	 * @return           �����ز�ѯ��¼������ΪString���飬��������Ϊ���м�¼�ĵ�json��ʽ��
	 * ����ֵ�����԰�����
	 * callid: SIP�����callid
	 * caller: ���к���
	 * callee: ���к���
	 * status: ͨ��״̬ INITIAL/CONFIRMMED/TERMINATED
	 * begain: ͨ����ʼʱ��  ��ʽ��YY-MM-DD hh:mm:ss SSS   ���磺2016-04-13 11:07:49.0
	 * end:    ͨ����ֹʱ��
	 * phrase: ͨ��������Ϣ ����ͨ��ΪNORMAL,�����ֱ���REJECTED/FAILED/CANCEL��
	 * total : ͨ��ʱ�� 00:00:00 ��ʽ
	 * flow  : ͨ����������  xxx KB
	 * ���磺 
	 * "callid":"diqd2345346dgfwerwqdwqdwq","caller":"18966666666","callee":"18513019385","status":"TERMINATED","begain":"2016-04-13 11:07:49.0","end":"2016-04-13 11:07:50.0","phrase":"NORMAL","total":"00:01","flow":"7 KB"}
	 */
	public String[] selectCallLogByFlowFromHss(boolean dataflow){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SELECT] - CallLogs by DataFlow from HSS.  ["+true+"], [decs?] :"+dataflow);
		return DBManager.CallLog_selectByFlowFromHss(dataflow);
	}	

	/**
	 * �÷�������Ue�Ƿ�¼����
	 * @param ueid  : UE���룬ͨ��Ϊ�ֻ���
	 * @param opt   : ����¼���򿪣�YES�� ����¼���رգ�NO
	 * @return      �������޸ĺ��¼��������Ϣ���Լ�������Ƿ���Ч
	 */
	public String setUeRecorderInHss(String ueid,String opt){
		//ScscfWS.log.info("[OM-WS] -  [from]: "+wsUtils.getClientIpAxis());
		ScscfWS.log.info("[OM-WS] - [SET] - Ue Recorder Key to HSS.  ["+true+"], [ueid] :"+ueid+", [opt] :"+opt);
		return (String) DBManager.Recorder_setToHss(ueid, opt);
	}		


}
