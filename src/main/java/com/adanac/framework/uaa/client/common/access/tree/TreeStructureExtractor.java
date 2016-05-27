package com.adanac.framework.uaa.client.common.access.tree;


import java.util.Collection;

/**
 * 树形结构抽取接口
 * @author
 */
public interface TreeStructureExtractor {

    /**
     * 
     * 根据节点对象获取该节点的所有子节点
     * 
     * @param parentTreeNode 父节点的对象
     * @return 子节点对象的集合
     */
    @SuppressWarnings("rawtypes")
    Collection getChildTreeNodes(Object parentTreeNode);

}
