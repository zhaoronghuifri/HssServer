package classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fri.utils.utilMethod;
import org.fri.utils.wsUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author zhaoronghui
 *  呼叫日志处理类
 */
@SuppressWarnings("deprecation")
public class CallLogDBManager {  

	/**
	 * 
	 */
	SessionFactory factory;
	Session session ;  

	private  String CONFIRMED      = "CONFIRMED";
	private  String INITIAL        = "INITIAL";
	private  String TERMINATED     = "TERMINATED";	
	private  String NOMARL_PHRASE  = "NORMAL";
	private  String ABNOMARL_PHRASE  = "ABNORMAL";



	/**
	 * 呼叫日志初始化方法
	 * @param callid
	 * @param status
	 * @param caller
	 * @param callee
	 */
	public   void insertCallInit(String callid,String caller,String callee) {  
		this.insertCall(callid, caller, callee);  
	}  

	/**
	 * 设置会话结束并释放
	 * @param callid  会话标示
	 */
	public void setCallAbort(String callid, String phrase){
		this.setAbortCall(callid, phrase); 
	}


	/**
	 * 设置呼叫开始时间
	 * @param callid
	 * @param begain
	 * @return
	 */
	public  boolean setCallBegain(String callid){

		this.setBegainTime(callid);
		return true;		
	}
	/**
	 * 设置呼叫结束时间
	 * @param callid  会话标示
	 * @param end    结束日期
	 * @return
	 */
	public  int setCallEnd(String callid){
		int ret = this.setEndTime(callid);
		return ret;		
	}

	/**
	 * 根据会话标识查询会话是否村则
	 * @param callid
	 * @return
	 */
	public int checkExistedByCallid(String callid) {
		// TODO Auto-generated method stub
		int ret = this.checkCallLogByCallid(callid);
		return ret;
	}


	/**
	 * 通过UE号码查询呼叫日志集合
	 * @param ueid
	 * @return
	 */
	public String[] selectCallLogsById(String ueid){
		String[] ret = this.selectCallLog(ueid);
		return ret;		
	}

	/**
	 * 通过参数设定查询呼叫日志集合
	 * @param start  开始
	 * @param interval 间隔
	 * @param begain  开始时间
	 * @param end     截止时间
	 * @return
	 */
	public String[] selectCallLogsFromHss(int start, int interval,Date begain ,Date end){
		String[] ret = this.selectCallLogs(start, interval, begain, end);
		return ret;		
	}

	/**
	 * 根据时间降序或升序查找日志集合
	 * @param ueid
	 * @return
	 */
	public String[] selectCallLogsByTime(boolean timeacs){
		String[] ret = this.selectCallLogByTime(timeacs);
		return ret;		
	}

	/**
	 * 根据通话时长筛选日志集合
	 * @param ueid
	 * @return
	 */
	public String[] selectCallLogsByDuration(boolean duration){
		String[] ret = this.selectCallLogByDuration(duration);
		return ret;		
	}

	/**
	 * 通过流量大小筛选日志集合
	 * @param ueid
	 * @return
	 */
	public String[] selectCallLogsByFlow(boolean dataflow){
		String[] ret = this.selectCallLogByFlow(dataflow);
		return ret;		
	}

	/**
	 * 通过流量大小筛选日志集合
	 * @param ueid
	 * @return
	 */
	public boolean checkAbnormalCallLogsByStatus(String ueid){
		boolean ret = this.setErrCallLogByStatus(null,ueid,null);
		return ret;		
	}


	/**
	 * 返回总呼叫记录数量
	 * @return
	 */
	public long selectCallLogsTotal(int start,int interval,Date begain,Date end){
		long num = this.selectCallLogTotalNum(start,interval,begain, end);
		return num;		
	}

