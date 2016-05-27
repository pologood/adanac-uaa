package com.adanac.framework.uaa.client.common.access.tree;

import java.util.HashMap;
import java.util.Map;

//package com.bn.framework.uaa.common.access.tree;
//
//import com.bn.framework.uaa.common.intf.AuthorizationSelector;
//import com.bn.framework.uaa.common.intf.AuthorizationService;
//import com.bn.framework.uaa.common.model.Authorization;
//import com.bn.framework.uaa.common.user.CurrentUserInfoGetter;
//import com.bn.framework.uaa.common.util.ComponentManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adanac.framework.uaa.client.common.intf.AuthorizationSelector;
import com.adanac.framework.uaa.client.common.intf.AuthorizationService;
import com.adanac.framework.uaa.client.common.user.CurrentUserInfoGetter;
import com.adanac.framework.uaa.client.common.util.ComponentManager;
import com.adanac.framework.uaa.client.core.entity.Authorization;

/**
 * 
 * 树形数据过滤基类<br>
 * 
 * @author
 */
public abstract class AbstractTreeFilter implements TreeFilter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 对应节点信息获取接口 */
	private TreeNodeInfoExtractor treeNodeInfoExtractor;

	/** 当前用户信息获取接口 */
	// private CurrentUserInfoGetter currentUserInfoGetter;

	/** 鉴权接口 */
	private AuthorizationService authorizationService;

	/** 对应节点访问权限类型的id */
	private String treeNodeAuthorizationTypeId = "TREE_NODE";

	public void setAuthorizationService(AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	public void setTreeNodeInfoExtractor(TreeNodeInfoExtractor treeNodeInfoExtractor) {
		this.treeNodeInfoExtractor = treeNodeInfoExtractor;
	}

	// public void setCurrentUserInfoGetter(CurrentUserInfoGetter
	// currentUserInfoGetter) {
	// this.currentUserInfoGetter = currentUserInfoGetter;
	// }

	public void setTreeNodeAuthorizationTypeId(String treeNodeAuthorizationTypeId) {
		this.treeNodeAuthorizationTypeId = treeNodeAuthorizationTypeId;
	}

	/**
	 * 
	 * 根据节点对象以及当前用户id，判断该用户是否具有访问该节点的权限
	 * 
	 * @param 参数说明
	 *            treeNode 节点对象
	 * @return boolean判断该用户是否具有访问该节点的权限
	 */
	protected boolean hasPermission(Object treeNode) {
		TreeNodeInfoExtractor.NodeAccessControlPolicy policy = treeNodeInfoExtractor.getAccessControlPolicy(treeNode);
		if (policy.equals(TreeNodeInfoExtractor.NodeAccessControlPolicy.IGNORE)) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]hasPermission: NodeAccessControlPolicy is IGNORE");
			}
			return true;
		}
		// 获取当前用户id
		CurrentUserInfoGetter currentUserInfoGetter = ComponentManager.getInstance().getCurrentUserInfoGetter();
		if (currentUserInfoGetter == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]hasPermission: currentUserInfoGetter is null.");
			}
			return false;
		}
		String userId = currentUserInfoGetter.getCurrentUserId();
		if (logger.isDebugEnabled()) {
			logger.debug("[UAALOG]hasPermission: userId is " + userId);
		}
		// 获取当前节点系统名称
		String system = treeNodeInfoExtractor.getNodeSystemName(treeNode);

		// 基于node url的访问判断
		if (policy.equals(TreeNodeInfoExtractor.NodeAccessControlPolicy.URL_BASED)) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]hasPermission: NodeAccessControlPolicy is URL_BASED");
			}
			String url = treeNodeInfoExtractor.getNodeUrl(treeNode);
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]hasPermission: url is " + url);
			}
			if (StringUtils.isEmpty(url)) {
				return true;
			}
			Map<String, Object> resourceMap = new HashMap<String, Object>();
			resourceMap.put(AuthorizationSelector.RESOURCE_MAP_DEFAULT_KEY, url);
			return authorizationService.hasPermission(userId, system, Authorization.AUTHORIZATION_TYPE_URL,
					resourceMap);
		} else if (policy.equals(TreeNodeInfoExtractor.NodeAccessControlPolicy.ID_BASED)) {
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]hasPermission: NodeAccessControlPolicy is ID_BASED");
			}
			// 基于node id的访问判断
			String id = treeNodeInfoExtractor.getNodeId(treeNode);
			if (logger.isDebugEnabled()) {
				logger.debug("[UAALOG]hasPermission: id is " + id);
			}
			if (StringUtils.isEmpty(id)) {
				return true;
			}
			Map<String, Object> resourceMap = new HashMap<String, Object>();
			resourceMap.put(AuthorizationSelector.RESOURCE_MAP_DEFAULT_KEY, id);
			return authorizationService.hasPermission(userId, system, treeNodeAuthorizationTypeId, resourceMap);
		}
		throw new IllegalStateException("Invalid NodeAccessControlPolicy value " + policy);
	}

}
