package com.xmy.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(path={"index"}, method={RequestMethod.GET})
	public String toIndexPage() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			return "index";
		}
		return "redirect:/login";
	}
	
}
