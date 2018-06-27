package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.domain.Message;
import com.msh.tcw.dto.MessageDTO;
import com.msh.tcw.security.WxSessionToken;
import com.msh.tcw.service.MessageService;
import com.msh.tcw.service.WxRoomService;
import com.msh.tcw.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private WxRoomService roomService;
    @Autowired
    private MessageService messageService;

    @MessageMapping("/send/text/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Result greeting(@DestinationVariable int roomId, StompHeaderAccessor accessor, Message message) throws Exception {
        if (roomService.validateRoom(roomId)) {
            message.setRoomId(roomId);
            message.setSendTime(System.currentTimeMillis());
            WxSessionToken sessionToken = (WxSessionToken) accessor.getUser();
            message.setOpenId(sessionToken.getDetails().getOpenid());
            if (message.getOrderDetail() != null) {
                message.setDetailId(message.getOrderDetail().getId());
            }
            messageService.insertMessage(message);
            MessageDTO messageDTO = messageService.constructMessageDTO(message);
            return ResultGenerator.genSuccessResult(messageDTO);
        } else {
            return ResultGenerator.genFailResult("房间尚未启用或已经过期");
        }

    }


}
