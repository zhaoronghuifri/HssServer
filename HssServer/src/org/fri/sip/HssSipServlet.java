package org.fri.sip;

import javax.servlet.ServletContext;
import javax.servlet.sip.ServletTimer;
import javax.servlet.sip.SipApplicationSessionEvent;
import javax.servlet.sip.SipApplicationSessionListener;
import javax.servlet.sip.SipErrorEvent;
import javax.servlet.sip.SipErrorListener;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletContextEvent;
import javax.servlet.sip.SipServletListener;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.TimerListener;

import org.apache.log4j.PropertyConfigurator;

/**
 * Sipservlet ¿‡
 * @author zhaoronghui
 *
 */
public class HssSipServlet extends SipServlet implements SipServletListener, SipErrorListener,TimerListener, SipApplicationSessionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doInvite(SipServletRequest request) {
		// TODO: Add logic		
	}
	public void doOptions(SipServletRequest request) {
		// TODO: Add logic		
	}
	
	public void doBye(SipServletRequest request) {
		// TODO: Add logic
	}

	public void sessionCreated(SipApplicationSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sessionDestroyed(SipApplicationSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sessionExpired(SipApplicationSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void sessionReadyToInvalidate(SipApplicationSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void timeout(ServletTimer arg0) {
		// TODO Auto-generated method stub
		
	}

	public void noAckReceived(SipErrorEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void noPrackReceived(SipErrorEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void servletInitialized(SipServletContextEvent sce) {
		// TODO Auto-generated method stub
		//System.err.println("----------------------hss------------------");
		ServletContext sc = sce.getServletContext();		
		PropertyConfigurator.configure(sc.getRealPath("")+"/log4j.properties");
	}
}
