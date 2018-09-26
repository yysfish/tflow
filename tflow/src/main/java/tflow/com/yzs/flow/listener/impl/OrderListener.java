package tflow.com.yzs.flow.listener.impl;

import org.springframework.stereotype.Service;

import tflow.com.yzs.flow.common.BaseService;
import tflow.com.yzs.flow.listener.RedisListenerService;

@Service("orderListener")
public class OrderListener extends BaseService implements RedisListenerService{

	public void onListener(String jsonStr, String topic) throws Exception {
		logger.info("orderListener监听到的消息:" + jsonStr);
		logger.info("orderListener监听到的主题:" + topic);
	}

}
