package tflow.com.yzs.flow.listener;

import org.springframework.stereotype.Component;

@Component
public class TestListener extends AbstractListener<String>{
	
	/**
	 * 利用构造方法将需要监听的消息队列名称设置好，也可以利用InitializingBean实现
	 */
	public TestListener() {
		setListName("listKey");
	}
	
	@Override
	protected void processObj(String t) {
		logger.info("TestListener监听到的消息为:" + t);
		
	}
	

}
