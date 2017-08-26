package com.xmy.util;

import javax.servlet.http.HttpServletRequest;

public class CommonMethodUtil {

	public static String getAbsoluteServletPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		int serverPort = request.getServerPort();
		String serverName = request.getServerName();
		return serverName + ":" + serverPort + servletPath;
	}
	
}
