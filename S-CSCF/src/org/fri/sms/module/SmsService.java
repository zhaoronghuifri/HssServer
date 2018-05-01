
package org.fri.sms.module;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SmsService", targetNamespace = "http://service/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface SmsService {


    /**
     * 
     * @param phone
     * @param countstring
     * @return 
     */
    @WebMethod
    @Action(input = "http://service/SmsService/sendmsnRequest", output = "http://service/SmsService/sendmsnResponse")
    public int sendmsg(    		
        @WebParam(name = "phone", partName = "phone")
        String phone,
        @WebParam(name = "countstring", partName = "countstring")
        String countstring);

}