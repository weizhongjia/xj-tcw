package com.msh.tcw.websocket;

import com.alibaba.fastjson.JSONObject;
import com.msh.tcw.core.ProjectConstant;
import com.msh.tcw.model.Message;
import com.msh.tcw.security.WxSessionToken;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MessageWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {

        WxSessionToken useInfo = (WxSessionToken) session.getAttributes().get(ProjectConstant.WEBSOCKET_USERINFO_KEY);
        TextMessage msg = new TextMessage("Hello, " + message.getPayload() + "!");
        session.sendMessage(msg);
    }
}
