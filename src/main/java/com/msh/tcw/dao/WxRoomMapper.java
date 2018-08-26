package com.msh.tcw.dao;


import com.msh.tcw.core.Mapper;
import com.msh.tcw.domain.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxRoomMapper extends Mapper<Room> {
    List<Room> findUserRoom(@Param("openid") String openid,
                            @Param("roomId") Integer roomId,
                            @Param("pageSize") Integer pageSize);
}