package com.msh.tcw.controller.admin;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return ResultGenerator.genSuccessResult(messageService.selectMessageByRoomIdAndTime(room, lastTime));
    }
}
