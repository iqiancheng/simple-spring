package me.qiancheng.simple.springframework.beans;

import me.qiancheng.simple.springframework.beans.factory.BeanDefinition;
import me.qiancheng.simple.springframework.beans.factory.BeanDefinitionHolder;
import me.qiancheng.simple.springframework.beans.factory.BeanFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author qian.cheng
 */
public class TestIoC {

	/**
	 * 简易Ioc
	 * @see BeanFactory#registerBeanDefinition(String, BeanDefinition)
	 * @see BeanFactory#getBean(String)
     */
	@Test
	public void testIoC() {

		// 1. 创建beanFactory
		BeanFactory beanFactory = new BeanFactory();
		
		// 2. 注册bean
		BeanDefinition definition = new BeanDefinition();
		definition.setBeanClassName("HelloWorld");

		BeanDefinitionHolder holder = new BeanDefinitionHolder("helloWorld", definition);
		beanFactory.registerBeanDefinition(holder.getBeanName(), holder.getBeanDefinition());
		
		// 3. 获取bean
		HelloWorld hello = (HelloWorld) beanFactory.getBean("helloWorld");
		assertEquals("Hello World!", hello.sayHello());
	}


	/**
	 * 简易Ioc & Property Injection
	 * @see BeanFactory#applyPropertyValues(Object, BeanDefinition)
	 * @see BeanFactory#registerBeanDefinition(String, BeanDefinition)
	 * @see BeanFactory#getBean(String)
	 */
	@Test
	public void testPropertyInjection() {
		// 1. 创建beanFactory
		BeanFactory beanFactory = new BeanFactory();
		
		// 2. 注册bean
		BeanDefinition bd = new BeanDefinition();
		bd.setBeanClassName("HelloWorld");
		
		// 注入Property
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("msg", "Hello IoC Property!"));
		bd.setPropertyValues(propertyValues);
		
		BeanDefinitionHolder holder = new BeanDefinitionHolder("helloWorld", bd);
		beanFactory.registerBeanDefinition(holder.getBeanName(), holder.getBeanDefinition());
		
		// 3. 获取bean
		HelloWorld hello = (HelloWorld) beanFactory.getBean("helloWorld");
		assertEquals("Hello IoC Property!", hello.sayHello());
	}
}
