package com.msh.tcw.controller.admin;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.domain.Message;
import com.msh.tcw.dto.BapingMessageDTO;
import com.msh.tcw.dto.MessageDTO;
import com.msh.tcw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/baping")
public class BapingController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/top/{room}/{type}")
    public Result top(@PathVariable int room, @PathVariable int type) {
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/more/{room}/{lastTime}")
    public Result more(@PathVariable int room, @PathVariable long lastTime) {
        List<MessageDTO> messageList = messageService.selectMessageByRoomIdAndTime(room, lastTime);
        List<BapingMessageDTO> dtos = new ArrayList<>(messageList.size());
        for (MessageDTO message : messageList) {
            dtos.add(new BapingMessageDTO(message));
        }
        return ResultGenerator.genSuccessResult(dtos);
    }
}
