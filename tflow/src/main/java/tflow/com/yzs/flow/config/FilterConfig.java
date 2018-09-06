package tflow.com.yzs.flow.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tflow.com.yzs.flow.filter.LoginFilter;
import tflow.com.yzs.flow.filter.TestFilter;

/**
 *    配置过滤器 方法如下:
 *   1.@webFilter标签 无法排序 执行顺序是过滤器名称字典排序然后倒序执行
 *   2.如FilterConfig类所示，通过@Configuration和@Bean，通过setOrder()来设置执行顺序，小的先执行
 *   3.把@bean方法放到启动类Application类中，同样可以实现
 * @author 005
 *
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean registerFilter1() {
		FilterRegistrationBean bean = new FilterRegistrationBean(new TestFilter());
		bean.setName("testFilter");
		bean.setOrder(1);
		bean.addUrlPatterns("/*");
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean registerFilter2() {
		FilterRegistrationBean bean = new FilterRegistrationBean(new LoginFilter());
		bean.setName("loginFilter");
		bean.setOrder(2);
		bean.addUrlPatterns("/*");
		return bean;
	}
}
