package com.adanac.framework.uaa.client.common.system;

/**
 * 系统信息获取默认实现类
 * @author adanac
 * @version 1.0
 */
public class UaaSystemInfoGetter implements SystemInfoGetter {
	private String system;

	@Override
	public String getSystemName() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}
}
