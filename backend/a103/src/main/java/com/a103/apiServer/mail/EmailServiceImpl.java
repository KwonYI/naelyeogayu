package com.a103.apiServer.mail;

import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.a103.apiServer.member.MemberDao;
import com.a103.apiServer.model.Buy;
import com.a103.apiServer.model.Member;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender emailSender;
	
	@Autowired
	MemberDao memberDao;

	@Override
	public void sendMessage(int type, Buy buy) throws Exception {
		//1: 판매 완료 메시지 , 2: 구매 완료 메시지
		MimeMessage message = null;
		
		switch(type) {		
			case 1:
				message = createSellMessage(buy);
				break;
			case 2:
				message = createBuyMessage(buy);
				break;			
		}
		
		emailSender.send(message);
	}

	@Override
	public MimeMessage createSellMessage(Buy buy) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		Member member = memberDao.findMemberById(buy.getProduct().getSellerId());
		message.addRecipients(RecipientType.TO, member.getEmail());// 보내는 대상
		message.setSubject("『내려가유』등록 하신 상품 " + buy.getProduct().getName() + " 가(이) 판매 되었습니다.");// 제목
		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요  [내려가유]입니다. </h1>";
		msgg += "<br>";
		msgg += "<p>등록하신 상품  " + buy.getProduct().getName() +" 가(이) " + buy.getCount() + " 개 판매 되었습니다.<p>";
		msgg += "<br>";
		msgg += "<p>판매 가격은  " + buy.getPrice() + "원 이며 " + buy.getBuyDate() +"에 판매 되었습니다.<p>";
		msgg += "<br>";
		msgg += "<p>감사합니다!<p>";
		msgg += "<br>";
		message.setText(msgg, "utf-8", "html");// 내용

		return message;
	}

	@Override
	public MimeMessage createBuyMessage(Buy buy) throws Exception {
		MimeMessage message = emailSender.createMimeMessage();
		Member member = memberDao.findMemberById(buy.getMemberId());
		message.addRecipients(RecipientType.TO, member.getEmail());// 보내는 대상
		message.setSubject("『내려가유』상품 " + buy.getProduct().getName() + " 가(이) 구매 되었습니다.");// 제목
		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<h1> 안녕하세요  [내려가유]입니다. </h1>";
		msgg += "<br>";
		msgg += "<p>상품  " + buy.getProduct().getName() +" 가(이) " + buy.getCount() + " 개 구매 되었습니다.<p>";
		msgg += "<br>";
		msgg += "<p>구매 가격은  " + buy.getPrice() + "원 이며 " + buy.getBuyDate() +"에 구매 되었습니다.<p>";
		msgg += "<br>";
		msgg += "<p>감사합니다!<p>";
		msgg += "<br>";
		message.setText(msgg, "utf-8", "html");// 내용

		return message;
	}

}