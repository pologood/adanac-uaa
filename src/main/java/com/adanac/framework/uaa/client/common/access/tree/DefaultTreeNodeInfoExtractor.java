package com.adanac.framework.uaa.client.common.access.tree;


import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 默认节点信息抽取类<br>
 * @author
 */
public class DefaultTreeNodeInfoExtractor implements TreeNodeInfoExtractor {

    /** 该属性对应菜单节点的nodeId，是通过配置文件设置的 */
    private String idPropertyName;

    /** 该属性对应菜单节点的node url */
    private String urlPropertyName;
    
    /** 该属性对应菜单节点的所属系统 */
    private String systemPropertyName;

    /** 该属性对应菜单节点的access Policy */
    private String accessPolicyPropertyName;
    
    /** 该属性对应菜单的默认access Policy */
    private String accessPolicy;

    public void setIdPropertyName(String idPropertyName) {
        this.idPropertyName = idPropertyName;
    }

    public void setUrlPropertyName(String urlPropertyName) {
        this.urlPropertyName = urlPropertyName;
    }

    public void setSystemPropertyName(String systemPropertyName) {
        this.systemPropertyName = systemPropertyName;
    }

    public void setAccessPolicyPropertyName(String accessPolicyPropertyName) {
        this.accessPolicyPropertyName = accessPolicyPropertyName;
    }

    public void setAccessPolicy(String accessPolicy) {
        this.accessPolicy = accessPolicy;
    }


    @Override
    public String getNodeId(Object node) {
        return getFieldValue(node, idPropertyName);
    }


    @Override
    public String getNodeUrl(Object node) {
        return getFieldValue(node, urlPropertyName);
    }


    @Override
    public String getNodeSystemName(Object node) {
        return getFieldValue(node, systemPropertyName);
    }
    

    @Override
    public NodeAccessControlPolicy getAccessControlPolicy(Object node) {
        if (StringUtils.isBlank(accessPolicyPropertyName)){
            if(StringUtils.isBlank(accessPolicy)){
                return NodeAccessControlPolicy.URL_BASED;
            }
            return EnumUtils.getEnum(NodeAccessControlPolicy.class, accessPolicy);
        }
        // 利用反射机制获取属性
        Field field = ReflectionUtils.findField(node.getClass(), accessPolicyPropertyName);
        if (field == null) {
            return NodeAccessControlPolicy.URL_BASED;
        }
        // 设置属性是可访问的
        ReflectionUtils.makeAccessible(field);
        // 获取属性值
        Object value = ReflectionUtils.getField(field, node);
        if (value == null) {
            return NodeAccessControlPolicy.URL_BASED;
        }
        return EnumUtils.getEnum(NodeAccessControlPolicy.class, value.toString());
    }

    private String getFieldValue(Object node, String fieldName) {
        // 利用反射机制获取属性
        Field field = ReflectionUtils.findField(node.getClass(), fieldName);
        // 设置属性是可访问的
        ReflectionUtils.makeAccessible(field);
        // 获取属性值
        Object value = ReflectionUtils.getField(field, node);
        return value == null ? null : value.toString();
    }
}

