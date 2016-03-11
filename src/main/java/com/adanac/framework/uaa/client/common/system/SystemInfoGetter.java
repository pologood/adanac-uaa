package com.adanac.framework.uaa.client.common.system;

/**
 * 权限控制过程中，使用到的系统信息获取接口
 * @author adanac
 * @version 1.0
 */
public interface SystemInfoGetter {
	/** 系统信息bean默认名称 **/
	String SYSTEM_INFO_BEAN_NAME = "systemInfoGetter";

	/**
	 * 
	 * 获取系统名称 <br>
	 *
	 * @return 系统名称
	 */
	String getSystemName();
}
