package com.a103.apiServer.Scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.a103.apiServer.product.ProductController;

@Component
public class LogScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	//10분마다 현재 시각 출력
	@Scheduled(cron = "0 0/10 * * * *")
	public void nowTimeAlert() {
		logger.info("현재 시간 : {}", new Date());
	}
	
	//매 1초마다 현재 시각 출력
//		@Scheduled(fixedDelay = 1000)
//		public void nowTimeAlertPerSec() {
//			logger.info("현재 시간 : {}", new Date());
//
//		}
}
