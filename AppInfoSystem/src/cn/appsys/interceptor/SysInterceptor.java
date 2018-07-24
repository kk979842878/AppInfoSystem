package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.appsys.interceptor.SysInterceptor;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;

public class SysInterceptor {

	private Logger logger = Logger.getLogger(SysInterceptor.class);

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		logger.debug("SysInterceptor preHandle ==========================");
		HttpSession session = request.getSession();	
		
		DevUser devUser = (DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
		
		if(null != devUser){ //dev SUCCESS
			return true;
		}else{
			response.sendRedirect(request.getContextPath()+"/403.jsp");
			return false;
		}
		
	}
}
