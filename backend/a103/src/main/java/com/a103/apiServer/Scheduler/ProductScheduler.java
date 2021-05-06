package com.a103.apiServer.Scheduler;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.a103.apiServer.model.Product;
import com.a103.apiServer.product.ProductController;
import com.a103.apiServer.product.ProductDao;

@Component
public class ProductScheduler {
	
private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductDao productDao;
	//매일 정각 마다 판매 상태 변경
	@Scheduled(cron = "0 0 0 * * *")
	public void StartSell() {
		logger.info("start sell");

		try {
			productDao.updateStatusByDate(0, 1, LocalDate.now());			
		} catch (Exception e) {
			logger.error("message :" + e);
		}		
		
	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void EndSell() {
		logger.info("end sell");

		try {
			productDao.updateStatusUnderDate(3, 0, LocalDate.now());			
		} catch (Exception e) {
			logger.error("message :" + e);
		}	
		
	}
	
}
