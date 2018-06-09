package com.msh.tcw.dao;

import com.msh.tcw.core.Mapper;
import com.msh.tcw.domain.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends Mapper<Message> {
    List<Message> findMessageByRoomIdAndTime(@Param("roomId") int roomId, @Param("lastTime") long lastTime);
}
