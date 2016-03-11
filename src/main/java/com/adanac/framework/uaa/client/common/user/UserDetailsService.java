package com.adanac.framework.uaa.client.common.user;

import java.util.Map;

/**
 * 用户信息服务接口
 * 需要进行扩展以获取指定用户的详细信息
 * @author adanac
 * @version 1.0
 */
public interface UserDetailsService {
	/**
	 * 
	 * 获取指定用户的扩展信息<br>
	 * 根据用户唯一标识（工号）获取该用户的扩展信息（如部门、岗位）；这些信息可作为授权时的授权对象（如将某角色授权给某部门）。<br> 
	 * 返回的MAP集合，以信息的标识为key（必须与授权时保存的授权对象类型一致）
	 * 
	 * @param userId
	 * @return 用户的扩展信息
	 */
	Map<String, Object> getIdentityInfo(String userId);
}
