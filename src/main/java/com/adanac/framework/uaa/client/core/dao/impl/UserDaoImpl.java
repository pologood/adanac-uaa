package com.adanac.framework.uaa.client.core.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.adanac.framework.dac.client.DacClient;
import com.adanac.framework.page.QueryResult;
import com.adanac.framework.uaa.client.core.dao.UserDao;
import com.adanac.framework.uaa.client.core.entity.User;
import com.adanac.framework.uaa.client.core.web.QueryParamExt;

/**
 * 
 * @author adanac
 * @version 1.0
 */
@Repository("userDao")
public class UserDaoImpl extends UaaBaseDao implements UserDao {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "uaaDacClient")
	private DacClient uaaDalClient;

	@Override
	public QueryResult<User> findUsersByPage(QueryParamExt<Map<String, Object>> queryParam) {
		String countSqlId = "page.query_user_count";
		String sqlId = "page.query_users";
		return this.pageQuery(countSqlId, sqlId, queryParam, User.class);
	}

	@Override
	public Boolean addUser(User user) {
		try {
			if (user.getUserId() == null) {
				throw new RuntimeException("用户名不允许为空.");
			}
			int count = uaaDalClient.execute("execute.add_user", user);
			if (count > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.warn("[addAuthorization]Exception occured in method:" + e);
			return false;
		}
	}

	@Override
	public List<User> findUser(User queryUser) {
		String sqlId = "query.find_user";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", queryUser.getId());
		paramMap.put("userId", queryUser.getUserId());
		paramMap.put("departmentId", queryUser.getDepartmentId());
		paramMap.put("departmentName", queryUser.getDepartmentName());
		paramMap.put("nickName", queryUser.getNickName());
		try {
			return uaaDalClient.queryForList(sqlId, paramMap, User.class);
		} catch (Exception e) {
			logger.warn("[findAuthorization]Exception occured in method:" + e);
			return null;
		}
	}

	@Override
	public Boolean editUser(User user) {
		try {
			int count = uaaDalClient.merge(user);
			if (count > 0) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("editUser exception occured in method:{}", ex);
			return false;
		}
		return false;
	}

	@Override
	public Boolean deleteUser(User user) {
		try {
			int count = uaaDalClient.remove(user);
			if (count == 1) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("deleteUser  exception occured in method:{}", ex);
		}
		return false;
	}

	@Override
	public Boolean deleteUserRoleBindRelation(User user) {
		String sqlId = "execute.delete_role_user_mapping_by_userId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userIdentity", user.getId());
		paramMap.put("identityType", "employee_id");
		try {
			int count = uaaDalClient.execute(sqlId, paramMap);
			if (count > 1) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("deleteUser  exception occured in method:{}", ex);
			throw new RuntimeException(ex);
		}
		return false;
	}

	@Override
	public Boolean deleteUsersByDepartmentId(Long departmentId) {
		String sqlId = "execute.delete_users_by_departmentId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("departmentId", departmentId);
		try {
			int count = uaaDalClient.execute(sqlId, paramMap);
			if (count > 1) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("deleteUser  exception occured in method:{}", ex);
		}
		return false;
	}

	@Override
	public Boolean deleteUserRoleBindRelationssByDepartmentId(Long departmentId) {
		String sqlId = "execute.delete_user_role_bind_relations_by_departmentId";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("departmentId", departmentId);
		try {
			int count = uaaDalClient.execute(sqlId, paramMap);
			if (count > 1) {
				return true;
			}
		} catch (Exception ex) {
			logger.error("deleteUser  exception occured in method:{}", ex);
		}
		return false;
	}
}
