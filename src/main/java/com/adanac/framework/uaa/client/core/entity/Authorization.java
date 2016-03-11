package com.adanac.framework.uaa.client.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 权限对象实体类
 * @author adanac
 * @version 1.0
 */
@Entity(name = "UAA_II_AUTHORIZATION")
public class Authorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2681293164751362713L;

	public static String MAPPING_TYPE_AUTHORIZATION = "A";

	public static String AUTHORIZATION_TYPE_URL = "REQUEST_URL";

	public static String AUTHORIZATION_TYPE_TREE_NODE = "TREE_NODE";

	/** 主键 */
	private Long id;

	/** 系统ID */
	private String systemId;

	/** 权限类型 */
	private String type;

	/** 资源内容 */
	private String resource;

	/** 权限描述 */
	private String description;

	/**
	 *树形菜单的父节点ID
	 */
	private String treeParentId;

	/**
	 *菜单-- 排序
	 */
	private Integer orderNumber;

	/**
	 * 针对type为treeNode的节点该值适用
	 */
	private String url;

	/**
	 * 0 业务系统使用权限系统中的树作为菜单
	 * 1 代表菜单由业务系统自己维护
	 */
	private Integer selfMenu;

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SYSTEM_ID")
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Column(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "RESOURCES")
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	@Column(name = "TREE_PARENT_ID")
	public String getTreeParentId() {
		if (treeParentId == null)
			return "";
		return treeParentId;
	}

	public void setTreeParentId(String treeParentId) {
		this.treeParentId = treeParentId;
	}

	@Column(name = "URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ORDER_NUMBER")
	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "SELF_MENU")
	public Integer getSelfMenu() {
		if (selfMenu == null)
			return 0;
		return selfMenu;
	}

	public void setSelfMenu(Integer selfMenu) {
		this.selfMenu = selfMenu;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Authorization that = (Authorization) o;
		return !(id != null ? !id.equals(that.id) : that.id != null);
	}

	@Override
	public int hashCode() {
		int hs = 17;
		if (id != null) {
			hs = 37 * hs + id.hashCode();
		}
		return hs;
	}

}
