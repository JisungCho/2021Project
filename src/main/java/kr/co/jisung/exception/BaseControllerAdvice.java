package kr.co.jisung.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;

import kr.co.jisung.configuration.BaseResponse;

@ControllerAdvice
public class BaseControllerAdvice {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = {BaseException.class})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	private BaseResponse<String> handleBaseException(BaseException e, WebRequest request){
		//ResponseCode와 ResponseCode에 해당하는 예외처리 내용을 가져옴
		return new BaseResponse<String>(e.getResponseCode(),messageSource.getMessage(e.getResponseCode().name(), e.getArgs(), null));
	}
}
