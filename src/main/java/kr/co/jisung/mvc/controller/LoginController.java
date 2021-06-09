package kr.co.jisung.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.jisung.configuration.BaseResponse;
import kr.co.jisung.mvc.domain.Member;
import kr.co.jisung.mvc.service.SecurityService;

@Controller
@RequestMapping("/login")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SecurityService securityService; 
	
	//로그인 페이지
	@RequestMapping("/loginForm")
	public void loginForm() {
	}
	
	//회원가입 페이지
	@RequestMapping("/signUp")
	public void signUp() {
		logger.info("sign up");
	}
	
	@PostMapping("/save")
	@ResponseBody
	public BaseResponse<String> saveMember(@RequestBody Member member) {
		logger.info("Member"+member);
		return securityService.InsertMember(member);
	}
	
	
	
}
