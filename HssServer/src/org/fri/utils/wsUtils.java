package org.fri.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.fri.encrypt.AES;
import org.fri.encrypt.Coder;
import org.fri.encrypt.Md532;

import classes.CallLog;
import classes.Devices;
import classes.UeInfo;
import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ws ���߽ӿ���
 * @author zhaoronghui
 *
 */
public class wsUtils {

	public static Logger log = Logger.getRootLogger();//(wsUtils.class);

	/**
	 * @param device
	 * @return
	 */
	public static String DevicesObjList2String(Devices device){
		JSONObject json = new JSONObject();
		json.put("deviceid", device.getDeviceid());
		json.put("deviceip", device.getDeviceip());
		json.put("devicestatus", device.getDevicestatus());
		json.put("deviceload", device.getDeviceload());		
		return json.toString();			
	}
	
	/**
	 * @param list
	 * 	private String ueid;  
		private Date time;  
		private String address;  
		private String status;
		private String special;
		private String domain;
		private String kek;
	 * @return
	 */
	public static String UeInfoObjList2String(UeInfo ue){
		JSONObject json = new JSONObject();
		json.put("ueid", ue.getUeid());
		json.put("time", ue.getTime().toString());
		json.put("address", ue.getAddress());
		json.put("status", ue.getStatus());
		json.put("special", ue.getSpecial());
		json.put("domain", ue.getDomain());
		json.put("kek", ue.getKek());
		return json.toString();			
	}
	/**
	 * @param list
	 * 	private String ueid;  
		private Date time;  
		private String address;  
		private String status;
		private String special;
		private String domain;
		private String kek;
	 * @return
	 */
	public static String[] UeInfoObjList2StringArray(List<UeInfo> list){
		String[] ueArray = new String[list.size()];		
		
		JSONObject json = new JSONObject();
		for(int index=0; index<list.size();index++){
			json.put("ueid", list.get(index).getUeid());
			json.put("time", list.get(index).getTime().toString());
			json.put("address", list.get(index).getAddress());
			json.put("status", list.get(index).getStatus());
			json.put("special", list.get(index).getSpecial());
			json.put("domain", list.get(index).getDomain());
			json.put("kek", list.get(index).getKek());
			ueArray[index] = json.toString();
		}
		return ueArray;			
	}

	/**
	 * @param start
	 * @param end
	 * @param list
	 * @return
	 */
	public static String[] UeInfoObjListByTime2String(Date start, Date end, List<UeInfo> list) {
		// TODO Auto-generated method stub
		List<String> ueInfos = new ArrayList<String>();		
		JSONObject json = new JSONObject();
		for(int index=0; index<list.size();index++){			
			Date date = list.get(index).getTime();
			System.err.println("date:"+date);
			System.err.println("start:"+(date.getTime()-start.getTime()));
			System.err.println("end:"+(date.getTime()-end.getTime()));

			if((start.compareTo(date)<0 
					&& date.compareTo(end)<0))
			{
				json.put("ueid", list.get(index).getUeid());
				json.put("time", list.get(index).getTime().toString());
				json.put("address", list.get(index).getAddress());
				json.put("status", list.get(index).getStatus());
				json.put("special", list.get(index).getSpecial());
				json.put("domain", list.get(index).getDomain());
				json.put("kek", list.get(index).getKek());
				ueInfos.add(json.toString());
				//System.out.println(json.toString());
			}
		}
		return ueInfos.toArray(new String[ueInfos.size()]);	
	}


	/**
	 * @param list
	 * @return
	   	private String callid;
		private String caller;
		private String callee;
		private String status;
		private Date begain;
		private Date end;
		private String phrase;
		private String total;
		private String flow;
	 */
	public static String[] CallLogObjList2StringArray(List<CallLog> list){
		String[] logArr = new String[list.size()];		
		JSONObject json = new JSONObject();
		for(int index=0; index<list.size();index++){
			json.put("callid", list.get(index).getCallid());
			json.put("caller", list.get(index).getCaller());
			json.put("callee", list.get(index).getCallee());
			json.put("status", list.get(index).getStatus());
			json.put("begain", list.get(index).getBegain().toString());
			json.put("end",    list.get(index).getEnd().toString());
			json.put("phrase", list.get(index).getPhrase());
			json.put("total",  list.get(index).getTotal());
			json.put("flow",   list.get(index).getFlow());
			logArr[index] = json.toString();
			//System.out.println(json.toString());
		}
		//System.out.println(logArr.length);
		return logArr;			
	}
	/**
	private String callid;
	private String caller;
	private String callee;
	private String status;
	private Date begain;
	private Date end;
	private String phrase;
	private String total;
	private String flow;

	 * @param start
	 * @param end
	 * @param list
	 * @return
	 */
	public static String[] CallLogObjListByTime2String(Date start, Date end, List<CallLog> list) {
		// TODO Auto-generated method stub
		List<String> calllogs = new ArrayList<String>();	
		JSONObject json = new JSONObject();
		for(int index=0; index<list.size();index++){			
			Date date = list.get(index).getBegain();
			//System.err.println("date:"+date);
			//System.err.println("start:"+(date.getTime()-start.getTime()));
			//System.err.println("end:"+(date.getTime()-end.getTime()));
			if((start.compareTo(date)<0 && date.compareTo(end)<0))
			{		
				json.put("callid", list.get(index).getCallid());
				json.put("caller", list.get(index).getCaller());
				json.put("callee", list.get(index).getCallee());
				json.put("status", list.get(index).getStatus());
				json.put("begain", list.get(index).getBegain().toString());
				json.put("end",    list.get(index).getEnd().toString());
				json.put("phrase", list.get(index).getPhrase());
				json.put("total",  list.get(index).getTotal());
				json.put("flow",   list.get(index).getFlow());
				calllogs.add(json.toString());				
			}			
		}
		//System.err.println(calllogs);
		return calllogs.toArray(new String[calllogs.size()]);	
	}


