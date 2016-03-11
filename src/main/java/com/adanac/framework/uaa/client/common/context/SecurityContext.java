package com.adanac.framework.uaa.client.common.context;

import com.adanac.framework.uaa.client.core.entity.Identity;

/**
 * 权限上下文获取接口
 * @author adanac
 * @version 1.0
 */
public interface SecurityContext {

	Identity getIdentity();

	void setIdentity(Identity identity);
}
