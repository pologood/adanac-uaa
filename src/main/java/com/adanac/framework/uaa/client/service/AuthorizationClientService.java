package com.adanac.framework.uaa.client.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.adanac.framework.uaa.AuthorizationService;
import com.adanac.framework.uaa.client.common.intf.AuthorizationRemoteService;
import com.adanac.framework.uaa.client.core.entity.Authorization;
import com.adanac.framework.uaa.client.core.entity.Role;

/**
 * 鉴权服务远程调用<br>
 * 该类是鉴权接口的默认远程调用实现类，仅实现了hasAnyRole，hasAllRoles，hasPermission三个方法，调用其他方法均返回null
 * @author adanac
 * @version 1.0
 */
public class AuthorizationClientService implements AuthorizationService {
	/** 鉴权远程调用接口 **/
	private AuthorizationRemoteService authorizationRemoteService;

	/** 标识是否需要启用远程鉴权 ,默认为false,需要远程鉴权 */
	private boolean flag = false;

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public boolean hasAnyRole(String userId, String[] roleIds) {
		// flag为true时不进行远程远程鉴权
		return flag ? true : authorizationRemoteService.hasAnyRole(userId, roleIds);
	}

	@Override
	public boolean hasAllRoles(String userId, String[] roleIds) {
		// flag为true时不进行远程远程鉴权
		return flag ? true : authorizationRemoteService.hasAllRoles(userId, roleIds);
	}

	@Override
	public boolean hasPermission(String userId, String systemName, String authorizationTypeId,
			Map<String, Object> resourceMap) {
		// flag为true时不进行远程远程鉴权
		return flag ? true
				: authorizationRemoteService.hasPermission(userId, systemName, authorizationTypeId, resourceMap);
	}

	@Override
	public Set<Role> getRoleOfUser(String userId) {
		return null;
	}

	@Override
	public Set<Role> getAuthorizedRoles(String systemName, String authorizationTypeId,
			Map<String, Object> resourceMap) {
		return null;
	}

	@Override
	public Set<Authorization> getAuthorizations(String userId, String systemName, String authorizationTypeId,
			Map<String, Object> resourceMap) {
		return null;
	}

	@Override
	public List<Authorization> getAuthorizations(String systemName, String authorizationTypeId) {
		return null;
	}

	public void setAuthorizationRemoteService(AuthorizationRemoteService authorizationRemoteService) {
		this.authorizationRemoteService = authorizationRemoteService;
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
		System.out.println(Long.MAX_VALUE);
	}
}