	/**
	 * @param e
	 */
	public static void logExceptions(Exception e){	

		StringWriter sw = new StringWriter();
		PrintWriter  pw = new PrintWriter(sw);	
		pw.flush();
		sw.flush();

		if( pw!=null && sw!=null)
		{
			try {					
				e.printStackTrace(pw);	
				String save = sw.toString();
				log.info("----------------------- "); 
				log.info(save);
				log.info("----------------------- \n"); 
				sw.close();
				pw.close();

				/*
				 *    Send email to notify
				 */
				save = "[ TIME ]: [ "+ new Date()+" ]\n"+save;
				//new SendEmail().doSendNormalMail(save); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			} catch (Exception e1) {
				// TODO Auto-generated catch block				
			}		
		}					
	}
	/**
	 * @param o
	 * @return
	 */
	public static String reverseStr(String o){
		String k ="";
		if(o != null){
			for(int i = o.length()-1;i>=0;i--)
			{
				k+=o.charAt(i);	
			}
			return k;
		}
		return null;
	}


	/**
	 *  paras in : authStr = username+":"+realm+":token:"+nonce+":"+cnonce+":"+nc+":"+qop
	 **/		

	public static String createSessionKey(String callerId ,String calleeId ,String callid, String checkStr){

		/*********************************************
		 *    Session Key
		 ***********************************/
		String A1 =  Md532.GetMD5Code(callerId+":"+calleeId+":"+callid);
		String UsrKek =  Md532.GetMD5Code(A1+":"+checkStr);		
		return UsrKek;
	}
	/**
	 * @param CallerphoneNum
	 * @param IdCallId
	 * @param sessionKey
	 * @return
	 */
	public static String createTransTek(String CallerphoneNum,String sessionKey,String OriginalTek) {		
		//teklog.info("[TekInf]: CREATE TRANS TEK. [paras]: "+CallerphoneNum +" "+sessionKey);

		try{		
			if(sessionKey != null){ 
				// Encrypt CallTek by UsrKek
				if(OriginalTek!=null) {
					byte[] version = utilMethod.Tek_setVersionByte();
					byte[] time = utilMethod.Tek_setTimeStamp();
					byte[] phonenum = utilMethod.Tek_getPhoneNumIn5bytes(CallerphoneNum);
					byte[] checkBits = new byte[6];

					/*
					 *    iv ---1
					 */

					byte[] iv =utilMethod.byteMerger(version, time);
					iv = utilMethod.byteMerger(iv,phonenum);
					byte[] iv0 = new byte[6];
					for(int i =0;i<6;i++)
						iv0[i] = 0x00;
					iv = utilMethod.byteMerger(iv,iv0);

					/*
					 *  AES for check byte 6 bits  byte[]
					 */
					byte[] tek = AES.hexStringToBytes(OriginalTek);
					checkBits = AES.AESEncrypt4CheckBit(iv,tek);
					iv = new byte[16];
					/*
					 *   iv ----2       xor --> AES
					 */
					iv = utilMethod.byteMerger(version, time);
					//	System.err.println("merge 2 1 length:"+iv.length);
					iv = utilMethod.byteMerger(iv,phonenum);
					//	System.err.println("merge 2 2 length:"+iv.length);
					iv = utilMethod.byteMerger(iv,checkBits);
					//	System.err.println("merge 2 3 length:"+iv.length);

					/*	
					utilMethod.Tek_setVersionByte()+
					utilMethod.Tek_setTimeStamp()+
					utilMethod.Tek_getPhoneNumIn5bytes(phoneNum)+
					utilMethod.Tek_setCRCIn6Bytes(OriginalTek);
					 */

					//	System.out.println("iv:"+iv.length);
					//	System.out.println("OriginalTek:"+OriginalTek.length());		
					//  OriginalTek = utilMethod.Tek_XorTekStr(iv, OriginalTek);			
					//	System.out.println("UsrKey:"+UsrKek);

					byte[] tekByte = utilMethod.Tek_XorTekStr(iv, tek); 
					//	System.out.println("XOR:"+tekByte.length);	

					// System.err.println("tekByte xor length:"+tekByte.length);


					// UsrKek.getBytes("ISO-8859-1")
					//System.err.println("userkey:"+UsrKek);
					byte[] aes = AES.AESEncrypt(tekByte,AES.hexStringToBytes(sessionKey));
					//	System.err.println("aes length:"+aes.length);
					//	System.out.println("AES byte 2 Str:"+AES.parseByte2HexStr(aes));
					//	System.out.println("AES.length:"+aes.length);	
					//String retStr = AES.parseByte2HexStr(iv)+AES.parseByte2HexStr(aes);

					iv = utilMethod.byteMerger(iv,aes);
					//	System.err.println("key length:"+iv.length);
					// Coder.Base64Encrypt  0AB060EF344E955064B998BE43BF96F0  AVREg/gSIz9GALnhJlz6kEkoSzXel61RoK9fziaTztQ=
					// System.out.println("Base64:"+e);	48102119106103620712655112-99-115126114
					//  0870CEFCCF5946D581784A74AFEEC579
					//	String de64 = Coder.Base64Decrypt(e);
					//  tlog.info("AES:"+de64);	
					//	String o = AES.AESDecrypt(de64, UsrKek);
					//tlog.info("tek:"+o);		//  01761B3CBC3F422F4960F672E1779F21 MDE3NjFCM0NCQzNGNDIyRjQ5NjBGNjcyRTE3NzlGMjE=
					String e = Coder.Base64Encrypt(iv).trim();
					//String retStr = AES.parseByte2HexStr(iv);//new String(iv,"UTF-8");//
					//tlog.info("sessionkey:"+iv.length);
					//tlog.info("retStr:"+retStr);  
					//tlog.error("Tek base64 String :"+e);
					//System.out.println(iv.length);
					return e;	
				}
				else return null;
			}else{
				//System.err.println("[TekBasic]: CreateUsrEncryptTek error. user kek is null. [UEID]: "+CallerphoneNum);
				//teklog.error("[TekBasic]: CreateUsrEncryptTek error. user kek is null. [UEID]: "+CallerphoneNum);
				return null;
			}
		}catch (Exception e){
			utilMethod.logExceptions(e);
		}
		return null;
	}
	/**
	 * @param CallId
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	/**
	 * ѹ���ַ���
	 * @param data ��Ҫѹ�����ַ���
	 * @return ����ѹ������ַ���
	 * @throws IOException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static String compress(String s) throws IOException{ 
		ByteArrayInputStream input = new ByteArrayInputStream(s.getBytes("UTF-8"));  
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024); 
		GZIPOutputStream gzout = new GZIPOutputStream(output);  

		byte[] buf=new byte[1024]; 
		int number;   

		while ((number = input.read(buf)) != -1){  
			gzout.write(buf,0,number);  
		} 

		gzout.close();  
		input.close(); 

		String result =new BASE64Encoder().encode(output.toByteArray()); 

		output.close(); 

		return result; 
	} 

	/**
	 * ��ѹ���ַ���
	 * @param data ��Ҫ��ѹ���ַ���
	 * @return ��ѹ������ַ���
	 * @throws IOException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static String decompress(String data) throws IOException{ 
		ByteArrayOutputStream output = new ByteArrayOutputStream(1024); 
		ByteArrayInputStream input = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(data));  
		GZIPInputStream gzinpt = new GZIPInputStream(input); 
		byte[] buf = new byte[1024]; 
		int number = 0; 

		while((number = gzinpt.read(buf)) != -1){ 
			output.write(buf,0,number); 
		} 

		gzinpt.close(); 
		input.close(); 

		String result = new String(output.toString("UTF-8")); 
		output.close(); 
		return result;  
	} 

	/**
	 * ��ȡ�ͻ���IP��ַ������÷���IP���Ա���Ȩ�ޡ�
	 * ������axis������webservice
	 * @return
	 */
	public static String getClientIpAxis() {
		MessageContext mc = null;
		HttpServletRequest request = null;
		try {
			mc = MessageContext.getCurrentContext();
			if (mc == null)
				throw new Exception("�޷���ȡ��MessageContext");
			request = (HttpServletRequest) mc
					.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
			//System.out.println("remote  ip: " + request.getRemoteAddr());
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return request.getRemoteAddr();
	}
}
