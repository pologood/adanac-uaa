package com.adanac.framework.uaa.client.core.web;

import com.adanac.framework.page.QueryParam;

/**
 * 查询扩展参数
 * @author adanac
 * @version 1.0
 */
public class QueryParamExt<T> extends QueryParam<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6040063074510889980L;

	// 排序方式
	public enum OrderType {
		asc, desc
	}

	private String property;// 查找属性名称
	private String keyword;// 查找关键字
	private OrderType orderType = OrderType.desc;// 排序方式

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

}
