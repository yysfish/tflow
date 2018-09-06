package tflow.com.yzs.flow.common;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tflow.com.yzs.flow.constant.ResponseConstant;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public ResponseModel getSuccessModel() {
		ResponseModel model = new ResponseModel();
		model.setCode(ResponseConstant.SUCCESS_CODE);
		model.setMsg(ResponseConstant.SUCCESS_MSG);
		return model;
	}
	
	public ResponseModel getSuccessModel(Object data) {
		ResponseModel model = getSuccessModel();
		model.put("data", data);
		return model;
	}
	
	
	public ResponseModel getSuccessModel(Map<String, Object> map) {
		ResponseModel model = getSuccessModel();
		if (map != null) {
			model.putAll(map);			
		}
		return model;
	}
	
	public ResponseModel getErrorModel(Exception e) {
		if (e instanceof ResponseCodeException) {
			ResponseCodeException codeException = (ResponseCodeException) e;
			return new ResponseModel(codeException.getCode(), codeException.getMessage());
		} else {
			return new ResponseModel(ResponseConstant.ERROR_CODE, ResponseConstant.ERROR_MSG);
		}
	}
}
