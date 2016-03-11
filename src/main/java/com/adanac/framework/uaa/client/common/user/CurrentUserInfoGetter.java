package com.adanac.framework.uaa.client.common.user;

import java.util.Map;

/**
 * 当前用户信息获取接口
 * @author adanac
 * @version 1.0
 */
public interface CurrentUserInfoGetter {
	/**
	 * 
	 * 获取当前用户的id
	 *
	 * @param request 当前请求
	 * @return 返回获取结果信息
	 */
	String getCurrentUserId();

	/**
	 * 
	 * 获取当前用户的其他信息<br>
	 *
	 * @return 用户信息集合
	 */
	Map<String, Object> getCurrentUserAttributes();
}
