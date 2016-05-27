package com.adanac.framework.uaa.client.common.access.web;

import javax.servlet.http.HttpServletRequest;

import com.adanac.framework.uaa.client.core.entity.Identity;

/**
 * 预登录信息过滤器默认实现类<br>
 *
 * @author
 */
public class UaaPreAuthenticationFilter extends AbstractPreAuthenticationFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return request.getRemoteUser();
	}

	@Override
	protected Object successfulAuthentication(Object principal, Identity identity) {
		identity.setId(String.valueOf(principal));
		return identity;
	}

}
