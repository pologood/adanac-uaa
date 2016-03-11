package com.adanac.framework.uaa.client.core.entity;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @author adanac
 * @version 1.0
 */
public class Identity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2286627812512639984L;

	public static final String IDENTITY_TYPE_EMPLOYEE = "EMPLOYEE_ID";

	private String id;

	private Map<String, Object> attributes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
