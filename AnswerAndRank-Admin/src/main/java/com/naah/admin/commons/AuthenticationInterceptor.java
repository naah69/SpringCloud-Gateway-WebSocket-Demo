package com.naah.admin.commons;

import com.naah.admin.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Properties;

/**
 * 拦截器
 * @author dazhi
 *
 */

public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object mothed) throws Exception {
		//检查访问路径是否是公共路径
		Properties initProperties = PropertiesUtils.initProperties("game.properties");
		String publicPaths = initProperties.get("publicPath").toString();
		String[] publicPath = null;
		if(publicPaths == null){
			logger.error("配置文件未配置任何输入");
		}else {
			publicPath = publicPaths.split(",");
			for (String path : publicPath) {
				if(request.getServletPath().equals(path)){
					//路径相等，放行
					return true;
				}
			}
		}

		//不是公共路径,判断是否登录
		HttpSession session = request.getSession();
		Object attribute = session.getAttribute("user");
		if(attribute == null){
			//跳转到登录页面
			request.getRequestDispatcher("/admin/adminPage").forward(request, response);
			//未授权，拦截
			return false;
		}else{
			//已授权，放行
			return true;
		}
	}

}
