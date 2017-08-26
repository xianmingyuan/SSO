package com.xmy.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.xmy.interceptor.SSOTockenInterceptor;

/**
 * 拦截器
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	private final static transient Logger log = LoggerFactory.getLogger(CustomWebMvcConfigurerAdapter.class);
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.debug("CustomWebMvcConfigurerAdapter.addInterceptors()");
		registry.addInterceptor(new SSOTockenInterceptor()).addPathPatterns("/**");
	}

}
