package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.AbstractService;
import com.msh.tcw.dao.RoomImageMapper;
import com.msh.tcw.dao.WxRoomMapper;
import com.msh.tcw.domain.Room;
import com.msh.tcw.domain.RoomImage;
import com.msh.tcw.service.WxRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/01/03.
 */
@Service
@Transactional
public class WxRoomServiceImpl extends AbstractService<Room> implements WxRoomService {
    @Resource
    private WxRoomMapper wxRoomMapper;
    @Resource
    private RoomImageMapper roomImageMapper;

    public boolean validateRoom(int roomId) {
        Room room = findById(roomId);
        long currentTime = System.currentTimeMillis();
        return room != null && room.getStartTime() < currentTime && room.getEndTime() > currentTime;
    }

    public Page<Room> findRoomPage(Page<Room> page){
        return page.setRecords(wxRoomMapper.selectPage(page, new EntityWrapper<>()));
    }

    @Override
    public List<String> findRoomBgImages(int roomId) {
        List<RoomImage> roomBgImageDOs = roomImageMapper.selectList(new EntityWrapper<RoomImage>().eq("room_id", roomId));
        List<String> srcs = new ArrayList<>(roomBgImageDOs.size());
        for (RoomImage images : roomBgImageDOs) {
            srcs.add(images.getSrc());
        }
        return srcs;
    }

}
