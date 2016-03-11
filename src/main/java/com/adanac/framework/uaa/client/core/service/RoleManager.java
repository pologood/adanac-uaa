package com.adanac.framework.uaa.client.core.service;

import java.util.List;
import java.util.Map;

import com.adanac.framework.page.QueryParam;
import com.adanac.framework.page.QueryResult;
import com.adanac.framework.uaa.client.core.entity.Role;

/**
 * 角色(Role)模型管理接口
 * 
 * 对角色实体，角色继承关系以及授权对象与角色映射关系进行管理
 * 
 * 本接口下的的方法都同时操作了缓存和数据库，因此调用时，请注意对缓存的影响
 * @author adanac
 * @version 1.0
 */
public interface RoleManager {
	/**
	 * 
	 * <p>添加或修改角色，并将信息同步到相应缓存</p>
	 * 
	 * @param role 角色
	 * @return 返回主键值 角色
	 */
	String saveRole(Role role);

	/**
	 * 
	 * <p>删除角色，并将相应缓存中的信息也删除</p>
	 * 
	 * @param roleId 角色编码
	 */
	void removeRole(String roleId);

	/**
	 * 
	 * <p>添加授权对象-角色映射，并将该关联添加到相应的缓存中</p>
	 * 
	 * @param identity 授权对象编码 
	 * @param roleId 角色编码
	 * @param identityType 授权对象编码类型
	 * @param departmetnId 部门ID
	 */
	void addUserRole(String identity, String roleId, String identityType, String departmetnId);

	/**
	 * 
	 * <p>删除授权对象-角色映射，并将相应缓存中的信息也删除</p>
	 * 
	 * @param identity 授权对象编码 
	 * @param roleId 角色编码
	 * @param identityType 授权对象编码类型
	 */
	void removeUserRole(String identity, String roleId, String identityType);

	/**
	 *
	 * <p>删除授权对象-角色映射之间的缓存</p>
	 *
	 * @param identity 授权对象编码
	 * @param roleId 角色编码
	 * @param identityType 授权对象编码类型
	 */
	void removeUserRoleCache(String identity, String roleId, String identityType);

	/**
	 * 
	 * <p>根据授权对象编码查询该授权对象拥有的角色</p>
	 * 
	 * @param identity 授权对象编码
	 * @return 角色对象的List集合
	 */
	List<Role> getRolesOfUser(String identity, String identityType);

	/**
	 * 
	 * <p>添加角色继承关系</p>
	 * 
	 * @param parentRole 父角色
	 * @param childRole 子角色
	 */
	void addChildRole(String parentRole, String childRole);

	/**
	 * 
	 * <p>删除角色继承关系，并将该关联添加到相应的缓存中</p>
	 * 
	 * @param parentRole 父角色
	 * @param childRole 子角色
	 */
	void removeChildRole(String parentRole, String childRole);

	/**
	 * 
	 * <p>根据角色ID查询角色模型实体对象</p>
	 * 
	 * @param roleId 角色编码
	 * @return 角色对象
	 */
	Role getRole(String roleId);

	/**
	 * 
	 * <p>查询应用中所有角色模型数据，直接从数据库获取</p>
	 * 
	 * @return 角色对象的List集合
	 */
	List<Role> getAllRoles();

	/**
	 * 
	 * <p>查询指定角色直接继承的父角色</p>
	 * 
	 * @param childRole 子角色ID
	 * @return 角色对象List集合
	 */
	List<Role> getParentRole(String childRole);

	/**
	 * 
	 * <p>查询指定角色级联继承的所有父角色</p>
	 * 
	 * @param childRole 子角色ID
	 * @return 角色列表List集合
	 */
	List<Role> getRecursiveParentRole(String childRole);

	/**
	 * 
	 * <p>查询直接继承指定父角色的子角色</p>
	 * 
	 * @param parentRole 父角色ID
	 * @return 角色列表List集合
	 */
	List<Role> getChildRole(String parentRole);

	/**
	 * 
	 * <p>查询级联继承指定角色的所有子角色</p>
	 * 
	 * @param parentRole 父角色ID
	 * @return 角色列表List集合
	 */
	List<Role> getRecursiveChildRole(String parentRole);

	// methods for view-layer
	/**
	 * 
	 * <p>分页查询角色信息，供页面展示使用</p>
	 * 
	 * @param queryParam 分页查询参数
	 * @return 分页查询结果QueryResult
	 */
	QueryResult<Role> getRolesByPage(QueryParam<Map<String, Object>> queryParam);

	/**
	 * 
	 * 分页查询用户角色映射关系信息，供页面展示使用
	 * 
	 * @param 分页查询参数QueryParam
	 * @return 分页查询结果QueryResult
	 */
	QueryResult<Map<String, Object>> getUserRolesByPage(QueryParam<Map<String, Object>> queryParam);

	/**
	 * 将角色信息同步到缓存
	 *
	 */
	void syncRolesToCache();

	/**
	 * 查出所有角色与角色的父关联，然后放入缓存中以及 查出所有角色与角色的子关联，然后放入缓存中
	 *
	 */
	void syncRoleMappingsToCache();

	/**
	 * 查询所有用户和角色之间的绑定关系.
	 * @param queryParam
	 * @return
	 */
	public List<Map<String, Object>> listAllUserRole(Map<String, Object> queryParam);
}
