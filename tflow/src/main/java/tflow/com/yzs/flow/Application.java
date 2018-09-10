package tflow.com.yzs.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import tflow.com.yzs.flow.common.TflowBanner;
/**
 * springboot项目的启动类
 * 
 * 	1.SpringBootApplication表示是启动入口类
 *  2.@MapperScan指定mybatis dao层等
 *  3.@EnableScheduling 开启定时任务
 * @author 005
 *
 */
@SpringBootApplication(scanBasePackages="tflow.com.yzs.flow")
@MapperScan(basePackages="tflow.com.yzs.flow.dao")
@EnableScheduling
public class Application {
	
	public static void main(String[] args) {
		/**
		 * 1.调用springApplication的静态方法
		 * 2.new一个springApplication对象，再调用其run()
		 * 第二种方式的好处是可以设置一些属性，如设置启动的banner图，如果对banner没要求可以直接用第一种方法
		 */
		//SpringApplication.run(Application.class, args);
		SpringApplication application = new SpringApplication(Application.class);
		application.setBanner(new TflowBanner());
		application.run(args);
	}
}
