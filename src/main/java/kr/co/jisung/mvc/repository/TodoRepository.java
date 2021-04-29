package kr.co.jisung.mvc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.co.jisung.mvc.domain.Todo;

@Repository
public interface TodoRepository {
	void save(Todo todo);
	List<Todo> getList();
}
