package com.msh.tcw.config.websocket;

import com.msh.tcw.service.WxRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
public class WebSocketApplicationListener implements ApplicationListener<SessionDisconnectEvent>{

    @Autowired
    private WxRoomService roomService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        log.info("这位用户离开了：" + sessionDisconnectEvent.getUser().getName());
        roomService.leaveRoom(sessionDisconnectEvent.getUser().getName(), sessionDisconnectEvent.getSessionId());
        log.info("websocket 断开通知成功。");
    }
}
