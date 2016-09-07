package me.qiancheng.simple.springframework.beans.factory;

import com.sun.tools.javac.util.Assert;
import me.qiancheng.simple.springframework.beans.PropertyValue;
import me.qiancheng.simple.springframework.beans.PropertyValues;
import me.qiancheng.simple.springframework.beans.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qian.cheng
 */
public class BeanFactory {
	// 用于存储bean的单例实体
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);
	// 用于存储beanDefinition
	private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	
	// 注册BeanDefinition
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		beanDefinitionMap.put(beanName, beanDefinition);
	}
	
	// 获取bean，如果不存在则创建
	public Object getBean(String beanName) {
		Object bean = getSingleton(beanName);
		if(bean == null) {
			try {
				bean = doCreateBean(beanName, beanDefinitionMap.get(beanName));
			} catch (Exception e) {
				e.printStackTrace();
			}
			singletonObjects.put(beanName, bean);
		}
		return bean;
	}
	
	private Object getSingleton(String beanName) {
		return this.singletonObjects.get(beanName);
	}

	private Object doCreateBean(final String beanName, final BeanDefinition beanDefinition) throws Exception {
		Object bean = createBeanInstance(beanDefinition);
		applyPropertyValues(bean, beanDefinition);
		return bean;
	}
	
	private Object createBeanInstance(final BeanDefinition beanDefinition) throws Exception {
		Object bean;
		bean = beanDefinition.getBeanClass().newInstance();
		return bean;
	}
	
	private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception{
		PropertyValues pvs = beanDefinition.getPropertyValues();
		if(pvs != null) {
			for(PropertyValue pv : pvs.getPropertyValueList()) {
				ReflectionUtils.setFieldValue(bean.getClass().getDeclaredField(pv.getName()),pv.getName(),pv.getValue());
				/*
				Field field = bean.getClass().getDeclaredField(pv.getName());
				field.setAccessible(true);
				field.set(bean, pv.getValue());
				*/
			}
		}
	}
}
