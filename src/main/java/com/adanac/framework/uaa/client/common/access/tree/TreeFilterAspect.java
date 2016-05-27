package com.adanac.framework.uaa.client.common.access.tree;


import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 树形数据过滤切片类<br>
 * @author
 */

public class TreeFilterAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private TreeFilter filter;

    public void setFilter(TreeFilter filter) {
        this.filter = filter;
    }

    /**
     * 
     * aop拦截过滤无权访问节点
     */
    public Object filter(ProceedingJoinPoint jp) throws Throwable {
        try {
            Object result = jp.proceed();
            if(logger.isDebugEnabled()){
                logger.debug("[UAALOG]filter: enter filter method");
            }
            return filter.filter(result);
        } catch (Throwable ex) {
            logger.error("process tree filter error.", ex);
            throw ex;
        }
    }

}

