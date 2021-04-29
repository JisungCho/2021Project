package kr.co.jisung.mvc.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jisung.configuration.BaseResponse;
import kr.co.jisung.configuration.BaseResponseCode;
import kr.co.jisung.exception.BaseException;
import kr.co.jisung.mvc.domain.Todo;
import kr.co.jisung.mvc.service.TodoService;

@Controller
@RequestMapping("/todolist")
public class TodoController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TodoService service;
	
	/*
	 * 할일 목록 가져오기
	 */
	@GetMapping("/dashboard")
	public void dashboard() {
		
	}
	
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse<Todo> add(@RequestBody Todo todo) {
		logger.info(todo.toString());
		if(StringUtils.isEmpty(todo.getTodo_content())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[] {"todo_content","내용"}); 
		}
		service.save(todo);
		return new BaseResponse<Todo>(todo);
	}
	
}
