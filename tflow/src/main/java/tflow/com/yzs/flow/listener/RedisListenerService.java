package tflow.com.yzs.flow.listener;

public interface RedisListenerService {

	public void onListener(String jsonStr, String topic) throws Exception;
}
