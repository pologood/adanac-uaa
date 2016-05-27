package com.adanac.framework.uaa.client.common.access.tree;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 无层次的树形数据过滤器<br>
 * 针对以列表形式提供的树形数据进行过滤
 * @author
 */
public class FlatBasedTreeFilter extends AbstractTreeFilter {


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object filter(Object object) {
        if (object == null) {
            return null;
        }
        if (!(object instanceof Collection)) {
            throw new IllegalStateException("ListBasedTreeFilter can process Collection object only.");
        }
        Collection collection = (Collection) object;
        List<Object> needRemoveItemList = new ArrayList<Object>();
        for (Object item : collection) {
            if (!hasPermission(item)) {
                needRemoveItemList.add(item);
            }
        }
        collection.removeAll(needRemoveItemList);
        return collection;
    }
}
