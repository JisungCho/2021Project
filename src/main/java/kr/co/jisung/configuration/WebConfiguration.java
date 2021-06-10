package kr.co.jisung.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.jisung.handler.BaseHandlerInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer{

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		//메세지 프로퍼티 파일의 위치와 이름을 지정
		source.setBasename("classpath:/messages/message");
		//기본 인코딩을 지정
		source.setDefaultEncoding("UTF-8");
		//프로퍼티 파일의 변경을 감지할 시간 간격을 지정
		source.setCacheSeconds(60);
		//기본값
		source.setDefaultLocale(Locale.KOREAN);
		//없는 메세지일 경우 예외를 발생시키는 대신 코드를 기본 메세지로 한다.
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	
	//인터셉터 생성
	@Bean
	public BaseHandlerInterceptor baseHandlerInterceptor() {
		return new BaseHandlerInterceptor();
	}
	
	//인터셉터 등록
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(baseHandlerInterceptor());
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
