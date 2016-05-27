package com.adanac.framework.uaa.client.common.access.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adanac.framework.uaa.client.common.intf.AuthorizationSelector;
import com.adanac.framework.uaa.client.common.intf.AuthorizationService;
import com.adanac.framework.uaa.client.common.user.CurrentUserInfoGetter;
import com.adanac.framework.uaa.client.common.util.ComponentManager;
import com.adanac.framework.uaa.client.core.entity.Authorization;

/**
 * URL请求权限控制过滤类
 * 
 * @author
 */
public class UaaSecurityFilter implements Filter {

	private static final String FILTER_APPLIED = "_uaa_security_filter_applied";

	private static final String SYSTEM_PARAM_NAME = "system";

	private static final String ERROR_PAGE_PARAM_NAME = "errorPage";

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 是否只过滤一次的标识（每个request） **/
	private boolean observeOncePerRequest = true;

	/** 错误页面 **/
	private String errorPage;

	/** 当前系统标识 **/
	private String system;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		setSystem(filterConfig.getInitParameter(SYSTEM_PARAM_NAME));
		setErrorPage(filterConfig.getInitParameter(ERROR_PAGE_PARAM_NAME));
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
		HttpServletRequest req = (HttpServletRequest) request;
		// 如果该request已经执行过此过滤器，且此过滤器设置为每个request只过滤一次，则跳过此次过滤
		if ((req != null) && (req.getAttribute(FILTER_APPLIED) != null) && observeOncePerRequest) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]FILTER_APPLIED: " + req.getAttribute(FILTER_APPLIED) + ",observeOncePerRequest: "
						+ observeOncePerRequest);
			}
			chain.doFilter(request, response);
		} else {
			if (req != null) {
				req.setAttribute(FILTER_APPLIED, Boolean.TRUE);
			}

			if (authorize(req)) {
				chain.doFilter(request, response);
			} else {
				HttpServletResponse resp = (HttpServletResponse) response;
				if (errorPage != null) {
					// Set the 403 status code.
					resp.setStatus(HttpServletResponse.SC_FORBIDDEN);

					// forward to error page.
					RequestDispatcher dispatcher = req.getRequestDispatcher(errorPage);
					dispatcher.forward(req, resp);
				} else {
					resp.sendError(HttpServletResponse.SC_FORBIDDEN, "");
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	private boolean authorize(HttpServletRequest request) {
		CurrentUserInfoGetter userInfo = ComponentManager.getInstance().getCurrentUserInfoGetter();
		String userId = userInfo.getCurrentUserId();
		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]UaaSecurityFilter authorize: userId is " + userId);
		}
		String uri = request.getServletPath();
		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]UaaSecurityFilter authorize: uri is " + uri);
		}
		if (StringUtils.isBlank(uri)) {
			return false;
		}
		if (request.getPathInfo() != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]UaaSecurityFilter authorize: PathInfo is " + request.getPathInfo());
			}
			uri += request.getPathInfo();
		}
		Map<String, Object> resourceMap = new HashMap<String, Object>();
		resourceMap.put(AuthorizationSelector.RESOURCE_MAP_DEFAULT_KEY, uri);

		// if (StringUtils.isBlank(userId)) {
		// return false;
		// }
		AuthorizationService service = getAuthorizationService();
		return service.hasPermission(userId, system, Authorization.AUTHORIZATION_TYPE_URL, resourceMap);
	}

	private AuthorizationService getAuthorizationService() {
		return (AuthorizationService) ComponentManager.getInstance()
				.getComponent(ComponentManager.AUTHORIZATION_SERVICE_BEAN_NAME);
	}

	public void setObserveOncePerRequest(boolean observeOncePerRequest) {
		this.observeOncePerRequest = observeOncePerRequest;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public void setSystem(String system) {
		this.system = system;
	}
}
