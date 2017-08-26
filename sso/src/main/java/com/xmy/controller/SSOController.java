package com.xmy.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmy.mapper.UserMapper;
import com.xmy.po.User;
import com.xmy.util.RedisClient;

@Controller
public class SSOController {

	private final static transient Logger log = LoggerFactory.getLogger(SSOController.class);

	@Autowired
	private RedisClient redisClient;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 跳转到统一登录界面
	 */
	@RequestMapping(path = { "login" }, method = { RequestMethod.GET })
	public String toSSOPage(String redirectUrl, HttpServletRequest request) {
		log.debug("SSOController.toSSOPage()...");
//		String tocken = (String) SecurityUtils.getSubject().getSession().getAttribute("tocken");
		String tocken = (String) request.getSession().getAttribute("tocken");
		log.debug("tocken : " + tocken);
		if (null != tocken && !tocken.equals("")) {
			//session域已记录当前用户已登录，进行tocken有效性检验
			try {
				String username = redisClient.get(tocken);
				if (null != username && !username.equals("")) {
					//tocken有效，直接返回回调地址
					return "redirect:http://" + redirectUrl + "?tocken=" + tocken;
				}
			} catch (Exception e) {
			}
		}
		request.setAttribute("redirectUrl", redirectUrl);
		return "login";
	}

	/**
	 * 登录
	 */
	@RequestMapping(path = { "login" }, method = { RequestMethod.POST })
	public String login(String redirectUrl, String username, String password, HttpServletRequest request) {
		log.debug("SSOController.login()...");
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken authenticationToken = new UsernamePasswordToken(username, password);
		try {
			subject.login(authenticationToken);
			String tocken = new Md5Hash(username, password).toString();
			try {
				redisClient.set(tocken, username);
//				subject.getSession().setAttribute("tocken", tocken);
//				log.debug("tocken : " + (String)subject.getSession().getAttribute("tocken"));
				request.getSession().setAttribute("tocken", tocken);
				if (null == redirectUrl || redirectUrl.equals("")) {
					//从统一认证系统登陆
					return "redirect:/index";
				}
				//返回跳转页面，不能直接重定向到不同域，需要js重定向，以便保存session信息
				request.setAttribute("redirectUrl", redirectUrl);
				request.setAttribute("tocken", tocken);
				return "redirect";
			} catch (Exception e) {
				log.debug(e.getMessage());
			}
		} catch (IncorrectCredentialsException e) {
		} catch (UnknownAccountException e) {
		} catch (AuthenticationException e) {
		} 
		//登陆失败
		request.setAttribute("redirectUrl", redirectUrl);
		return "login";
	}
	
	
	/**
	 * 根据tocken获取user
	 */
	@RequestMapping(path = { "user" }, method = { RequestMethod.GET })
	@ResponseBody
	public User user(String tocken) {
		try {
			String username = redisClient.get(tocken);
			User user = userMapper.selectByUsername(username);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

}
