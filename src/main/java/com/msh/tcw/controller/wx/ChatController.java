package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.dto.MessageDTO;
import com.msh.tcw.model.Message;
import com.msh.tcw.service.MessageService;
import com.msh.tcw.service.WxRoomService;
import com.msh.tcw.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private WxRoomService roomService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private WxUserService userService;

    @MessageMapping("/send/text/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Result greeting(@DestinationVariable int roomId, Message message) throws Exception {
        if (roomService.validateRoom(roomId)) {
            message.setRoomId(roomId);
            message.setSendTime(System.currentTimeMillis());
            message.setOpenId("ozrMn4xQ7m60hcJ1ZkW6lJecX6jU");
            messageService.insertMessage(message);
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setMessage(message);
            messageDTO.setUser(userService.findBy("openid", message.getOpenId()));
            return ResultGenerator.genSuccessResult(messageDTO);
        } else {
            return ResultGenerator.genFailResult("房间尚未启用或已经过期");
        }

    }


}
