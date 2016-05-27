package com.adanac.framework.uaa.client.common.access.tree;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 层级结构树形数据过滤器<br>
 * 针对以层级形式提供的树形数据进行过滤
 * @author
 */

public class RecursiveBasedTreeFilter extends AbstractTreeFilter {

    private TreeStructureExtractor treeStructureExtractor;

    public void setTreeStructureExtractor(TreeStructureExtractor treeStructureExtractor) {
        this.treeStructureExtractor = treeStructureExtractor;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    // 树形结构节点过滤
    public Object filter(Object object) {
        if (!hasPermission(object)) {
            return null;
        }
        Collection childCollection = treeStructureExtractor.getChildTreeNodes(object);
        List<Object> needRemoveList = new ArrayList<Object>();
        for (Object child : childCollection) {
            Object filtered = filter(child);
            if (filtered == null) {
                needRemoveList.add(child);
            }
        }
        childCollection.removeAll(needRemoveList);
        return object;
    }
}

