package com.adanac.framework.uaa.client.common.access.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.bn.framework.uaa.common.model.Identity;
//import com.bn.framework.uaa.common.util.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adanac.framework.uaa.client.common.util.SecurityContextHolder;
import com.adanac.framework.uaa.client.core.entity.Identity;

/**
 * 预登录信息过滤器基类<br>
 *
 * @author
 */
public abstract class AbstractPreAuthenticationFilter implements Filter {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (requiresAuthentication((HttpServletRequest) request)) {
			doAuthenticate((HttpServletRequest) request, (HttpServletResponse) response);
		}

		chain.doFilter(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	private boolean requiresAuthentication(HttpServletRequest request) {
		Identity currentUser = SecurityContextHolder.getContext().getIdentity();
		if (currentUser == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]requiresAuthentication: currentUser is null.");
			}
			return true;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]requiresAuthentication: currentUser is " + currentUser.getId());
		}

		Object principal = getPreAuthenticatedPrincipal(request);
		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]requiresAuthentication: principal is " + principal);
		}
		if (currentUser.getId().equals(principal)) {
			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"[UAALOG]Pre-authenticated principal has changed to " + principal + " and will be reauthenticated");
		}
		return true;
	}

	private void doAuthenticate(HttpServletRequest request, HttpServletResponse response) {

		Object principal = getPreAuthenticatedPrincipal(request);

		if (principal == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]No pre-authenticated principal found in request");
			}
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]preAuthenticatedPrincipal = " + principal + ", trying to authenticate");
		}
		Identity identity = new Identity();
		successfulAuthentication(principal, identity);
		SecurityContextHolder.getContext().setIdentity(identity);
	}

	protected abstract Object getPreAuthenticatedPrincipal(HttpServletRequest request);

	protected abstract Object successfulAuthentication(Object principal, Identity identity);
}
