package kr.co.jisung.mvc.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Todo {
	private int seq;
	private int member_seq;
	private String todo_content;
	private TodoType todo_state;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date todo_date;
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date reg_date;
	
	private Member member;
}