	/**
	 * @param ueid
	 * @return
	 * 	private String callid;
		private String caller;
		private String callee;
		private String status;
		private Date begain;
		private Date end;
		private String phrase;
		private String total;
		private String flow;
	 */
	private String[] selectCallLogs(int start,int interval,Date begain,Date end) {	
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			
			Criteria crit = this.session.createCriteria(CallLog.class);
			crit.addOrder(Order.asc("begain"));
			if(null !=begain && null!=end)
				crit.add(Expression.between("begain", begain, end));	

			if(start>=0 && 0!=interval)
			{
				crit.setFirstResult(start);
				crit.setMaxResults(interval);
			}

			@SuppressWarnings("unchecked")
			List<CallLog> list = crit.list();	

			if(null == list)
				return null;	
			return wsUtils.CallLogObjList2StringArray(list);
		}catch (Exception e){	
			return null;	
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}			
	}

	/**
	 * @param timeacs
	 * @return
	 */
	private String[] selectCallLogByTime(boolean timeacs) {	
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			
			Criteria crit = this.session.createCriteria(CallLog.class);		
			if(!timeacs)
				crit.addOrder(Order.asc("begain"));
			else crit.addOrder(Order.desc("begain"));
			@SuppressWarnings("unchecked")
			List<CallLog> list = crit.list();	
			return wsUtils.CallLogObjList2StringArray(list);
		}catch (Exception e){	
			return null;	
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}			
	}
	/**
	 * @param dataflow :  true ����ʱ����С��������  false:����ʱ����С��������
	 * @return
	 */
	private String[] selectCallLogByDuration(boolean duration) {	
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			Criteria crit = this.session.createCriteria(CallLog.class);		
			if(!duration)
				crit.addOrder(Order.asc("total"));
			else crit.addOrder(Order.desc("total"));
			@SuppressWarnings("unchecked")
			List<CallLog> list = crit.list();	
			//System.err.println("list:"+list);
			return wsUtils.CallLogObjList2StringArray(list);
		}catch (Exception e){	
			e.printStackTrace();
			return null;	
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}			
	}
	/**
	 * @param dataflow :true ����������С��������  false:����������С��������
	 * @return
	 */
	private String[] selectCallLogByFlow(boolean dataflow) {	
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
					
			Criteria crit = this.session.createCriteria(CallLog.class);		
			if(!dataflow)
				crit.addOrder(Order.asc("flow"));
			else crit.addOrder(Order.desc("flow"));
			@SuppressWarnings("unchecked")
			List<CallLog> list = crit.list();	
			//System.err.println("list:"+list);
			return wsUtils.CallLogObjList2StringArray(list);
		}catch (Exception e){	
			e.printStackTrace();
			return null;	
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}			
	}

	/**
	 * @param dataflow :true 
	 * @return
	 */
	private synchronized boolean setErrCallLogByStatus(Session session,String ueid,String currentCallid) {	
		try{
			Criteria crit = session.createCriteria(CallLog.class);		
			crit.add(Restrictions.or(Restrictions.like("status", this.INITIAL,MatchMode.ANYWHERE),
					Restrictions.like("status", this.CONFIRMED,MatchMode.ANYWHERE)));
			crit.add(Restrictions.or(Restrictions.like("caller", ueid,MatchMode.ANYWHERE),Restrictions.like("callee", ueid,MatchMode.ANYWHERE)));	
			@SuppressWarnings("unchecked")
			List<CallLog> list = crit.list();	
			for(int i=0;i<list.size();i++){
				CallLog callLog = list.get(i);
				String callid = callLog.getCallid();
				if(null!= currentCallid && currentCallid.equals(callid))
					continue;		
				callLog.setStatus(this.TERMINATED);
				callLog.setBegain(new Date());
				callLog.setEnd(new Date());
				callLog.setTotal("00:00");
				callLog.setFlow("0 KB");
				callLog.setPhrase(this.ABNOMARL_PHRASE);
				session.saveOrUpdate(callLog);  
			}
			return true;
		}catch (Exception e){				
			utilMethod.logExceptions(e);			
			return false;	
		}	
	}


	/**
	 * @param ueid
	 * @return
	 * 	private String callid;
		private String caller;
		private String callee;
		private String status;
		private Date begain;
		private Date end;
		private String phrase;
		private String total;
		private String flow;
	 */
	private String[] selectCallLog(String ueid) {	
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			Criteria crit = this.session.createCriteria(CallLog.class);		
			crit.add(Restrictions.or(Restrictions.like("caller", ueid,MatchMode.ANYWHERE),Restrictions.like("callee", ueid,MatchMode.ANYWHERE)));	
			crit.addOrder(Order.desc("begain"));
			@SuppressWarnings("unchecked")
			List<CallLog> list = crit.list();
			if(list==null)
				return null;
			return wsUtils.CallLogObjList2StringArray(list);
		}catch (Exception e){	
			e.printStackTrace();
			return null;	
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}			
	}
	/**
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private long selectCallLogTotalNum(int start,int interval,Date begain,Date end) {
		// TODO Auto-generated method stub
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			Criteria crit = this.session.createCriteria(CallLog.class);

			if(null !=begain && null!=end)
				crit.add(Expression.between("begain", begain, end));	

			if(start>=0 && interval!=0)
			{				
				crit.setFirstResult(start);
				crit.setMaxResults(interval);				
			}			

			@SuppressWarnings("unchecked")
			List<UeInfo> list = crit.list();
			tx1.commit(); 

			if(null == list)
				return 0;
			return list.size();
		}catch (Exception e){}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}
		return 0;
	}
	/**
	 * @param callid
	 * @return
	 */
	private int checkCallLogByCallid(String callid) {	
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			
			CallLog log = (CallLog) this.session.get(CallLog.class, callid);
			if(log==null)
				return -1;
			else return 0;
		}catch (Exception e){	
			e.printStackTrace();
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}
		return -1;			
	}


	/**
	 * @param callid
	 * @param status
	 * @param caller
	 * @param callee
	 */
	private  synchronized void insertCall(String callid,String caller,String callee) {  
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			
			Transaction tx1 = this.session.beginTransaction(); 

			CallLog call = (CallLog) this.session.load(CallLog.class, callid); 	
			if(call==null)
				return;			
			call = new CallLog();		
			call.setCallid(callid);
			call.setStatus(this.INITIAL);
			call.setCaller(caller);
			call.setCallee(callee);
			this.session.saveOrUpdate(call);  
			// check history err call log		
			setErrCallLogByStatus(this.session,caller,callid);
			setErrCallLogByStatus(this.session,callee,callid);
			tx1.commit();  
			//DBManager.log.info("[CallLog] - [INSERT] CallLog to DB.  ["+true+"], [callid] :"+callid+", [caller] :"+caller+", [callee] :"+callee);
		}catch(Exception e){
			DBManager.log.error("[CallLog] - [INSERT] CallLog to DB.  ["+false+"], [callid] :"+callid+" , [Cause]:"+e.getLocalizedMessage());
		}finally {
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}	
	}  

	/**
	 * @param callid
	 */
	private void setAbortCall(String callid, String phrase) {  
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx2  = this.session.beginTransaction(); 	

		try{
			CallLog call = (CallLog) this.session.load(CallLog.class, callid); 	
			if(call==null)
				return;

			Date now = new Date();
			call.setStatus(this.TERMINATED);
			call.setPhrase(phrase);
			call.setBegain(now);
			call.setEnd(now);
			call.setTotal(caculateTime(call,now,now));
			this.session.saveOrUpdate(call);  

			//DBManager.log.info("[CallLog] - [INSERT] CallLog Abort to DB.  ["+true+"], [callid] :"+callid+", [phrase] :"+phrase);
		}catch(Exception e){
			DBManager.log.error("[CallLog] - [INSERT] CallLog to DB.  ["+false+"], [callid] :"+callid+", [phrase] :"+phrase+",[Cause]:"+e.getLocalizedMessage());
		}finally{
			tx2.commit(); 
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}
	}  


	/**
	 * @param ids
	 * @param status
	 */
	private void setBegainTime(String callid) { 
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		Transaction tx2  = this.session.beginTransaction();

		try{
			CallLog call = (CallLog) this.session.load(CallLog.class, callid); 
			if(call==null)
				return;

			if(call.getStatus().equals(this.TERMINATED))
				return;

			call.setStatus(this.CONFIRMED);			
			call.setBegain(new Date());			

			this.session.saveOrUpdate(call);  

			//DBManager.log.info("[CallLog] - [INSERT] CallLog BegainTime to DB.  ["+true+"], [callid] :"+callid);
		}catch(Exception e){			
			DBManager.log.error("[CallLog] - [INSERT] CallLog BegainTime to DB.  ["+false+"], [callid] :"+callid+", [Cause]:"+e.getLocalizedMessage());
		}finally{
			tx2.commit();  
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}
		}
	}  

	/**
	 * @param ids
	 * @param status
	 */
	private int setEndTime(String callid) {  
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx2  = this.session.beginTransaction(); 	

		try{
			CallLog call = (CallLog) this.session.load(CallLog.class, callid);
			if(call == null)
				return -1;

			Date now = new Date();
			call.setEnd(now);	

			Date begain = call.getBegain();
			String timeshift = caculateTime(call,now,begain);
			call.setTotal(timeshift);
			call.setStatus(this.TERMINATED);
			call.setPhrase(this.NOMARL_PHRASE);	

			this.session.saveOrUpdate(call);  
			//DBManager.log.info("[CallLog] - [INSERT] CallLog EndTime to DB.  ["+true+"], [callid] :"+callid);
		}catch(Exception e){
			DBManager.log.error("[CallLog] - [INSERT] CallLog EndTime to DB.  ["+false+"], [callid] :"+callid +", [Cause]:"+e.getLocalizedMessage());
		}finally{
			tx2.commit();  
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
				this.factory.close();
			}

		}
		return 0;	
	}  




	// a integer to xx:xx:xx
	/**
	 * @param time
	 * @return
	 */
	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}


	/**
	 * @return
	 */
	public static String caculateTime(CallLog call,java.util.Date end, java.util.Date begain){
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		long l=end.getTime()-begain.getTime();   

		int sec = (int) (l/1000);

		int iflow = 6*sec;

		call.setFlow(String.valueOf(iflow)+" KB");
		//long day=l/(24*60*60*1000);   
		//long hour=(l/(60*60*1000)-day*24);   
		//long min=((l/(60*1000))-day*24*60-hour*60);   
		//long s=(l/1000-day*24*60*60-hour*60*60-min*60); 	

		String total = secToTime(sec);
		//System.out.println(total);  
		return total;
	}


}  
