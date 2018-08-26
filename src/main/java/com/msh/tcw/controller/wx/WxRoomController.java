package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.core.ServiceException;
import com.msh.tcw.domain.Room;
import com.msh.tcw.service.WxRoomService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping
    public Result getAllRoom(@RequestParam(required = false) Integer pageSize,
                             @RequestParam(required = false) Integer beforeId) {
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        if (beforeId == null || beforeId == 0) {
            beforeId = Integer.MAX_VALUE;
        }
        List<Room> rooms = wxRoomService.findUserRoom(beforeId, pageSize);
        return ResultGenerator.genSuccessResult(rooms);
    }
}
