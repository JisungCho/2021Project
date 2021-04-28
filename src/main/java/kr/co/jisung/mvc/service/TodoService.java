package kr.co.jisung.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.jisung.mvc.domain.Todo;
import kr.co.jisung.mvc.repository.TodoRepository;

@Service
public class TodoService {
	
	@Autowired
	private TodoRepository repository;
	
	/*
	 * 등록처리
	 */
	public void save(Todo todo) {
		repository.save(todo);
	}
}
