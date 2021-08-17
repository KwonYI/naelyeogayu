package com.a103.apiServer.product;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a103.apiServer.buy.BuyDao;
import com.a103.apiServer.member.MemberDao;
import com.a103.apiServer.model.Buy;
import com.a103.apiServer.model.Product;
import com.a103.apiServer.model.ProductDetail;
import com.a103.apiServer.watchlog.WatchLogDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	WatchLogDao watchLogDao;

	@Autowired
	MemberDao memberDao;

	@Autowired
	BuyDao buydaDao;

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

	@Override
	public Map getSalesRecord(Product product) {
		int total_cnt = 0;
		int total_price = 0;
		Map result = new HashMap<>();
		List<Buy> saleRecords = buydaDao.findListBuyByProductId(product.getId());
		
		if(saleRecords.size() != 0) {
			
			for (Buy buy : saleRecords) {
				int cnt = buy.getCount();
				total_cnt += cnt;
				total_price += (buy.getPrice() * cnt);
			}
			
		}
		
		result.put("product", product);
		result.put("total_cnt", total_cnt);
		result.put("total_price", total_price);
		
		return result;
	}
}
