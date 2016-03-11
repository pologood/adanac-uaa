package com.adanac.framework.uaa.client.common.intf;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adanac.framework.uaa.client.core.entity.Authorization;
import com.adanac.framework.uaa.client.core.entity.Role;

/**
 * 鉴权接口
 * 根据用户信息和权限信息（如角色，权限或访问资源标识）进行鉴权
 * @author adanac
 * @version 1.0
 */
public interface AuthorizationService {
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
	 * @param resourceMap 用户请求资源信息
	 * @return 判定结果
	 */
	boolean hasPermission(String userId, String systemName, String authorizationTypeId,
			Map<String, Object> resourceMap);

	/**
	 * 
	 * 根据用户标识，获取该用户具有的所有的角色
	 * 
	 * @param userId 用户标识
	 * @return 实体对象的set集
	 */
	Set<Role> getRoleOfUser(String userId);

	/**
	 * 
	 * 根据权限类型，以及请求资源，获取权限对应的所有角色
	 * 
	 * @param authorizationTypeId 权限类型
	 * @param resourceMap 请求资源信息
	 * @return 权限对应的角色的集合
	 */
	Set<Role> getAuthorizedRoles(String systemName, String authorizationTypeId, Map<String, Object> resourceMap);

	/**
	 * 
	 * 根据用户标识，权限模板id，以及请求资源，获取该用户对于请求资源，所具备的权限
	 * 
	 * @param userId 用户标识
	 * @param resourceMap 请求资源信息
	 * @return 返回 相应权限的集合
	 */
	Set<Authorization> getAuthorizations(String userId, String systemName, String authorizationTypeId,
			Map<String, Object> resourceMap);

	/**
	 * 
	 * 根据权限类型，获取该类型所有的权限
	 * 
	 * @param authorizationTypeId 权限类型
	 * @return 相应权限的集合
	 */
	List<Authorization> getAuthorizations(String systemName, String authorizationTypeId);

}
