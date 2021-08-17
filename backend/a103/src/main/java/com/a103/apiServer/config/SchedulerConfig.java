package com.a103.apiServer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.a103.apiServer.product.ProductController;

@Configuration
@EnableScheduling
public class SchedulerConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	private final int POOL_SIZE = 10;
	
	@Bean
	public TaskScheduler scheduler(){
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(POOL_SIZE);
		logger.info("scheduler size : " + scheduler.getPoolSize());
		return scheduler;
	}
}
