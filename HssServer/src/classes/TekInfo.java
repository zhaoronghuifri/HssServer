package classes;

import java.util.Date;


/**
 * @author zhaoronghui
 * TEK class
 */
public class TekInfo {
	private Date time; 
	private String callid;  
	private String caller;  
	private String callee; 
	private String tek;
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * @return the tek
	 */
	public String getTek() {
		return tek;
	}
	/**
	 * @param tek the tek to set
	 */
	public void setTek(String tek) {
		this.tek = tek;
	}
	
	public String getCallid() {
		return callid;
	}
	public void setCallid(String callid) {
		this.callid = callid;
	}
	public String getCaller() {
		return caller;
	}
	public void setCaller(String caller) {
		this.caller = caller;
	}
	public String getCallee() {
		return callee;
	}
	public void setCallee(String callee) {
		this.callee = callee;
	}  	
}
