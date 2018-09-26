package tflow.com.yzs.flow.listener.impl;

import java.util.Map;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import tflow.com.yzs.flow.common.BaseService;
import tflow.com.yzs.flow.listener.RedisListenerService;
import tflow.com.yzs.flow.util.SpringContextHolder;

@Component("listenterDispatcher")
public class RedisListenerDispatcher extends BaseService implements MessageListener{

	
	private Map<String, RedisListenerService> listenterMap = null;

	public void onMessage(Message message, byte[] pattern) {
		String body = new String(message.getBody());
		String topic = new String(pattern);
		logger.info("body:" + body + ",topic:" + topic);
		try {
			if (listenterMap == null) {
				listenterMap = SpringContextHolder.getBeans(RedisListenerService.class);
			}
			RedisListenerService service = listenterMap.get(topic);
			if (service != null) {
				service.onListener(body, topic);
			} else {
				logger.info("listenterMap的keys:" + listenterMap.keySet());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
