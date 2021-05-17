package com.a103.apiServer.mail;

import javax.mail.internet.MimeMessage;

import com.a103.apiServer.model.Buy;

public interface EmailService {
	public MimeMessage createSellMessage(Buy buy) throws Exception;
	public MimeMessage createBuyMessage(Buy buy) throws Exception;
	public void sendMessage(int type, Buy buy) throws Exception;
	
}
