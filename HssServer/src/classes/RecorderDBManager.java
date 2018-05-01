package classes;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

/**
 * 录音使能类方法
 * @author zhaoronghui
 *
 */
public class RecorderDBManager {  

	/**
	 * 
	 */
	SessionFactory factory;
	Session session ;  

	
	/**
	 * 设置录像
	 * @param ueid
	 * @param opt
	 * @return
	 */
	public  Object setToDB(String ueid,String opt){
		Object obj = this.setRecorderToDB(ueid, opt);
		return obj;
	}

	/**
	 * 查询是否录像
	 * @param ids
	 * @return
	 */
	public  Object selectFromDB(String ids){
		Object obj = this.selectRecorderFromDB(ids);
		return obj;
	}

	
	/**
	 * 设置录像开关
	 * @param title
	 * @param theDate
	 */
	private Object setRecorderToDB(String ueid,String opt) { 
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		Object ret = new Object();
		try {
			Recorder temp = new Recorder();//(Recorder)( this.session.load(Recorder.class, ueid)); 
			temp.setUeid(ueid);
			temp.setRecorder(opt);
			if(temp!=null)
				ret = temp.getRecorder();
			
			this.session.saveOrUpdate(temp);	
			tx1.commit();

			DBManager.log.info("[Recorder] - [SELECT] SET REC KEY IN DB.["+true+"], [ueid] :"+ueid);

			return ret;
		}catch (Exception e){
			DBManager.log.error("[Recorder] - [SELECT] SET REC KEY IN DB.[false], [reason] :"+e.getLocalizedMessage());
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
	 * @param title
	 * @param theDate
	 */
	private Object selectRecorderFromDB(String ueid) {  	
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		Object ret = new Object();
		try {

			Recorder temp = (Recorder)( this.session.load(Recorder.class, ueid)); 			
			if(temp!=null)
				ret = temp.getRecorder();
			tx1.commit();

			DBManager.log.info("[Recorder] - [SELECT] - REC KEY from DB.["+true+"], [ueid] :"+ueid);

			return ret;
		}catch (Exception e){
			DBManager.log.error("[Recorder] - [SELECT] - REC KEY from DB.[false], [reason] :"+e.getLocalizedMessage());
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
}  
