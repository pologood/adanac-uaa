package com.adanac.framework.uaa.client.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 角色对象实体
 * @author adanac
 * @version 1.0
 */
@Entity(name = "UAA_II_ROLE")
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -369853717994368945L;

	/** 角色编码，也是主键 */
	private String code;

	/** 角色名称 */
	private String name;

	/** 角色描述 */
	private String description;

	/** 角色状态 */
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Role role = (Role) o;
		return !(code != null ? !code.equals(role.code) : role.code != null);
	}

	@Override
	public int hashCode() {
		int hs = 17;
		if (code != null) {
			hs = 37 * hs + code.hashCode();
		}
		return hs;
	}
}
