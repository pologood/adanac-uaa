package com.adanac.framework.uaa.client.common.util;

/**
 * 
 * @author adanac
 * @version 1.0
 */
public class UUID {
	static Long beginValue = 0L;

	public static synchronized Long getRandomUID() {
		Long randomId = System.currentTimeMillis() + beginValue;
		if (beginValue == 99999) {
			beginValue = 0L;
		}
		beginValue++;
		return randomId;
	}

	public static void main(String[] args) {
		System.out.println(getRandomUID());
	}
}
