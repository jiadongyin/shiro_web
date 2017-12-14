package com.hunt.system.exception;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;  
  
/**  
 * 简单邮件（不带附件的邮件）发送器  
 *   
 * @author HY  
 * @date 创建时间：2017年2月28日  
 * @version  
 */  
public class SimpleMailSender {  
  
    private final transient Properties props = System.getProperties();  
  
    private transient MailAuthenticator authenticator;  
  
    private transient Session session;  
        // 从本地缓存中获取邮件相关配置信息  
    private static final String USERNAME = "yjd757454436@163.com";  
    private static final String PASSWORD = "Y421123JD7653";  
    //private static final String PASSWORD = "fmlcjoydiodhbbbc";  //QQ邮箱授权码
   
    public SimpleMailSender() {  
        init();  
    }  
  
    /**  
     * 初始化  
     *   
     * 创建时间：2017年2月28日  
     *   
     * @author HY  
     * @param smtpHostName  
     *            SMTP服务器地址  
     * @param username  
     *            发送邮件的用户名（邮箱地址）  
     * @param password  
     *            密码  
     */  
    private void init() {  
        // 初始化Properties  
         // 使用smtp：简单邮件传输协议  
         props.put("mail.smtp.host", "smtp.163.com");//存储发送邮件服务器的信息  
         props.put("mail.smtp.auth", "true");//同时通过验证    
        // 验证发送者账户密码  
        authenticator = new MailAuthenticator(USERNAME, PASSWORD);  
        // 创建Session  
        session = Session.getInstance(props, authenticator);  
    }  
  
    /**  
     * 发送邮件（单发）  
     *   
     * 创建时间：2017年2月28日  
     *   
     * @author HY  
     * @param recipient  
     *            收件人邮箱地址  
     * @param subject  
     *            邮件主题  
     * @param content  
     *            邮箱内容  
     * @throws AddressException  
     * @throws MessagingException  
     */  
    public void send(String recipient, String subject, Object content) throws AddressException, MessagingException {  
        // 1.创建mime类型邮件  
        final Message message = new MimeMessage(session);  
        // 2.设置发件人  
        message.setFrom(new InternetAddress(authenticator.getUsername()));  
        // 3.设置收件人  
        message.setRecipient(RecipientType.TO, new InternetAddress(recipient));  
        // 4.设置邮件主题  
        message.setSubject(subject);  
        // 5.设置邮件内容  
        message.setContent(content.toString(), "text/html;charset=utf-8");  
        // 6.发送  
        Transport.send(message);  
    }  
  
    /**  
     * 群发邮件  
     *   
     * 创建时间：2017年2月28日  
     *   
     * @author HY  
     * @param recipients  
     *            多个收件人（邮箱地址集合）  
     * @param subject  
     *            邮件主题  
     * @param content  
     *            邮件内容  
     * @throws AddressException  
     * @throws MessagingException  
     */  
    public void send(List<String> recipients, String subject, Object content) throws AddressException, MessagingException {  
        // 1.创建mime类型邮件  
        final Message message = new MimeMessage(session);  
        // 2.设置发件人  
        message.setFrom(new InternetAddress(authenticator.getUsername()));  
        // 3.设置收件人  
        final int num = recipients.size();  
        InternetAddress[] addresses = new InternetAddress[num];  
        for (int i = 0; i < num; i++) {  
            addresses[i] = new InternetAddress(recipients.get(i));  
        }  
        message.setRecipients(RecipientType.TO, addresses);  
        // 4.设置邮件主题  
        message.setSubject(subject);  
        // 5.设置邮件内容  
        message.setContent(content.toString(), "text/html;charset=utf-8");  
        // 6.发送  
        Transport.send(message);  
    }  
  
}  
