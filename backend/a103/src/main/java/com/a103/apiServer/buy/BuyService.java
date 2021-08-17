package com.a103.apiServer.buy;

import com.a103.apiServer.model.Buy;

public interface BuyService {
	public int BuyProduct(long productId, Buy buy) throws Exception;
}
