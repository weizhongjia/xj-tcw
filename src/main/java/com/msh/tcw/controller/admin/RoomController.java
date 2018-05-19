package com.msh.tcw.controller.admin;

import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.core.ServiceException;
import com.msh.tcw.model.Room;
import com.msh.tcw.service.WxRoomService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/admin/room")
public class RoomController {
    @Resource
    private WxRoomService wxRoomService;

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Room room = wxRoomService.findById(id);
        if (room.getStartTime() < System.currentTimeMillis() || room.getEndTime() > System.currentTimeMillis()) {
            throw new ServiceException("房间尚未开始或已经过期");
        }
        return ResultGenerator.genSuccessResult(room);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {

        Page<Room> list = wxRoomService.findRoomPage(new Page<>(page, size));
        return ResultGenerator.genSuccessResult(list);
    }

    @PutMapping
    public Result create(@RequestBody Room room) {
        if (room.getId() != null && room.getId() != 0) {
            wxRoomService.update(room);
        } else {
            room.setCreateTime(System.currentTimeMillis());
            room.setCreateUser((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            wxRoomService.save(room);
        }
        return ResultGenerator.genSuccessResult();
    }
}
