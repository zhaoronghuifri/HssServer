package classes;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * TEK manager class
 * @author zhaoronghui
 *
 */
public class TekInfoDBManager {  

	/**
	 * initial
	 */
	SessionFactory factory;
	/**
	 * session
	 */
	Session session ;  


	/**
	 * 向数据库插入一条TEK记录
	 * @param callid 会话标示
	 * @param caller  主叫方
	 * @param callee  被叫方
	 * @param tek    tek信息
	 */
	public  synchronized void insertTekInfoToDB(String callid,String caller, String callee,String tek) {  
		this.insertTekInfo(callid,caller,callee,tek);  
	}  



	/**
	 * 从数据库查询一条TEK信息
	 * @param callid  会话标识
	 * @return
	 */
	public  String selectTekInfoFromDB(String callid){	
		String tek = this.selectTekInfo(callid);
		return tek;
	}

	/**
	 * 从数据库删除一条TEK记录
	 * @param callid
	 * @return
	 */
	public  boolean deleteTekInfoFromDB(String callid){
		try{
			this.deleteTekinfo(callid);
			return true;		
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}


	
	/**
	 * 插入记录的实现方法
	 * @param callid
	 * @param caller
	 * @param callee
	 * @param tek
	 */
	private synchronized void insertTekInfo(String callid,String caller,String callee, String tek) {  
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			Transaction tx2  = this.session.beginTransaction(); 	
			TekInfo tekInfo = new TekInfo();		
			tekInfo.setCallid(callid);
			tekInfo.setCaller(caller);
			tekInfo.setCallee(callee);
			tekInfo.setTek(tek);
			tekInfo.setTime(new Date());
			this.session.saveOrUpdate(tekInfo);  
			tx2.commit();  			
		}catch(Exception e){
			e.printStackTrace();
			DBManager.log.error("[Tek] - [INSERT] TekInfo to DB. ["+false+"], [callid] :"+callid+", [Cause]:"+e.getLocalizedMessage());
		}	finally {
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
	 * 查询TEK信息的接口方法
	 * @param callid
	 * @return
	 */
	private String selectTekInfo(String callid) {  
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();		
		Transaction tx1 = this.session.beginTransaction(); 
		try {
			TekInfo temp = (TekInfo)( this.session.load(TekInfo.class, callid)); 			
			String ret = null;
			if(temp!=null)
				ret = temp.getTek();
			tx1.commit();						
			//DBManager.log.info("[Tek] - [SELECT] TekInfo from DB.["+true+"], [callid] :"+callid);
			return ret;
		}catch (Exception e){
			DBManager.log.error("[Tek] - [SELECT] TekInfo from DB.["+false+"], [callid] :"+callid+", [Cause]:"+e.getLocalizedMessage());
		}finally{
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
			}
			this.factory.close();

		}
		return null;			
	}  


	/**
	 * 删除tek记录的方法
	 * @param callid
	 * @return
	 */
	private  boolean deleteTekinfo(String callid) {
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			TekInfo del = (TekInfo)(this.session.get(TekInfo.class, callid)); 
			if(del!=null)
				this.session.delete(del);  
			tx1.commit(); 			
			//DBManager.log.info("[Tek] - [DELETE] TekInfo from DB.["+true+"], [callid] :"+callid);			
			return true;
		}catch (Exception e){			
			DBManager.log.error("[Tek] - [DELETE] TekInfo from DB.["+false+"], [callid] :"+callid+", [Cause]:"+e.getLocalizedMessage());
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
}  
