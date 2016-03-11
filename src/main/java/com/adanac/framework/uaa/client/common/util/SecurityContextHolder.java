package com.adanac.framework.uaa.client.common.util;

import org.springframework.util.Assert;

import com.adanac.framework.uaa.client.common.context.SecurityContextImpl;

/**
 * 当前用户信息获取默认实现类
 * @author adanac
 * @version 1.0
 */
public class SecurityContextHolder {
	private static ThreadLocal<SecurityContextImpl> contextHolder = new ThreadLocal<SecurityContextImpl>();

	// public static void clearContext() {
	// contextHolder.remove();
	// }

	public static SecurityContextImpl getContext() {
		SecurityContextImpl ctx = contextHolder.get();

		if (ctx == null) {
			ctx = createEmptyContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}

	public static void setContext(SecurityContextImpl context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}

	public static SecurityContextImpl createEmptyContext() {
		return new SecurityContextImpl();
	}
}
