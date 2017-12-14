package com.hunt.model.entity;

import java.util.Date;

/**  
 * 异常信息日志表  
 *   
 */  
public class SysExceptionLog {  
    /**  
     * 主键id  
     */  
    private Integer id;  
  
    /**  
     * 远程访问主机IP  
     */  
    private String ip;  
  
    /**  
     * 类名  
     */  
    private String className;  
  
    /**  
     * 方法名  
     */  
    private String methodName;  
  
    /**  
     * 异常类型  
     */  
    private String exceptionType;  
  
    /**  
     * 异常发生时间  
     */  
    private Date createTime;  
  
    /**  
     * 是否查看，1：未查看、2：已查看  
     */  
    private Byte isView;  
  
    /**  
     * 异常信息  
     */  
    private String exceptionMsg;

	public SysExceptionLog() {
		super();
	}

	public SysExceptionLog(Integer id, String ip, String className, String methodName, String exceptionType,
			Date createTime, Byte isView, String exceptionMsg) {
		super();
		this.id = id;
		this.ip = ip;
		this.className = className;
		this.methodName = methodName;
		this.exceptionType = exceptionType;
		this.createTime = createTime;
		this.isView = isView;
		this.exceptionMsg = exceptionMsg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Byte getIsView() {
		return isView;
	}

	public void setIsView(Byte isView) {
		this.isView = isView;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	
  
         
        
  
}  
