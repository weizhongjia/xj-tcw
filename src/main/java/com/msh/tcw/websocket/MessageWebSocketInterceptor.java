package com.msh.tcw.websocket;

import com.msh.tcw.core.ProjectConstant;
import com.msh.tcw.security.TokenAuthenticationUtils;
import com.msh.tcw.security.WxSessionToken;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by weizhongjia on 2018/5/6.
 */
public class MessageWebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String token = servletRequest.getServletRequest().getParameter(ProjectConstant.JWT_HEADER);
            if (token == null) {
                WxSessionToken userInfo = TokenAuthenticationUtils.getSessionFromToken(token);
                attributes.put(ProjectConstant.WEBSOCKET_USERINFO_KEY, userInfo);
                return true;
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
