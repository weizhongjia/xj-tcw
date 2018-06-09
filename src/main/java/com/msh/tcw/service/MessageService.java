package com.msh.tcw.service;

import com.msh.tcw.domain.Message;
import com.msh.tcw.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    void insertMessage(Message message);
    List<Message> selectMessageByRoomIdAndTime(int roomId, long time);
    MessageDTO constructMessageDTO(Message message);
}
