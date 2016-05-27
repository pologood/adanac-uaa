package com.adanac.framework.uaa.client.common.access.tree;



/**
 * 节点信息抽取接口<br>
 * @author
 */
public interface TreeNodeInfoExtractor {

    /**
     * 
     * 树形菜单过滤策略<br> 
     *
     * @author
     */
    enum NodeAccessControlPolicy {
        URL_BASED, ID_BASED, IGNORE
    }

    /**
     * 
     * 根据节点node对象获取节点id
     * 
     * @param node 节点对象
     * @return String类型的 节点id
     */
    String getNodeId(Object node);

    /**
     * 
     * 根据节点node对象获取节点对应的url
     * 
     * @param node 节点对象
     * @return String类型的 节点对应的url
     */
    String getNodeUrl(Object node);
    
    /**
     * 
     * 根据节点node对象获取节点对应的系统名称
     *
     * @param node
     * @return 系统名称
     */
    String getNodeSystemName(Object node);

    /**
     * 
     * 根据节点node对象获取节点的访问方针
     * 
     * @param  node 节点对象
     * @return 返回获取结果
     */
    NodeAccessControlPolicy getAccessControlPolicy(Object node);

}
