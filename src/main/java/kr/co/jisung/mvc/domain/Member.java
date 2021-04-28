package kr.co.jisung.mvc.domain;

import lombok.Data;

@Data
public class Member {
	private int seq;
	private String member_id;
	private String member_pw;
}
