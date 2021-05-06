package com.a103.apiServer.Scheduler;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.a103.apiServer.product.ProductController;
import com.a103.apiServer.reserve.ReserveDao;

@Component
public class ReserveScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ReserveDao reserveDao;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void EndReserve() {
		logger.info("reserve expire");
				
		try {
			reserveDao.updateStatusByDate(2, 0, LocalDate.now());
		} catch (Exception e) {
			logger.error("message : " + e);

		}

	}
}
