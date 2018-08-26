package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.AbstractService;
import com.msh.tcw.dao.RoomImageMapper;
import com.msh.tcw.dao.RoomUserMapper;
import com.msh.tcw.dao.WxRoomMapper;
import com.msh.tcw.domain.Room;
import com.msh.tcw.domain.RoomImage;
import com.msh.tcw.domain.RoomUser;
import com.msh.tcw.domain.enums.RoomUserStatus;
import com.msh.tcw.service.WxRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
public class WxRoomServiceImpl extends AbstractService<Room> implements WxRoomService {
    @Resource
    private WxRoomMapper wxRoomMapper;
    @Resource
    private RoomImageMapper roomImageMapper;
    @Autowired
    private RoomUserMapper roomUserMapper;

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

    @Override
    public void upsertRoomUser(String openid, int roomId, RoomUserStatus status, String sessionId) {
        Long time = System.currentTimeMillis();
        RoomUser roomUser = new RoomUser(openid, roomId, status, sessionId, time, time);
        int affeted =  roomUserMapper.insertRoomUser(roomUser);
        if (affeted != 1) {
            log.error("更新房间用户数据出错，房间：{}， 用户：{}", roomId, openid);
        }
    }

    @Override
    public void leaveRoom(String openid, String sessionId) {
        Long time = System.currentTimeMillis();
        RoomUser roomUser = new RoomUser(openid, null, RoomUserStatus.LEAVED, sessionId, null, time);
        int affected = roomUserMapper.leaveRoom(roomUser);
        if (affected != 1) {
            log.error("用户离开房间时出错，用户：{}", openid);
        }
    }

    @Override
    public List<Room> findUserRoom(int roomId, int pageSize) {
        return wxRoomMapper.findUserRoom(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), roomId, pageSize);
    }

}
