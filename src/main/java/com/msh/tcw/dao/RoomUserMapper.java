package com.msh.tcw.dao;

import com.msh.tcw.core.Mapper;
import com.msh.tcw.domain.RoomUser;

public interface RoomUserMapper extends Mapper<RoomUser> {
    int insertRoomUser(RoomUser roomUser);
    int leaveRoom(RoomUser roomUser);
}
