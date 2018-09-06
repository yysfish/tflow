package tflow.com.yzs.flow.common;

public class ResponseCodeException extends RuntimeException{

	private String code;
	
	public ResponseCodeException(String msg) {
		super(msg);
	}

	public ResponseCodeException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
