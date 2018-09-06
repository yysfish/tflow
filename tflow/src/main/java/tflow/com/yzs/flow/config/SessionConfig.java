package tflow.com.yzs.flow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 *  maxInactiveIntervalInSeconds配置的是会话的超时时间，默认不配置是30分钟， 是@EnableRedisHttpSession的一个属性
 * @author 005
 *
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig {

}
