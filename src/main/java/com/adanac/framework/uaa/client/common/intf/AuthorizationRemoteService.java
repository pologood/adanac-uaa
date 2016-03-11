package com.adanac.framework.uaa.client.common.intf;

import java.util.Map;

/**
 * 鉴权远程调用接口
 * @author adanac
 * @version 1.0
 */
public interface AuthorizationRemoteService {
	/**
	 * 
	 * 判断用户是否拥有该角色组中的任意一个角色
	 * 
	 * @param userId 用户标识
	 * @param roleIds 角色组
	 * @return 判定结果
	 */
	boolean hasAnyRole(String userId, String[] roleIds);

	/**
	 * 
	 * 判断用户是否拥有该角色组中的所有角色
	 * 
	 * @param userId 用户标识
	 * @param roleIds 角色组
	 * @return 判定结果
	 */
	boolean hasAllRoles(String userId, String[] roleIds);

	/**
	 * 
	 * 根据用户标识，权限类型，以及请求资源，判断该用户是否具有相应权限
	 * 
	 * @param userId 用户标识
	 * @param authorizationTypeId 权限类型
	 * @param fieldValues 用户请求资源信息
	 * @return 判定结果
	 */
	boolean hasPermission(String userId, String systemName, String authorizationTypeId,
			Map<String, Object> resourceMap);
}
