package com.a103.apiServer.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.a103.apiServer.model.Member;

public class EchoHandler extends TextWebSocketHandler {
	// 로그인 한 전체에게 알림 주기
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1대1로 알림 주기
	Map<String, WebSocketSession> userSessionMap = new HashMap<String, WebSocketSession>();

	// 서버에 접속이 성공 했을때
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		String senderEmail = getEmail(session);
		userSessionMap.put(senderEmail, session);
	}

	// 소켓에 메세지를 보냈을때
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		// protocol : cmd, 유저Email, 상품ID, 상품가격

		if (StringUtils.isNotEmpty(msg)) {
			String[] strs = msg.split(",");

			if (strs != null && strs.length == 4) {
				String cmd = strs[0];
				String receiverEmail = strs[1];
				String productID = strs[2];
				String productPrice = strs[3];
				// 작성자가 로그인한 상태면
				WebSocketSession alarmSession = userSessionMap.get(receiverEmail);

				if ("reserveSuccess".equals(cmd) && alarmSession != null) {
					TextMessage tmpMsg = new TextMessage(receiverEmail + "님이 예약한 상품" + productID + "이 예약구매에 성공했습니다!");
					alarmSession.sendMessage(tmpMsg);
				} else if ("reserveFail".equals(cmd) && alarmSession != null) {
					TextMessage tmpMsg = new TextMessage(receiverEmail + "님이 예약한 상품" + productID + "이 예약구매에 실패했습니다!");
					alarmSession.sendMessage(tmpMsg);
				} else if ("bookmark".equals(cmd) && alarmSession != null) {
					TextMessage tmpMsg = new TextMessage(
							receiverEmail + "님이 북마크한 상품" + productID + "이 " + productPrice + "로 가격변동했습니다!");
					alarmSession.sendMessage(tmpMsg);
				}
			}
		}
	}

	// 연결 해제
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		userSessionMap.remove(session.getId());
		sessions.remove(session);
	}

	private String getEmail(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		Member loginUser = (Member) httpSession.get("loginUser");

		if (loginUser == null) {
			return session.getId();
		} else {
			return loginUser.getEmail();
		}

	}
}
