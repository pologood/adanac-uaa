package com.adanac.framework.uaa.client.common.user;

import java.util.Map;

import com.adanac.framework.uaa.client.common.util.ComponentManager;
import com.adanac.framework.uaa.client.common.util.SecurityContextHolder;
import com.adanac.framework.uaa.client.core.entity.Identity;

/**
 * 当前用户信息获取默认实现类
 * @author adanac
 * @version 1.0
 */
public class DefaultCurrentUserInfoGetter implements CurrentUserInfoGetter {
	public String getCurrentUserId() {
		Identity currentUser = SecurityContextHolder.getContext().getIdentity();
		if (currentUser == null) {
			return null;
		}
		return currentUser.getId();
	}

	@Override
	public Map<String, Object> getCurrentUserAttributes() {
		UserDetailsService userDetailsService = ComponentManager.getInstance().getUserDetailsService();
		if (userDetailsService != null) {
			return userDetailsService.getIdentityInfo(getCurrentUserId());
		}
		return null;
	}
}
