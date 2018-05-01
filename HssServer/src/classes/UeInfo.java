package classes;

import java.util.Date;  

/**
 * UE对象类
 * @author zhaoronghui
 *
 */
public class UeInfo {  
	/**
	 * ue号码
	 */
	private String ueid;  
	/**
	 * 时间
	 */
	private Date time;  
	private String address;  
	private String status;
	private String special;
	private String domain;
	private String kek;
		
	/**
	 * 获取时间
	 * @return
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * 设置时间
	 * @param time
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * 获取地址信息
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置地址信息
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取UE状态
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取特殊自断
	 * @return
	 */
	public String getSpecial() {
		return special;
	}

	/**
	 * 设置特殊自断
	 * @param special
	 */
	public void setSpecial(String special) {
		this.special = special;
	}

	/**
	 * 获取域名
	 * @return
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * 设置域名
	 * @param domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/*
	 * 获取
	 * @return    
	 */
	public String getKek() {
		return kek;
	}

	public void setKek(String kek) {
		this.kek = kek;
	}


	public String getUeid() {
		return ueid;
	}


	public void setUeid(String ueid) {
		this.ueid = ueid;
	}  
 
}
