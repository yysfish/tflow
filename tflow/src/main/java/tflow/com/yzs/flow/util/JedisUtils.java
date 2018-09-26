package tflow.com.yzs.flow.util;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class JedisUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);
	/**
	 * redisTemplate.opsForValue();//操作字符串
	 * redisTemplate.opsForHash();//操作hash
	 * redisTemplate.opsForList();//操作list
	 * redisTemplate.opsForSet();//操作set
	 * redisTemplate.opsForZSet();//操作有序set
	 */
	private static RedisTemplate<String, Object> template = SpringContextHolder.getBean("redisTemplate");
	
	/**
	 * 通过key获取值
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object value = null;
		try {
			value = template.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("根据key从redis缓存获取值时异常:", e);
		}
		return value;
	}
	
	public static String getString(String key) {
		return (String) get(key);
	}
	
	/**
	 * 设置缓存
	 * @param key
	 * @param value
	 */
	public static void set(String key, Object value) {
		try {			
			template.opsForValue().set(key, value);
		} catch (Exception e) {
			logger.error("入库redis缓存异常:", e);
		}
	}
	
	/**
	 * 设置缓存-有超时时间
	 * @param key
	 * @param value
	 * @param time 超时时间 单位:秒
	 */
	public static void set(String key, Object value, int time) {
		try {
			template.opsForValue().set(key, value, time, TimeUnit.SECONDS);			
		} catch (Exception e) {
			logger.error("入库redis缓存异常:", e);
		}
	}
	
	public static void del(String key) {
		try {			
			template.delete(key);
		} catch (Exception e) {
			logger.error("删除缓存失败", e);
		}
	}
	
	/**
	 * 向redis队列的最左边压入一个元素
	 * @param key
	 * @param value
	 * @return
	 */
	public static long leftPush(String key, Object value) {
		return template.opsForList().leftPush(key, value);
	}
	
	/**
	 * 弹出redis队列最右边的一个元素，弹出后该元素不存在于队列中
	 * @param key
	 * @return
	 */
	public static Object rightPop(String key) {
		return template.opsForList().rightPop(key);
	}
	
	/**
	 * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
	 * @param key
	 * @param time
	 * @return
	 */
	public static Object rightPop(String key, int time) {
		return template.opsForList().rightPop(key, time, TimeUnit.SECONDS);
	}
	
	/**
	 * 向redis容器中发送主题为topic的msg消息
	 * @param topic
	 * @param msg
	 */
	public static void convertAndSend(String topic, String msg) {
		template.convertAndSend(topic, msg);
	}
	
}
