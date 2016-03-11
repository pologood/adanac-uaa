package com.adanac.framework.uaa.client.common.intf;

import java.util.Map;
import java.util.Set;

import com.adanac.framework.uaa.client.core.entity.Authorization;

/**
 * 权限信息选择器接口
 * @author adanac
 * @version 1.0
 */
public interface AuthorizationSelector {
	/** 权限对象中资源字段的默认标识 **/
	String RESOURCE_MAP_DEFAULT_KEY = "resource";

	/**
	 * 
	 * 主要是根据权限类型id以及请求资源，判断是否能用当前AuthorizationSelector的实现方式， 来获取权限信息
	 * 
	 * @param authorizationTypeId 权限类型
	 * @param resourceMap 请求的资源
	 * @return 返回 boolean值判断是否能用当前AuthorizationSelector的实现方式
	 */
	boolean supports(String authorizationTypeId, Map<String, Object> resourceMap);

	/**
	 * 
	 * 根据权限所属系统、权限类型id以及请求资源，来获取其具有的权限
	 * 
	 * @param system 权限所属系统
	 * @param authorizationTypeId 权限类型
	 * @param resourceMap 请求的资源
	 * @return 最终具有的Authorization集合
	 */
	Set<Authorization> getAuthorizations(String system, String authorizationTypeId, Map<String, Object> resourceMap);

}
