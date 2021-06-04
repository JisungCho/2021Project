package kr.co.jisung.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign")
public class SignController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	//회원가입 페이지
	@RequestMapping("/signUp")
	public void signUp() {
	
	}
	
	
}
