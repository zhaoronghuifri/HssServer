package classes;

import java.util.Date;
import java.util.List;

import org.fri.utils.wsUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * UE处理方法
 * @author zhaoronghui
 *
 */
@SuppressWarnings("deprecation")
public class UeInfoDBManager {  
	public static String UEID = "UEID";
	public static String ADDR = "ADDR";
	public static String TIME = "TIMESTAMPS";
	public static String STATUS = "STATUS";
	public static String SPECIAL = "SPECIAL";
	public static String DOMAIN = "DOMAIN";
	public static String KEK = "KEK";
	SessionFactory factory;
	Session session;

	/**
	 * 构造函数
	 */
	public UeInfoDBManager(){
		//hibernateUtil = new HibernateUtil();	
		//session = this.hibernateUtil.getSessionFactory().getCurrentSession();  
	}

	/**
	 * 向数据库插入一条UE数据
	 * @param ueid
	 * @param time
	 * @param address
	 * @param status
	 * @param special
	 * @param domain
	 * @param kek
	 */
	public void insertUeInfoToDB(String ueid,Date time,String address,String status,String special,String domain,String kek) {  
		this.insertUeInfo(ueid,time,address,status,special,domain,kek); 
		
	}  

	/**
	 * 从数据库查询UE数据集合
	 * @param start
	 * @param interval
	 * @param begain
	 * @param end
	 * @return
	 */
	public  String[] selectUeInfosFromHss(int start, int interval,Date begain, Date end){
		return  this.selectUeInfos(start, interval, begain, end);
	}

	/**
	 * 从数据库查询UE信息
	 * @param ueid
	 * @return
	 */
	public  String selectUeInfoFromDB(String ueid){	
		return this.selectUeInfo(ueid);
	}


	/**
	 * 从数据库查询符合条件的数据总数
	 * @param start
	 * @param interval
	 * @param begain
	 * @param end
	 * @return
	 */
	public  long selectUeInfoTotalNumFromDB(int start,int interval,Date begain,Date end){	
		return this.selectUeInfoTotalNum(start,interval,begain,end);
	}

