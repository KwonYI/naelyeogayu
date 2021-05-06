package com.a103.apiServer.buy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.a103.apiServer.member.MemberDao;
import com.a103.apiServer.model.Buy;
import com.a103.apiServer.model.Member;
import com.a103.apiServer.model.Product;
import com.a103.apiServer.product.ProductDao;

@Service
public class BuyServiceImpl implements BuyService{
	
	@Autowired
	BuyDao buyDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	MemberDao memberDao;
	
	@Override
	public int BuyProduct(long productId, Buy buy) {
		Product product = productDao.findProductById(productId);
		Member member = memberDao.findMemberById(buy.getMemberId());
		int buyProductCount = buy.getCount();
		int buyProductprice = buy.getPrice();
		int usePoint = buyProductCount * buyProductprice;
		int productStock = product.getStock();
		int productStatus = product.getStatus();
		int memberPoint = member.getPoint();
		
		if (productStatus == 0 && productStock >= buyProductCount && memberPoint >= usePoint) {

			if (productStock == buyProductCount) {
				product.setStatus(2);
			}

			member.setPoint(memberPoint - usePoint);
			memberDao.save(member);
			product.setStock(productStock - buyProductCount);
			productDao.save(product);
			buy.setProduct(product);
			buyDao.save(buy);
			return 1;
		}else if(productStock < buyProductCount){
			return 3;
		}else if(memberPoint < usePoint) {
			return 4;
		}
		
		return 5;

	}

}
