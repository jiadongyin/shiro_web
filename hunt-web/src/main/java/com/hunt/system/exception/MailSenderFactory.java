package com.hunt.system.exception;

/**  
 * 邮件发送工厂  
 *   
 * @author HY  
 * @date 创建时间：2017年2月28日  
 * @version  
 */  
public class MailSenderFactory {  
  
    private static SimpleMailSender getInstance = null;  
  
    private MailSenderFactory() {  
  
    }  
  
    public static SimpleMailSender getSender() {  
        if (getInstance == null) {  
            getInstance = new SimpleMailSender();  
        }  
        return getInstance;  
    }  
  
}  
