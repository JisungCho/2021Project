package kr.co.jisung.exception;

import kr.co.jisung.configuration.BaseResponseCode;
import lombok.Data;

@Data
public class BaseException extends RuntimeException{
	private BaseResponseCode responseCode;
	private Object[] args;

	public BaseException() {
	}

	public BaseException(BaseResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public BaseException(BaseResponseCode responseCode, Object[] args) {
		this.responseCode = responseCode;
		this.args = args;
	}
	
	
	
}
