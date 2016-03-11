package com.adanac.framework.uaa.client.core.dao;

import java.util.List;
import java.util.Map;

import com.adanac.framework.page.QueryResult;
import com.adanac.framework.uaa.client.core.entity.User;
import com.adanac.framework.uaa.client.core.web.QueryParamExt;

/**
 * 
 * @author adanac
 * @version 1.0
 */
public interface UserDao {
	QueryResult<User> findUsersByPage(QueryParamExt<Map<String, Object>> queryParam);

	/**
	 * 添加用户.
	 * @param user 用户对象.
	 * @return  成功则返回true，失败返回false。
	 */
	Boolean addUser(User user);

	/**
	 * 查询用户列表.
	 * @param queryUser 查询条件.
	 * @return 符合条件的列表.
	 */
	List<User> findUser(User queryUser);

	/**
	 * 编辑用户.
	 * @param user
	 * @return 成功返回true ，失败返回false
	 */
	Boolean editUser(User user);

	/**
	 * 根据用户ID，来删除用户.
	 * @param user
	 * @return  成功返回true ，失败返回false
	 */
	Boolean deleteUser(User user);

	/**
	 * 删除用户和角色之间的关联关系.
	 * @param user
	 * @return  成功返回true ，失败返回false
	 */
	Boolean deleteUserRoleBindRelation(User user);

	/**
	 * 根据部门Id来删除用户
	 * @param departmentId
	 * @return
	 */
	Boolean deleteUsersByDepartmentId(Long departmentId);

	/**
	 * 根据部门Id来删除用户和角色的绑定关系
	 * @param departmentId
	 * @return
	 */
	Boolean deleteUserRoleBindRelationssByDepartmentId(Long departmentId);
}
