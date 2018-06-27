package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{roomId}/before/{messageId}/{messageCount}")
    public Result getMessageBefore(@PathVariable int messageId, @PathVariable int messageCount, @PathVariable int roomId){
        return ResultGenerator.genSuccessResult(messageService.getMessageBefore(messageId, messageCount, roomId));
    }


}
