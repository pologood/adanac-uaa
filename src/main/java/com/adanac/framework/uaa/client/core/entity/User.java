package com.adanac.framework.uaa.client.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author adanac
 * @version 1.0
 */
@Entity(name = "UAA_II_USER_INFO")
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7530845911954028635L;
	/**
	 * 主键id.
	 */
	private Long id;
	/**
	 * 用户唯一标识,登录用户名
	 */
	private String userId;

	private Long departmentId;

	private String password;

	private String departmentName;

	private String nickName;

	public User() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "department_id")
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	@Column(name = "nick_name")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
