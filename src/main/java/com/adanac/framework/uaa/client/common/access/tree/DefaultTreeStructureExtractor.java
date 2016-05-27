package com.adanac.framework.uaa.client.common.access.tree;


import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 默认树形结构节点抽取类<br>
 * @author
 */
public class DefaultTreeStructureExtractor implements TreeStructureExtractor {

    /** 节点对应的子节点的属性 */
    private String childTreeNodesPropertyName;

    public void setChildTreeNodesPropertyName(String childTreeNodesPropertyName) {
        this.childTreeNodesPropertyName = childTreeNodesPropertyName;
    }


    @SuppressWarnings("rawtypes")
    @Override
    public Collection getChildTreeNodes(Object parentTreeNode) {
        Field field = ReflectionUtils.findField(parentTreeNode.getClass(), childTreeNodesPropertyName);
        if (field == null) {
            return null;
        }
        ReflectionUtils.makeAccessible(field);
        Object value = ReflectionUtils.getField(field, parentTreeNode);
        return value == null ? null : (Collection) value;
    }
}
