package classes;

import org.fri.utils.wsUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;

/**
 * 相关设备信息类
 * @author zhaoronghui
 *
 */
public class DevicesDBManager {  

	/**
	 * 
	 */
	SessionFactory factory;
	Session session;  

	/**
	 * @param callid
	 * @param status
	 * @param caller
	 * @param callee
	 */
	public  void updateDeviceInfo(String deviceid,String deviceip,String devicestatus,int deviceload) {  
		this.updateDevice(deviceid, deviceip, devicestatus, deviceload);
	}  	

	/**
	 * @param ueid
	 * @return
	 */
	public String selectDevicesById(String deviceid){
		String ret = this.selectDeviceInfo(deviceid);
		return ret;		
	}	
	/**
	 * @return
	 */
	private String selectDeviceInfo(String deviceid) {
		// TODO Auto-generated method stub
		this.factory = HibernateUtil.getSessionFactory();
		this. session = this.factory .openSession();
		
		Transaction tx1 = this.session.beginTransaction(); 
		try{
			Criteria crit = this.session.createCriteria(Devices.class);
			crit.add(Restrictions.eq("deviceid", deviceid));			
			Devices device = (Devices)crit.uniqueResult();				
			tx1.commit(); 			
			return wsUtils.DevicesObjList2String(device);
		}catch (Exception e){}finally{
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
	 * @param callid
	 * @param status
	 * @param caller
	 * @param callee
	 */
	private synchronized void updateDevice(String deviceid,String deviceip,String devicestatus,int deviceload) {  
		try{
			this.factory = HibernateUtil.getSessionFactory();
			this. session = this.factory .openSession();
			Transaction tx2  = this.session.beginTransaction(); 
			Devices device = new Devices();
			device.setDeviceid(deviceid);	
			device.setDeviceip(deviceip);
			device.setDevicestatus(devicestatus);
			device.setDeviceload(deviceload);
			
			this.session.saveOrUpdate(device);  
			tx2.commit();  
			DBManager.log.info("[Device] - [INSERT] Device to DB.  ["+true+"], [deviceid] :"+deviceid);
		}catch(Exception e){
			DBManager.log.error("[Device] - [INSERT] Device to DB.  ["+false+"], [deviceid] :"+deviceid+" , [Cause]:"+e.getLocalizedMessage());
		}finally {
			if(this.session.isOpen())
			{
				this.session.flush();
				this.session.clear();
				this.session.close();
			}
			this.factory.close();
		}	
	}  	

}  
