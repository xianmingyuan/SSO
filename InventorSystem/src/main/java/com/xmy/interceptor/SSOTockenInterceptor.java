package com.xmy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 将单点登录服务器回调时传递的tocken参数放置到session域中
 */
public class SSOTockenInterceptor implements HandlerInterceptor {
	
	private final static transient Logger log = LoggerFactory.getLogger(SSOTockenInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		log.debug("SSOTockenInterceptor.afterCompletion()...");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		log.debug("SSOTockenInterceptor.postHandle()...");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		log.debug("SSOTockenInterceptor.preHandle()...");
		String tocken = (String) request.getParameter("tocken");
		if (null != tocken) {
			request.getSession().setAttribute("tocken", tocken);
		}
		return true;
	}

}
