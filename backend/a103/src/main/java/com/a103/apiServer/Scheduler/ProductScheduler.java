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
		List<Product> list = productDao.findListProudctByStatusAndStartDate(1, LocalDate.now());
		logger.info("change status of list : " + list);
		
		try {
			productDao.updateStartStatus(0, 1, LocalDate.now());			
		} catch (Exception e) {
			logger.error("message :" + e);
		}		
		
	}
	
}
