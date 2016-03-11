package com.adanac.framework.uaa.client.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 获取客户端IP地址信息通用类
 * @author adanac
 * @version 1.0
 */
public class IpAddrUtils {

	/**
	 * 获取客户端真实IP地址
	 *
	 * @param request
	 * @return 返回IP地址信息
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if (null == request) {
			return null;
		}

		String proxs[] = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
				"HTTP_X_FORWARDED_FOR" };

		String ip = null;

		for (String prox : proxs) {
			ip = request.getHeader(prox);
			if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
				continue;
			} else {
				break;
			}
		}

		if (StringUtils.isBlank(ip)) {
			return request.getRemoteAddr();
		}
		return ip;
	}
}
