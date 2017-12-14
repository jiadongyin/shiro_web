package com.hunt.system.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hunt.model.entity.SysExceptionLog;
import com.hunt.service.SysExceptionLogService;
import com.hunt.service.SystemService;
import com.hunt.util.ResponseCode;
import com.hunt.util.Result;
import com.hunt.util.StringUtil;  
  
/**  
 * 全局异常处理器  
 *   
 * @author HY  
 * @date 创建时间：2017年2月24日  
 * @version  
 */  
public class GlobalExceptionHandler implements HandlerExceptionResolver {  
  
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);  
  
    @Autowired
    private SystemService systemService;
    @Autowired  
    private SysExceptionLogService sysExceptionLogService;  
  
    @Override  
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {  
        Map<String, Object> model = new HashMap<String, Object>();  
        model.put("ex", ex);  
  
        if (handler instanceof HandlerMethod) {  
            LOGGER.info(">>>>>>系统异常，记录异常信息到数据库------start------");  
            // 远程访问IP  
            String ip = IPUtils.getRemortIP(request);  
            HandlerMethod handlerMethod = (HandlerMethod) handler;  
            String className = handlerMethod.getBeanType().getName();  
            String methodName = handlerMethod.getMethod().getName();  
            StringWriter sw = new StringWriter();  
            ex.printStackTrace(new PrintWriter(sw, true));  
  
            // 插入异常日志到数据库  
            SysExceptionLog log = new SysExceptionLog();  
            log.setIp(ip);  
            log.setClassName(className);  
            log.setMethodName(methodName);  
            log.setExceptionType(ex.getClass().getSimpleName());  
            log.setExceptionMsg(sw.toString()); // 异常详细信息  
            log.setIsView((byte) 1); // 默认未读,1:为查看、2：已查看  
            log.setCreateTime(new Date());  
            this.sysExceptionLogService.insertExceptionLogSelective(log);  
            LOGGER.info(">>>>>>系统异常，记录异常信息到数据库------end------");  
  
            // 此处先写死。后期完善，接收人从数据库配置中获取  
            try {  
                String recipient = "757454436@qq.com"; //收件人地址 
                String subject = "【hunt-admin系统异常通知】";  
                Object content = "管理员，您好：<br/>   hunt-admin系统出现异常，请立即登录后台系统：“系统管理”--“异常日志管理”进行查看。<br/>   "  
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());  
                MailSenderFactory.getSender().send(recipient, subject, content);  
            } catch (AddressException e) {  
                e.printStackTrace();  
            } catch (MessagingException e) {  
                e.printStackTrace();  
            }  
        }  
        
        if (request.getHeader("Accept").contains("application/json")) {
        	LOGGER.debug("qingqiu");
            Result result = Result.error();
            if (ex instanceof IncorrectCredentialsException) {
                result = Result.instance(ResponseCode.password_incorrect.getCode(), ResponseCode.password_incorrect.getMsg());
                //账号不存在
            } else if (ex instanceof UnknownAccountException) {
                result = Result.instance(ResponseCode.unknown_account.getCode(), ResponseCode.unknown_account.getMsg());
                //未授权
            } else if (ex instanceof UnauthorizedException) {
                result = Result.instance(ResponseCode.unauthorized.getCode(), ResponseCode.unauthorized.getMsg());
                //未登录
            } else if (ex instanceof UnauthenticatedException) {
                result = Result.instance(ResponseCode.unauthenticated.getCode(), ResponseCode.unauthenticated.getMsg());
                //缺少参数
            } else if (ex instanceof MissingServletRequestParameterException) {
                result = Result.instance(ResponseCode.missing_parameter.getCode(), ResponseCode.missing_parameter.getMsg());
                //参数格式错误
            } else if ((ex instanceof MethodArgumentTypeMismatchException)) {
                result = Result.instance(ResponseCode.param_format_error.getCode(), ResponseCode.param_format_error.getMsg());
                //ip限制
            } else if (ex.getCause().getMessage().contains("system.exception.ForbiddenIpException")) {
                result = Result.instance(ResponseCode.forbidden_ip.getCode(), ResponseCode.forbidden_ip.getMsg());
                //其他错误
            }
            //调试时输出异常日志
            if (systemService.selectDataItemByKey("error_detail", 2L).equals("true")) {
                result.setData(StringUtil.exceptionDetail(ex));
            }
            try {
            	response.setCharacterEncoding("UTF-8");
            	response.setContentType("application/json;charset=UTF-8");
            	response.getWriter().append(new Gson().toJson(result));
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
            String basePath = systemService.selectDataItemByKey("basePath", 4L);
            String url = "/error/internalError";

            if (ex instanceof UnauthorizedException) {
                url = "/error/unAuthorization";
            }
            //response.setCharacterEncoding("UTF-8");
            //response.setContentType("text/html;charset=UTF-8");
            //response.sendRedirect(basePath + url);
			return new ModelAndView(basePath +url, model);
        }

        return null;  
    }  
  
}  
