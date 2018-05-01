package classes;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;  

/**
 * hibernate 驱动类
 * @author zhaoronghui
 *
 */
public class HibernateUtil {  

/*	private SessionFactory sessionFactory = buildSessionFactory();  

	private static SessionFactory buildSessionFactory() {  
		try {  
			// Create the SessionFactory from hibernate.cfg.xml  
			return new Configuration().configure().buildSessionFactory();  
		}  
		catch (Exception ex) {          	         
		}
		return null;  
	}  

	public SessionFactory getSessionFactory() {  
		return sessionFactory;         
	}*/


	public static SessionFactory getSessionFactory(){
		Configuration configuration  = new Configuration();
		configuration.configure();
		SessionFactory factory = configuration.buildSessionFactory();
		
		return factory;	
	}


}  
