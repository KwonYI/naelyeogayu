package com.a103.apiServer.product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a103.apiServer.member.MemberDao;
import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.ProductDetail;
import com.a103.apiServer.watchlog.WatchLogDao;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	WatchLogDao watchLogDao;

	@Autowired
	MemberDao memberDao;
	
	private static final int PRICE_UPDATE_TIME = 4;

	@Override
	public ProductDetail getProductDetail(Product product, LocalDateTime now) {
		int priceDiff = product.getStartPrice() - product.getMinPrice();
		int dayDiff = (int) (ChronoUnit.DAYS.between(product.getStartDate(), product.getEndDate()));
		float discountPricePerHour = (float) ((float) priceDiff / (dayDiff * 24 / PRICE_UPDATE_TIME));
		int timeDiff = (int) (ChronoUnit.HOURS.between(product.getStartDate().atStartOfDay(), now) / PRICE_UPDATE_TIME);
		int dDay = (int) ChronoUnit.DAYS.between(now.toLocalDate(), product.getEndDate());

		if (product.getStatus() != 0 || timeDiff < 0) {
			return new ProductDetail(product, product.getStartPrice(), 0, dDay);
		}

		int discountPrice = (int) (Math.round(discountPricePerHour * timeDiff));
		long watchCnt = watchLogDao.countWatchLogIdByProductId(product.getId());

		if (watchCnt != 0) {
			long memberCnt = memberDao.count();
			double watchRate = 1 - Math.round((double) watchCnt / memberCnt * 100) / 100.0;
			discountPrice = (int) (discountPrice * watchRate);
		}

		int curPrice = product.getStartPrice() - discountPrice;
		float discountRate = (float) discountPrice / product.getStartPrice() * 100;
		return new ProductDetail(product, curPrice, discountRate, dDay);
	}
}
