package kr.co.jisung.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jisung.configuration.BaseResponse;

@Controller
@RequestMapping("/login")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	//로그인 페이지
	@RequestMapping("/loginForm")
	public void loginForm() {
	
	}
	
	
	
}
