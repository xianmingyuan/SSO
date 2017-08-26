package com.xmy.configuration;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xmy.realm.SSORealm;

@Configuration
public class ShiroConfiguration {

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	@Bean
	public Realm getRealm() {
		return new SSORealm();
	}
	
	@Bean(name = "securityManager")  
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {  
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getRealm());
        return securityManager;  
	}
	
	@Bean(name = "shiroFilter")  
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {  
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();  
        shiroFilterFactoryBean  
                .setSecurityManager(getDefaultWebSecurityManager());  
        shiroFilterFactoryBean.setLoginUrl("/login");  
        shiroFilterFactoryBean.setSuccessUrl("/index");  
        return shiroFilterFactoryBean;  
    }  
	
}
