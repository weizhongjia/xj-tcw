package com.msh.tcw.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.msh.tcw.core.Service;
import com.msh.tcw.model.Room;

import java.util.List;

public interface WxRoomService extends Service<Room> {

    boolean validateRoom(int roomId);

    Page<Room> findRoomPage(Page<Room> page);

    List<String> findRoomBgImages(int roomId);
}
