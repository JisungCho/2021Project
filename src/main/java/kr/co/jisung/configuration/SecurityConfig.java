package kr.co.jisung.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import kr.co.jisung.mvc.service.SecurityService;

@Configuration//이 클래스를 빈으로 등록
@EnableWebSecurity//스프링 시큐리티의 기능을 활성화
public class SecurityConfig extends  WebSecurityConfigurerAdapter {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private SecurityService securityService;
	
	@Bean//실제 인증을 한 이후에 인증이 완료되면 Authentication객체를 반환을 위한 bean등록
	public DaoAuthenticationProvider authenticationProvider(SecurityService securityService) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(securityService);
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return authenticationProvider;
	}
	
	/*
	 * 이미지 파일, css 파일, 자바스크립트 파일 을 접근 가능하게 처리하는 소스를 입력하면 된다.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**" , "/vendor/**","/favicon.ico", "/resources/**", "/error");
	}
	
	/*
	 * 이 메소드에서 URL 별 권한 설정, 로그인,세션 등등 HTTP 보안 관련 설정을 해주면 된다.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/join/*").permitAll()
			.antMatchers("/todolist/**").hasAnyAuthority("MEMBER","ADMIN")
			.anyRequest().authenticated();
		
		http.formLogin()
			.usernameParameter("member_id")
			.passwordParameter("member_pw")
			.loginPage("/join/loginForm")
			.loginProcessingUrl("/loginProcess")
			.defaultSuccessUrl("/todolist/dashboard")
			.failureHandler(authenticationFailureHandler)
			.permitAll();
		
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/join/loginForm")
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID");
		
			
	}

	/*
	 	4번과정
	 	여기서 DB 로부터 아이디,비번이 맞는지 해당 유저가 어떤 권한을 가지는지를 체크한다.
		UserDetailsService 인터페이스를 상속받은 클래스가 있다면 그 클래스에서 인증을 시도 하면된다. 
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider(securityService));
	}

	
}
