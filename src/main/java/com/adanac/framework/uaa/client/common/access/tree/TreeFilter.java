package com.adanac.framework.uaa.client.common.access.tree;

/**
 * 树形数据过滤接口
 * @author
 */
public interface TreeFilter {

    /**
     * 
     * 拦截过滤没有访问权限的节点
     * 
     * @param 参数说明 object 是待过滤对象
     * @return 返回过过滤节点后的对象
     */
    Object filter(Object object);

}

