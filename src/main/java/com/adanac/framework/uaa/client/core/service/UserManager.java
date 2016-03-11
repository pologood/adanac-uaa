package com.adanac.framework.uaa.client.core.service;

import java.util.List;
import java.util.Map;

import com.adanac.framework.page.QueryResult;
import com.adanac.framework.uaa.client.core.entity.User;
import com.adanac.framework.uaa.client.core.web.QueryParamExt;
import com.adanac.framework.web.controller.BaseResult;

/**
 * 用户管理
 * @author adanac
 * @version 1.0
 */
public interface UserManager {
	QueryResult<User> getUsersByPage(QueryParamExt<Map<String, Object>> queryParam);

	/**
	 * 删除部门下的所有用户.
	 * @param departmentIds 部门ID列表.
	 * @return
	 */
	boolean deleteUsersByDepartmentIds(List<Long> departmentIds);

	/**
	 * 新增用户.
	 * @param user 用户对象.
	 * @return 用户的id.
	 */
	Boolean addUser(User user);

	/**
	 * 查询用户.
	 * @param queryUser
	 * @return
	 */
	List<User> queryUser(User queryUser);

	/**
	 * 编辑用户.
	 * @param user
	 * @return 成功返回true,失败返回false
	 */
	Boolean editUser(User user);

	/**
	 * 根据用户Id来删除用户.
	 * @param user
	 * @return 成功返回true,失败返回false
	 */
	Boolean deleteUser(User user);

	/**
	 * 用户登录接口.
	 * @param userName 用户名字.
	 * @param password 密码.
	 * @return 登录成功,BaseResult.SUCCESS,同时返回用户对象, 登录失败的情况下,返回错误码.
	 *          <p>
	 *              1 未知错误<p>
	 *              2 用户不存在.<p>
	 *              3 用户名密码不匹配.<p>
	 *              4 用户被禁用.<p>
	 *
	 */
	BaseResult login(String userName, String password);

	/**
	 * 根据用户和角色的绑定关系和该用户绑定的所有角色关系 都会被删除，而不是仅仅删除一个.
	 * @param user
	 * @return 成功返回true,失败返回false
	 */
	Boolean deleteUserRoleBindRelation(User user);
}
