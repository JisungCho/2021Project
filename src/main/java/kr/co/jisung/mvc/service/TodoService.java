package kr.co.jisung.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jisung.mvc.domain.Todo;
import kr.co.jisung.mvc.repository.TodoRepository;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	/*
	 * 할일목록 가져오기
	 */
	public List<Todo> getList(){
		return repository.getList();
	};
	
	/*
	 * 해당 할일 가져오기
	 */
	
	/*
	 * 등록처리
	 */
	public void save(Todo todo) {
		repository.save(todo);
	}
	
	/*
	 * 삭제 처리
	 */
}
