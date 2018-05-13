package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.model.Room;
import com.msh.tcw.service.WxRoomService;
import com.msh.tcw.websocket.Greeting;
import com.msh.tcw.websocket.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private WxRoomService roomService;

    @MessageMapping("/hello/{roomId}")
    @SendTo("/topic/greetings/{roomId}")
    public Result greeting(@DestinationVariable int roomId, HelloMessage message) throws Exception {
        if (roomService.validateRoom(roomId)) {
            return ResultGenerator.genSuccessResult(new Greeting("Hello, " + message.getName() + "!"));
        } else {
            return ResultGenerator.genFailResult("房间尚未启用或已经过期");
        }

    }

}
