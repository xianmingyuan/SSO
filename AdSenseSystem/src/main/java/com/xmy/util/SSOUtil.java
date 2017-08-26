package com.xmy.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 单点登录工具类
 */
@Component
public class SSOUtil {
	
	public SSOUtil() {
		log.debug("SSOUtil()");
	}

	private final static transient Logger log = LoggerFactory.getLogger(SSOUtil.class);

	@Value("${remote.sso.server.tocken.validate.addr}")
	private String remoteSSOServerTockenValidateAddr;

	public boolean validate(String tocken) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(remoteSSOServerTockenValidateAddr);
		try {
			
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String entityString = EntityUtils.toString(entity);
					log.debug("entityString : " + entityString);
				}
			} catch (ParseException | IOException e) {
				log.debug("EntityUtils.toString(entity) 异常");
			} finally {
				response.close();
			}
		} catch (IOException e) {
			log.debug("httpclient.execute(httpget) 异常");
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
			}
		}
		
		return false;
	}

}
