package tflow.com.yzs.flow.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import sun.security.action.GetBooleanAction;

@Component
public class SpringContextHolder implements ApplicationContextAware , DisposableBean{

	private static ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
		
	}
	/**
	 * 获取指定类型的bean
	 * @param t
	 * @return
	 */
	public static <T> T getBean(Class<T> t) {
		return applicationContext.getBean(t);
	}
	
	/**
	 * 获取指定类型的所有bean
	 * @param t
	 * @return
	 */
	public static <T> Map<String, T> getBeans(Class<T> t) {
		return applicationContext.getBeansOfType(t);
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型
	 * @param name
	 * @return
	 */
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	public void destroy() throws Exception {
		applicationContext = null;
		
	}

}
