package tflow.com.yzs.flow.listener;

import org.springframework.stereotype.Component;

@Component
public class TestListener2 extends AbstractListener<String>{

	
	public TestListener2() {
		setListName("listKey2");
	}
	
	@Override
	protected void processObj(String t) {
		logger.info("TestListener2监听到的消息为:" + t);
		
	}

}
