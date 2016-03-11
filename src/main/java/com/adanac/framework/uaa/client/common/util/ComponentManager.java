package com.adanac.framework.uaa.client.common.util;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.adanac.framework.uaa.client.common.user.CurrentUserInfoGetter;
import com.adanac.framework.uaa.client.common.user.DefaultCurrentUserInfoGetter;
import com.adanac.framework.uaa.client.common.user.UaaUserDetailsService;
import com.adanac.framework.uaa.client.common.user.UserDetailsService;

/**
 * spring bean 对象获取工具类
 * @author adanac
 * @version 1.0
 */
public class ComponentManager implements ApplicationContextAware, DisposableBean {
	public static final String AUTHORIZATION_SERVICE_BEAN_NAME = "authorizationService";

	public static final String CURRENT_USER_INFO_GETTER_BEAN_NAME = "currentUserInfoGetter";

	public static final String USER_DETAIL_GETTER_BEAN_NAME = "userDetailsService";

	private static ComponentManager instance = new ComponentManager();

	private ApplicationContext applicationContext;

	/** 存储已获取的bean对象 */
	private final ConcurrentHashMap<String, Object> componentMap = new ConcurrentHashMap<String, Object>();

	/**
	 * 
	 * 返回本工具类对象实例
	 * 
	 * @return 对象实例
	 */
	public static ComponentManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * 获取bean对象<br>
	 * 从内部缓存中获取指定bean对象，若没有，从上下文中获取，并放入内部缓存中
	 * 
	 * @param componentName bean名称或类
	 * @return bean对象
	 */
	public Object getComponent(String componentName) {
		Object component = componentMap.get(componentName);
		if (component != null) {
			return component;
		}
		component = getComponentInternal(componentName);
		if (component != null) {
			componentMap.putIfAbsent(componentName, component);
			return component;
		}
		return null;
	}

	public UserDetailsService getUserDetailsService() {
		return (UserDetailsService) getComponent(ComponentManager.USER_DETAIL_GETTER_BEAN_NAME);
	}

	public CurrentUserInfoGetter getCurrentUserInfoGetter() {
		return (CurrentUserInfoGetter) getComponent(ComponentManager.CURRENT_USER_INFO_GETTER_BEAN_NAME);
	}

	@SuppressWarnings("rawtypes")
	private Object getComponentInternal(Object componentName) {
		Object component = null;
		if (componentName instanceof Class) {
			component = getComponentFromContext(((Class) componentName).getName());
		} else {
			component = getComponentFromContext(componentName.toString());
		}

		if (component == null) {
			if (CURRENT_USER_INFO_GETTER_BEAN_NAME.equals(componentName)) {
				component = new DefaultCurrentUserInfoGetter();
			} else if (USER_DETAIL_GETTER_BEAN_NAME.equals(componentName)) {
				component = new UaaUserDetailsService();
			}
		}
		return component;
	}

	private Object getComponentFromContext(String componentName) {
		if (applicationContext == null) {
			throw new IllegalStateException("Spring ApplicationContext not initialized yet.");
		}
		if (applicationContext.containsBean(componentName)) {
			return applicationContext.getBean(componentName);
		}
		return null;
	}

	/**
	 * 清空缓存
	 */
	public void destroy() throws Exception {
		componentMap.clear();
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		instance.applicationContext = applicationContext;
	}
}
