package kr.co.jisung.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.co.jisung.mvc.service.TodoService;

@Component //개발자가 직접 개발한 클래스를 Bean으로 등록
public class Scheduler {
	
	@Autowired
	TodoService service;
	
	//매일 자정에 기간이 지난 todo이면서 상태가 ACTIVE인 todo는 상태를 HAS-DUE-DATE로 바꿔줌
	@Scheduled(cron = " */5 * * * * *")
	public void schedule1() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
		Date time = new Date();
		String time1 = format1.format(time);
		System.out.println(time1);
	}
}
