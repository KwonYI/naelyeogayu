package com.a103.apiServer.Scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.ProductDetail;
import com.a103.apiServer.model.Reserve;
import com.a103.apiServer.product.ProductController;
import com.a103.apiServer.product.ProductDao;
import com.a103.apiServer.product.ProductService;
import com.a103.apiServer.reserve.ReserveDao;

@Component
public class ReserveScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ReserveDao reserveDao;
	
	@Autowired
	ProductDao productDao;
		
	@Autowired
	ProductService productService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void EndReserve() {
		logger.info("reserve expire");
				
		try {
			reserveDao.updateStatusByDate(2, 0, LocalDate.now());
		} catch (Exception e) {
			logger.error("message : " + e);

		}

	}
	
	@Scheduled(cron = "0 0 0 * * *")
	public void executeReserve() {
		logger.info("execute expire");
		//예약중인 목록 검색
		List<Reserve> waitingList= reserveDao.findListReserveByStatusOrderBypriceDescIdAsc(0);
		for(Reserve waiting : waitingList) {
			//상품 정보 가져오기
			Product product = productDao.findProductById(waiting.getProduct().getId());
			ProductDetail detail = productService.getProductDetail(product, LocalDateTime.now());
			
			//예약 프로세스
			if(detail.getCurPrice() <= waiting.getPrice()) {
				
				//재고 확인
				if(detail.getProduct().getStock() >= waiting.getCount()) {
					//구매
					
				}
				else {
					//재고 부족 구매 실패
					reserveDao.updateStatus(3, waiting.getId());
				}
				
			}
			else {
				//잔액 부족 구매 실패
				reserveDao.updateStatus(4, waiting.getId());
			}
			
		}

	}
}
