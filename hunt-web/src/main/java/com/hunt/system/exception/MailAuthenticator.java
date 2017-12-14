package com.hunt.system.exception;

import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  
  
/**  
 * 邮箱服务器登录验证器  
 *   
 * @author HY  
 * @date 创建时间：2017年2月28日  
 * @version  
 */  
public class MailAuthenticator extends Authenticator {  
  
    /**  
     * 用户名（登录邮箱地址）  
     */  
    private String username = null;  
  
    /**  
     * 密码  
     */  
    private String password = null;  
  
    public MailAuthenticator() {  
  
    }  
  
    public MailAuthenticator(String username, String password) {  
        this.username = username;  
        this.password = password;  
    }  
  
    /**  
     * 账户认证  
     */  
    @Override  
    protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(username, password);  
    }  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
}  
