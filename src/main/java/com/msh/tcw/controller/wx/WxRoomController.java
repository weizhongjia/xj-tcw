package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.core.ServiceException;
import com.msh.tcw.domain.Room;
import com.msh.tcw.service.WxRoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/wx/room")
public class WxRoomController {
    @Resource
    private WxRoomService wxRoomService;

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Room room = wxRoomService.findById(id);
        if (room.getStartTime() > System.currentTimeMillis() || room.getEndTime() < System.currentTimeMillis()) {
            throw new ServiceException("房间尚未开始或已经过期");
        }
        return ResultGenerator.genSuccessResult(room);
    }
}
