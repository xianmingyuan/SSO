package com.xmy.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xmy.util.CommonMethodUtil;
import com.xmy.util.SSOUtil;

@Controller
public class IndexController {

	private final static transient Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Value("${remote.sso.server.login.addr}")
	private String remoteSSOServerLoginAddr;
	
	@Autowired
	private SSOUtil ssoUtil;
	
	@RequestMapping("index")
	public String index(HttpServletRequest request) {
		
		String tocken = null;
		Object attribute = request.getSession().getAttribute("tocken");
		if (attribute instanceof String) {
			tocken = (String) attribute;
		}
		
		if (null == tocken || tocken.equals("")) {
			//未登录
			log.debug("redirect:" + remoteSSOServerLoginAddr + "?redirectUrl=" + CommonMethodUtil.getAbsoluteServletPath(request));
			return "redirect:" + remoteSSOServerLoginAddr + "?redirectUrl=" + CommonMethodUtil.getAbsoluteServletPath(request);
		}
		if (!ssoUtil.validate(tocken)) {
//			log.debug("redirect:" + remoteSSOServerLoginAddr + "?redirectUrl=" + CommonMethodUtil.getAbsoluteServletPath(request));
//			return "redirect:" + remoteSSOServerLoginAddr + "?redirectUrl=" + CommonMethodUtil.getAbsoluteServletPath(request);
		}
		return "index";
	}
	
}
