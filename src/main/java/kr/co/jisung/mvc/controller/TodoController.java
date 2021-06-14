package kr.co.jisung.mvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jisung.configuration.BaseResponse;
import kr.co.jisung.configuration.BaseResponseCode;
import kr.co.jisung.exception.BaseException;
import kr.co.jisung.mvc.domain.Todo;
import kr.co.jisung.mvc.domain.TodoType;
import kr.co.jisung.mvc.service.SecurityService;
import kr.co.jisung.mvc.service.TodoService;

@Controller
@RequestMapping("/todolist")
public class TodoController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TodoService service;
	
	@Autowired
	private SecurityService securityService;
	
	/*
	 * 할일 목록 가져오기
	 */
	@GetMapping("/dashboard")
	public void dashboard(Model model, Authentication authentication,Todo todo ) {
		//member_seq얻기
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
		String member_id = userDetails.getUsername(); 
		int member_seq = securityService.findMemberSeq(member_id);
		
		todo.setMember_seq(member_seq);
		todo.setTodo_state(TodoType.ALL);
		
		List<Todo> todolist = service.getList(todo);
		
		if(todolist == null) {
			throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] {"목록"});
		}
		model.addAttribute("todolist",todolist);
		model.addAttribute("member_seq", member_seq);
	}	
	
	/*
	 * 셀렉트박스에서 선택한 데이터 
	 */
	@GetMapping("/select")
	@ResponseBody
	public BaseResponse<List<Todo>> select (@RequestParam TodoType select,Authentication authentication){
		
		Todo t1 = new Todo();
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal(); 
		String member_id = userDetails.getUsername(); 
		int member_seq = securityService.findMemberSeq(member_id);
		
		t1.setMember_seq(member_seq);
		t1.setTodo_state(select);
		logger.info("상태에 맞는 목록 가져오기");
		List<Todo> todo = service.getList(t1);
		
		return new BaseResponse<List<Todo>>(todo);
	}
	
	
	/*
	 * 할일등록
	 */
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse<Todo> add(@RequestBody Todo todo) {
		if(StringUtils.isEmpty(todo.getTodo_content())) {
			throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[] {"todo_content","내용"}); 
		}
		logger.info(todo.toString());
		service.save(todo);
		return new BaseResponse<Todo>(todo);
	}
	
	/*
	 * 할일 수정
	 */
	@PutMapping("/update")
	@ResponseBody
	public BaseResponse<Todo> update(@RequestBody Todo todo){
		logger.info("업데이트");
		service.update(todo);
		return new BaseResponse<Todo>(todo);
	}
	
	
	/*
	 * 할일 삭제
	 */
	@DeleteMapping("/delete/{seq}")
	@ResponseBody
	public BaseResponse<Boolean> delete(@PathVariable int seq){
		Todo todo = service.get(seq);
		if(todo == null) {
			return new BaseResponse<Boolean>(false);
		}
		service.delete(seq);
		return new BaseResponse<Boolean>(true);
	}
}
