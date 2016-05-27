package com.adanac.framework.uaa.client.common.access.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adanac.framework.uaa.client.common.intf.AuthorizationService;
import com.adanac.framework.uaa.client.common.user.CurrentUserInfoGetter;
import com.adanac.framework.uaa.client.common.util.ComponentManager;

/**
 * 权限控制标签<br>
 * 对页面元素进行权限判断，权限判定通过时，显示标签中间的内容，反之，忽略
 * 
 * @author
 */
public class UaaAuthorizeTag extends TagSupport {

	private static final long serialVersionUID = -871584877897814626L;

	/** 角色变量分隔符 **/
	private static final String roleSeperate = ",";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 标签属性，表示需要判断是否包含属性值中所有角色 **/
	private String ifAllGranted;

	/** 标签属性，表示需要判断是否包含属性值中任一角色 **/
	private String ifAnyGranted;

	/** 标签属性，表示需要判断是否不包含属性值中任一角色 **/
	private String ifNotGranted;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {

		boolean hasTextAllGranted = StringUtils.isNotBlank(getIfAllGranted());
		boolean hasTextAnyGranted = StringUtils.isNotBlank(getIfAnyGranted());
		boolean hasTextNotGranted = StringUtils.isNotBlank(getIfNotGranted());

		if ((!hasTextAllGranted) && (!hasTextAnyGranted) && (!hasTextNotGranted)) {
			return SKIP_BODY;
		}

		if (authorizeUsingGrantedAuthorities(hasTextAllGranted, hasTextAnyGranted, hasTextNotGranted)) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	private boolean authorizeUsingGrantedAuthorities(boolean hasAllGranted, boolean hasAnyGranted,
			boolean hasNotGranted) {
		// boolean hasAllGranted = StringUtils.isNotBlank(getIfAllGranted());
		// boolean hasAnyGranted = StringUtils.isNotBlank(getIfAnyGranted());
		// boolean hasNotGranted = StringUtils.isNotBlank(getIfNotGranted());

		// 如果没标明任何属性，默认无权限
		if ((!hasAllGranted) && (!hasAnyGranted) && (!hasNotGranted)) {
			return false;
		}

		CurrentUserInfoGetter userInfoGetter = ComponentManager.getInstance().getCurrentUserInfoGetter();
		// 无法获取当前用户信息，默认无权限
		if (userInfoGetter == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]authorizeUsingGrantedAuthorities: userInfoGetter is null.");
			}
			return false;
		}
		String userId = userInfoGetter.getCurrentUserId();
		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]authorizeUsingGrantedAuthorities: userId is " + userId);
		}
		boolean result = true;
		// 三种属性之间为且的关系，即标明多个属性时，需要每个属性对应的判断全为true才行
		if (hasAllGranted && !isContainAll(userId, toRoles(getIfAllGranted()))) {
			result = false;
		}

		if (hasAnyGranted && !isContainAny(userId, toRoles(getIfAnyGranted()))) {
			result = false;
		}

		if (hasNotGranted && !isContainNone(userId, toRoles(getIfNotGranted()))) {
			result = false;
		}
		return result;
	}

	private boolean isContainAll(String userId, String[] roleIds) {
		AuthorizationService service = getAuthorizationService();
		if (service != null) {
			return service.hasAllRoles(userId, roleIds);
		}
		return false;
	}

	private boolean isContainAny(String userId, String[] roleIds) {
		AuthorizationService service = getAuthorizationService();
		if (service != null) {
			return service.hasAnyRole(userId, roleIds);
		}
		return false;
	}

	private boolean isContainNone(String userId, String[] roleIds) {
		AuthorizationService service = getAuthorizationService();
		if (service != null) {
			return !service.hasAnyRole(userId, roleIds);
		}
		return false;
	}

	private String[] toRoles(String roleStr) {
		if (StringUtils.isBlank(roleStr)) {
			return null;
		}
		return roleStr.split(roleSeperate);
	}

	private AuthorizationService getAuthorizationService() {
		return (AuthorizationService) ComponentManager.getInstance()
				.getComponent(ComponentManager.AUTHORIZATION_SERVICE_BEAN_NAME);
	}

	public String getIfAllGranted() {
		return ifAllGranted;
	}

	public void setIfAllGranted(String ifAllGranted) {
		this.ifAllGranted = ifAllGranted;
	}

	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	public void setIfAnyGranted(String ifAnyGranted) {
		this.ifAnyGranted = ifAnyGranted;
	}

	public String getIfNotGranted() {
		return ifNotGranted;
	}

	public void setIfNotGranted(String ifNotGranted) {
		this.ifNotGranted = ifNotGranted;
	}

}