	/**
	 * 删除数据库中某条UE信息
	 * @param ueid
	 * @return
	 */
	public  boolean deleteUeInfoFromDB(String ueid){
		this.deleteUeInfo(ueid);
		return true;		
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
	public boolean updateUeInfoToDB(String ueid, Date time, String address,
			String status, String special, String domain, String kek) {
		this.updateUeInfo(ueid, time, address, status, special, domain, kek);
		return true;
	}
	/**
	 * 更新UE数据
	 * @param ueinfo
	 * @return
	 */
	public boolean updateUeInfoToDB(UeInfo ueinfo) {
		this.updateUeInfo(ueinfo);
		return true;
	}
	/**
	 * @param ueid
	 * @param special
	 * @return
	 */
	public boolean updateUeStatusInfoToDB(String ueid,  String status) {
		this.updateUeStatusInfo(ueid, status);
		return true;
	}

	/**
	 * @param ueid
	 * @param special
	 * @return
	 */
	public void updateUeSpecialInfoToDB(String ueid, String special) {
		this.updateUeSpecialInfo(ueid,  special);
	}

	/**
	 * @param title
	 * @param theDate
	 */
	private synchronized void insertUeInfo(String ueid, 
			Date time,
			String address,
			String status,
			String special,
			String domain,
			String kek) {  
		try{
			
			this. factory = HibernateUtil.getSessionFactory();
			this. session = factory .openSession();
			Transaction tx2 = this. session.beginTransaction(); 
			
			UeInfo ueInfo = new UeInfo();
			ueInfo.setUeid(ueid);
			ueInfo.setTime(time);
			ueInfo.setAddress(address);
			ueInfo.setStatus(status);
			ueInfo.setSpecial(special);
			ueInfo.setDomain(domain);  
			ueInfo.setKek(kek);

			this. session.saveOrUpdate(ueInfo);  
			tx2.commit();				
			
			
		}catch(Exception e){			
			if(!ueid.contains("heart"))
			DBManager.log.error("[UE] - [INSERT] UeInfo to DB.  ["+false+"], [ueid]:"+ueid+", [Cause]:"+e.getLocalizedMessage());
		}finally{
			this. session.flush();
			this. session.clear();
			this. session.close();
			this.factory.close();
		}	
	}  


	/**
	 * 查询UE数据信息
	 * @param title
	 * @param theDate
	 */
	private String selectUeInfo(String ueid) {  		
		try{
			this. factory = HibernateUtil.getSessionFactory();
			this. session = factory .openSession();
			
			Criteria crit = this. session.createCriteria(UeInfo.class);

			crit.add(Restrictions.eq("ueid", ueid));			
			UeInfo ue = (UeInfo)crit.uniqueResult();	

			if(null == ue)
				return null;
			String ueStr = wsUtils.UeInfoObjList2String(ue);
			

			return ueStr;
		}catch (Exception e){	
			DBManager.log.error("[UE] - [SELECT] UeInfo from DB.  ["+false+"], [ueid]:"+ueid+", [Cause]:"+e.getLocalizedMessage());
			wsUtils.logExceptions(e);
			return null;	
		}finally{
			this. session.flush();
			this. session.clear();
			this. session.close();		
			this.factory.close();
		}			
	}  



	/**
	 * @param start
	 * @param interval
	 * @param begain
	 * @param end
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	private String[] selectUeInfos(int start,int interval,Date begain, Date end) { 		

		try{
			 factory = HibernateUtil.getSessionFactory();
			this. session = factory .openSession();
			
			Criteria crit = this.session.createCriteria(UeInfo.class);
			crit.addOrder(Order.asc("time"));

			if(null !=begain && null!=end)
				crit.add(Expression.between("time", begain, end));	
			
			if(start>=0 && interval!=0)
			{				
				crit.setFirstResult(start);
				crit.setMaxResults(interval);				
				
			}			
			List<UeInfo> result = crit.list();
			
			if(null == result)
				return null;
			
			/*
			 *  add total to first
			 */	
			

			return wsUtils.UeInfoObjList2StringArray(result);

		}catch (Exception e){	
			wsUtils.logExceptions(e);
			DBManager.log.error("[UE] - [SELECT] UeInfo  by Split and timesplit from DB. [false], [Cause]:"+e.getLocalizedMessage());
			return null;	
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
			}
			this.factory.close();
		}			
	}  
	
	 
	/**
	 * @param ueid
	 * @return
	 */
	private  boolean deleteUeInfo(String ueid) {
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			UeInfo del = (UeInfo)(this.session.get(UeInfo.class, ueid)); 	
			if(del == null)
				return false;

			this.session.delete(del);

			tx1.commit(); 

			return true;
		}catch (Exception e){		
			if(!ueid.contains("heart"))
			DBManager.log.error("[UE] - [DELETE] UeInfo from DB. ["+true+"], [ueid]:"+ueid+", [Cause]:"+e.getLocalizedMessage());
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
			}	
			this.factory.close();

		}
		return false;
	}

	private long selectUeInfoTotalNum(int start,int interval,Date begain,Date end) {
		// TODO Auto-generated method stub
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			Criteria crit = this.session.createCriteria(UeInfo.class);		

			if(null !=begain && null!=end)
				crit.add(Expression.between("time", begain, end));	
			
			if(start>=0 && interval!=0)
			{				
				crit.setFirstResult(start);
				crit.setMaxResults(interval);				
			}			
			
			@SuppressWarnings("unchecked")
			List<UeInfo> list = crit.list();

			if(null == list)
				return 0;
			
			tx1.commit(); 
			return list.size();
		}catch (Exception e){}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
			}
			this.factory.close();
		}
		return 0;
	}
	
	public static void main(String args[]){
		new UeInfoDBManager().updateUeInfo("18513019385",new Date(),"localhost","ON","NO","127.0.0.1","fghjk");
	}
	/**
	 * @param ueid
	 * @param time
	 * @param address
	 * @param status
	 * @param special
	 * @param domain
	 * @param kek
	 */
	private synchronized void updateUeInfo(String ueid, Date time, String address,String status, String special, String domain, String kek) {
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			UeInfo temp = (UeInfo) this.session.load(UeInfo.class, ueid); 	
			temp.setTime(time);
			if(temp.getAddress() != address) 
				temp.setAddress(address);
			if(temp.getStatus() != status)
				temp.setStatus(status);
			if(temp.getSpecial() != special) 
				temp.setSpecial(special);
			if(temp.getDomain() != domain) 
				temp.setDomain(domain);
			if(temp.getKek() != kek) 
				temp.setKek(kek);
			
			this.session.saveOrUpdate(temp);			
			tx1.commit(); 
		}catch (Exception e){		 
			DBManager.log.error("[UE] - [UPDATE] UeInfo to DB.  ["+true+"], [ueid]:"+ueid+", [Cause]:"+e.getLocalizedMessage());
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();				
			}
			this.factory .close();
		}
	}

	private synchronized void updateUeInfo(UeInfo ueinfo) {
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{


			this.session.saveOrUpdate(ueinfo);			
			tx1.commit(); 


		}catch (Exception e){		 
			DBManager.log.error("[UE] - [UPDATE] UeInfo to DB.  ["+true+"], [ueid]:"+ueinfo.getUeid()+", [Cause]:"+e.getLocalizedMessage());
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
	 * @param ueid
	 * @param status
	 */
	private synchronized void updateUeStatusInfo(String ueid,String status) {
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			UeInfo temp = (UeInfo) this.session.load(UeInfo.class, ueid);
			temp.setStatus(status);		
			this.session.saveOrUpdate(temp);			
			tx1.commit(); 
		}catch (Exception e){		 
			DBManager.log.error("[UE] - [UPDATE] Ue Status Info to DB.   ["+false+"], [ueid]:"+ueid+", [Cause]:"+e.getLocalizedMessage());
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
	 * @param ueid
	 * @param time
	 * @param address
	 * @param status
	 * @param special
	 * @param domain
	 * @param kek
	 */
	private synchronized void updateUeSpecialInfo(String ueid,String special) {
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			UeInfo temp = (UeInfo) this.session.load(UeInfo.class, ueid);			
			if(temp.getSpecial() != special) 
				temp.setSpecial(special);			
			this.session.saveOrUpdate(temp);			
			tx1.commit(); 
		}catch (Exception e){		 
			DBManager.log.error("[UE] - [UPDATE] Ue Special Info to DB.  ["+false+"], [ueid]:"+ueid+", [Cause]:"+e.getLocalizedMessage());
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
}  
