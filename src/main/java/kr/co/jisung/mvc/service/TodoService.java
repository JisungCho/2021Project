package kr.co.jisung.mvc.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jisung.mvc.domain.Todo;
import kr.co.jisung.mvc.domain.TodoType;
import kr.co.jisung.mvc.repository.MemberRepository;
import kr.co.jisung.mvc.repository.TodoRepository;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/*
	 * 할일목록 가져오기
	 */
	public List<Todo> getList(Todo todo){
		return repository.getList(todo);
	};
	
	/*
	 * 해당 할일 가져오기
	 */
	public Todo get(int seq) {
		return repository.get(seq);
	}
	
	/*
	 * 등록처리
	 */
	public void save(Todo todo) {
		repository.save(todo);
	}
	
	/*
	 * 수정처리
	 */
	public void update(Todo todo) {
		Date date = new Date();
		if(todo.getTodo_date()!=null && todo.getTodo_date().after(date)) {
			todo.setTodo_state(TodoType.ACTIVE);
		}
		repository.update(todo);
	}
	/*
	 * 삭제 처리
	 */
	public void delete(int seq) {
		repository.delete(seq);
	}
	
	/*
	 * 상태 변경
	 */
	public void changeState() {
		repository.changeState();
	}
}
