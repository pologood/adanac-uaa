package com.adanac.framework.uaa.client.common.user;

import java.util.Map;

/**
 * 用户信息服务默认实现类
 * @author adanac
 * @version 1.0
 */
public class UaaUserDetailsService implements UserDetailsService {
	@Override
	public Map<String, Object> getIdentityInfo(String userId) {
		return null;
	}
}
