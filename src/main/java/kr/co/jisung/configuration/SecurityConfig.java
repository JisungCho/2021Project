package kr.co.jisung.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration//이 클래스를 빈으로 등록
@EnableWebSecurity//스프링 시큐리티의 기능을 활성화
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/css/**","/fonts/**","/images/**","/js/**","/vendor/**").permitAll()
			.antMatchers("/todolist/*").hasAnyRole("USER")
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/login/loginForm")
			.loginProcessingUrl("/j_spring_security_check")
			.defaultSuccessUrl("/todolist/dashboard")
			.failureHandler(authenticationFailureHandler)
			.usernameParameter("member_id")
			.passwordParameter("member_pw")
			.permitAll();
		
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login/loginForm")
			.permitAll();
		
		http.csrf().disable();
			
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("user").password(passwordEncoder().encode("1234")).roles("USER");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
