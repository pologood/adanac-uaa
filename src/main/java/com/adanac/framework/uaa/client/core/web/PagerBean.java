package com.adanac.framework.uaa.client.core.web;

/**
 * 
 * @author adanac
 * @version 1.0
 */
public class PagerBean<T> {
	private QueryParamExt<T> pager = new QueryParamExt<T>();

	public QueryParamExt<T> getPager() {
		return pager;
	}

	public void setPager(QueryParamExt<T> pager) {
		this.pager = pager;
	}

}
