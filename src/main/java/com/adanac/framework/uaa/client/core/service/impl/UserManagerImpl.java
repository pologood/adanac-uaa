package com.adanac.framework.uaa.client.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adanac.framework.page.QueryResult;
import com.adanac.framework.uaa.client.core.dao.UserDao;
import com.adanac.framework.uaa.client.core.entity.User;
import com.adanac.framework.uaa.client.core.service.RoleManager;
import com.adanac.framework.uaa.client.core.service.UserManager;
import com.adanac.framework.uaa.client.core.web.QueryParamExt;
import com.adanac.framework.utils.MD5;
import com.adanac.framework.web.controller.BaseResult;

/**
 * 
 * @author adanac
 * @version 1.0
 */
@Service("userManager")
public class UserManagerImpl implements UserManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleManager roleManager;

	@Override
	public QueryResult<User> getUsersByPage(QueryParamExt<Map<String, Object>> queryParam) {
		return userDao.findUsersByPage(queryParam);
	}

	@Override
	@Transactional
	public boolean deleteUsersByDepartmentIds(List<Long> departmentIds) {
		// 根据部门来删除用户的同时，要删除用户和角色之间的绑定关系.
		for (Long departmentId : departmentIds) {
			deleteUsersByDepartmentId(departmentId);
			deleteUserRoleBindRelationssByDepartmentId(departmentId);
		}
		return true;
	}

	/**
	 * 根据部门id来删除用户.
	 * @param departmentId
	 */
	@Transactional
	private void deleteUsersByDepartmentId(Long departmentId) {
		userDao.deleteUsersByDepartmentId(departmentId);
	}

	/**
	 * 根据部门id来删除用户和角色之间的绑定关系.
	 * @param departmentId
	 */
	@Transactional
	private void deleteUserRoleBindRelationssByDepartmentId(Long departmentId) {
		// 根据部门ID查询所有的用户和角色之间的绑定关系
		Map<String, Object> queryUserRoleRelationParam = new HashMap<String, Object>();
		queryUserRoleRelationParam.put("departmetnId", departmentId);
		List<Map<String, Object>> userRoleBindRelationList = roleManager.listAllUserRole(queryUserRoleRelationParam);
		for (Map<String, Object> userRoleRelationItem : userRoleBindRelationList) {
			String identity = userRoleRelationItem.get("userIdentity") == null ? null
					: String.valueOf(userRoleRelationItem.get("userIdentity"));
			String roleId = userRoleRelationItem.get("roleCode") == null ? null
					: String.valueOf(userRoleRelationItem.get("roleCode"));
			String identityType = userRoleRelationItem.get("identityType") == null ? null
					: String.valueOf(userRoleRelationItem.get("identityType"));
			roleManager.removeUserRoleCache(identity, roleId, identityType);
		}
		userDao.deleteUserRoleBindRelationssByDepartmentId(departmentId);
	}

	@Override
	public Boolean addUser(User user) {
		if (user == null) {
			logger.info("[addUser]Param user check failed!");
			return false;
		}
		List<User> userList = queryUser(user);
		if (userList != null && !userList.isEmpty()) {
			return false;
		}
		return userDao.addUser(user);
	}

	@Override
	public List<User> queryUser(User queryUser) {
		return userDao.findUser(queryUser);
	}

	@Override
	public Boolean editUser(User user) {
		return userDao.editUser(user);
	}

	@Transactional
	@Override
	public Boolean deleteUser(User user) {
		try {
			// ①删除用户
			userDao.deleteUser(user);
			// ②删除用户和角色之间的对应关系
			deleteUserRoleBindRelation(user);
			return true;
		} catch (Exception ex) {
			logger.error("deleteUser exception:{}", ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public BaseResult login(String userName, String password) {
		BaseResult baseResult = new BaseResult();
		baseResult.setStatus(BaseResult.ERROR);

		User copyUser = new User();
		copyUser.setUserId(userName);
		List<User> userList = queryUser(copyUser);
		if (CollectionUtils.isNotEmpty(userList)) {
			if (userList.size() > 1) {
				baseResult.setErrorCode("1");// 未知错误
			}
			User resultUser = userList.get(0);

			String resultUserPassword = resultUser.getPassword();
			if (!StringUtils.equals(MD5.encode(password), resultUserPassword)) {// 用户名密码不匹配
				baseResult.setErrorCode("3");// 3 用户名密码不匹配
			} else {
				baseResult.setStatus(BaseResult.SUCCESS);
				baseResult.setContent(resultUser);
			}
		} else {
			baseResult.setErrorCode("2");// 用户不存在
		}
		return baseResult;
	}

	@Transactional
	@Override
	public Boolean deleteUserRoleBindRelation(User user) {
		try {
			// ①删除用户和角色之间的对应关系
			Map<String, Object> queryUserRoleRelationParam = new HashMap<String, Object>();
			queryUserRoleRelationParam.put("userIdentity", user.getId());
			queryUserRoleRelationParam.put("identityType", "employee_id");
			List<Map<String, Object>> userRoleBindRelationList = roleManager
					.listAllUserRole(queryUserRoleRelationParam);
			for (Map<String, Object> userRoleRelationItem : userRoleBindRelationList) {
				String identity = userRoleRelationItem.get("userIdentity") == null ? null
						: String.valueOf(userRoleRelationItem.get("userIdentity"));
				String roleId = userRoleRelationItem.get("roleCode") == null ? null
						: String.valueOf(userRoleRelationItem.get("roleCode"));
				String identityType = userRoleRelationItem.get("identityType") == null ? null
						: String.valueOf(userRoleRelationItem.get("identityType"));
				roleManager.removeUserRole(identity, roleId, identityType);
			}
			return true;
		} catch (Exception ex) {
			logger.error("deleteUserRoleBindRelation exception:{}", ex);
			throw new RuntimeException(ex);
		}
	}
}
