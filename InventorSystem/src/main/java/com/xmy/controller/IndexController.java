package com.xmy.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xmy.util.CommonMethodUtil;

@Controller
public class IndexController {

	@Value("${remote.sso.server.login.addr}")
	private String remoteSsoServerLoginAddr;
	
	@RequestMapping(path={"index"}, method={RequestMethod.GET})
	public String index(HttpServletRequest request) {
		String tocken = (String) request.getSession().getAttribute("tocken");
		if (null == tocken || tocken.equals("")) {
			//未登录，需重定向到统一登录服务器登录
			return "redirect:" + remoteSsoServerLoginAddr + "?redirectUrl=" + CommonMethodUtil.getAbsoluteServletPath(request);
		}
		//检验tocke的有效性
		return "index";
	}
	
}
