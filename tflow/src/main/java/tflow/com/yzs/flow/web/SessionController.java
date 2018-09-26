package tflow.com.yzs.flow.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tflow.com.yzs.flow.common.BaseController;
import tflow.com.yzs.flow.common.ResponseModel;
import tflow.com.yzs.flow.util.JedisUtils;

@RestController
@RequestMapping("/session")
public class SessionController extends BaseController{

	@RequestMapping("/getId")
	public ResponseModel getId(HttpServletRequest request) throws Exception {
		try {
			String sessionId = request.getSession().getId();
			JedisUtils.set("key", sessionId);
			return getSuccessModel(sessionId);
		} catch (Exception e) {
			logger.error("获取sessionId失败:", e);
			return getErrorModel(e);
		}
	}
	
	@RequestMapping("/getValue")
	public ResponseModel getValue() throws Exception {
		try {
			String sessionId = JedisUtils.getString("key");
			return getSuccessModel(sessionId);			
		} catch (Exception e) {
			return getErrorModel(e);
		}
	}
	
	@RequestMapping("/del")
	public ResponseModel del() throws Exception {
		try {
			JedisUtils.del("key");
			return getSuccessModel();
		} catch (Exception e) {
			logger.error("删除缓存失败", e);
			return getErrorModel(e);
		}
	}
	
	@RequestMapping("/send")
	public ResponseModel send() throws Exception {
		try {
			String str = "{\"0\":\"男\",\"1\":\"女\"}";
			JedisUtils.convertAndSend("orderListener", str);
			JedisUtils.convertAndSend("testListener", str);
			return getSuccessModel();
		} catch (Exception e) {
			logger.error("发送待处理字符串失败", e);
			return getErrorModel(e);
		}
	}
}
