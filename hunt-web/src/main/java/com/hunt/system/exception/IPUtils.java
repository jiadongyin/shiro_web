package com.hunt.system.exception;

import javax.servlet.http.HttpServletRequest;  

/**  
 * IP地址操作工具类  
 *   
 * @author HY  
 * @date 创建时间：2017年2月24日  
 * @version  
 */  
public class IPUtils {  
  
    /**  
     * 获取远程访问主机ip地址  
     *   
     * 创建时间：2017年2月24日  
     *   
     * @author HY  
     * @param request  
     * @return  
     */  
    public static String getRemortIP(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
  
}  
