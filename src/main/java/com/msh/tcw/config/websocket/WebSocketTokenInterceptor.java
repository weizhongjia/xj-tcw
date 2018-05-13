package com.msh.tcw.config.websocket;

import com.msh.tcw.core.ProjectConstant;
import com.msh.tcw.security.TokenAuthenticationUtils;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.WxRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;


public class WebSocketTokenInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private WxRoomService roomService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //1. 判断是否首次连接请求
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            //2. 验证是否登录
            String authToken = accessor.getNativeHeader(ProjectConstant.JWT_HEADER).get(0);
            WxSessionToken token = TokenAuthenticationUtils.getSessionFromToken(authToken);
            if (token != null) {
                accessor.setUser(token);
                return message;
            }
            return null;
        }
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();
            if (destination.startsWith("/topic/room/")) {
                String[] destinationArray = destination.split("/");
                int roomId = Integer.valueOf(destinationArray[3]);
                if (!roomService.validateRoom(roomId)) {
                    throw new IllegalArgumentException("房间尚未创建或已经被关闭");
                }
            }
        }
        //不是首次连接，已经成功登陆
        return message;
    }

    public static void main(String[] args) {
        String d = "/topic/room/1";
        if (d.startsWith("/topic/room/")) {
            String[] destinationArray = d.split("/");
            System.out.println(destinationArray[0]);
        }
    }
}
