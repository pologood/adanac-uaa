package com.adanac.framework.uaa.client.common.context;

import com.adanac.framework.uaa.client.core.entity.Identity;

/**
 * 权限上下文获取默认实现类
 * @author adanac
 * @version 1.0
 */
public class SecurityContextImpl implements SecurityContext {
	private Identity identity;

	public Identity getIdentity() {
		return this.identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
}
