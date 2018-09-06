package tflow.com.yzs.flow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
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
		SpringApplication.run(Application.class, args);
	}
}
