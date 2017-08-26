package com.xmy.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.xmy.mapper.UserMapper;
import com.xmy.po.User;

public class SSORealm extends AuthorizingRealm {

	private final static String NAME = "ssoRealm";
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	
		AuthenticationInfo authenticationInfo = null;
		String username = null;
		
		Object principal = token.getPrincipal();
		if (principal instanceof String) {
			username = (String) principal;
		}
		try {
			User user = userMapper.selectByUsername(username);
			if (null == user) 
				throw new UnknownAccountException();
			authenticationInfo = new SimpleAccount(user.getUsername()	, user.getPassword(), NAME);
		} catch (Exception e) {
			throw new AuthenticationException();
		}
		return authenticationInfo;
	}

}
