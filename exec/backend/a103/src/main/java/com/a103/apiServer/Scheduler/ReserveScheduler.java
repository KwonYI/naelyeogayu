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

import com.a103.apiServer.buy.BuyDao;
import com.a103.apiServer.buy.BuyService;
import com.a103.apiServer.model.Buy;
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
	BuyDao buyDao;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	BuyService BuyService;
	
	@Scheduled(cron = "0 0 0 * * *")
	public void EndReserve() {
		logger.info("reserve expire");

		try {
			reserveDao.updateStatusByDate(2, 0, LocalDate.now());
		} catch (Exception e) {
			logger.error("message : " + e);

		}

	}
	
	@Scheduled(cron = "5 0 0/4 * * *")
	public void executeReserve() throws Exception {
		logger.info("execute expire");
		//예약중인 목록 검색
		List<Reserve> waitingList= reserveDao.findListReserveByStatusOrderBypriceDescIdAsc(0);

		for(Reserve waiting : waitingList) {
			//상품 정보 가져오기
			long productId = waiting.getProduct().getId();
			Product product = productDao.findProductById(productId);
			ProductDetail detail = productService.getProductDetail(product, LocalDateTime.now());
			logger.info("product : " + product + "waiting : " + waiting);

			//예약 프로세스
			if(detail.getCurPrice() <= waiting.getPrice()) {
				
				//재고 확인
				if(detail.getProduct().getStock() >= waiting.getCount()) {
					//구매
					Buy buy = new Buy();
					buy.setCount(waiting.getCount());
					buy.setMemberId(waiting.getMemberId());
					buy.setPrice(detail.getCurPrice());
					buy.setBuyDate(LocalDateTime.now());
					reserveDao.updateStatus(BuyService.BuyProduct(productId, buy), waiting.getId());
				}else {
					//재고 부족 구매 실패
					reserveDao.updateStatus(3, waiting.getId());
				}
				
			}
			
			if(detail.getProduct().getStock() < waiting.getCount()) {
				reserveDao.updateStatus(3, waiting.getId());
			}
			
			if(waiting.getDueDate().isBefore(LocalDate.now())) {
				reserveDao.updateStatus(2, waiting.getId());
			}
			
		}

	}
	
}
