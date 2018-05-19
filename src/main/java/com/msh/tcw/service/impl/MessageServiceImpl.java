package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.msh.tcw.dao.MessageMapper;
import com.msh.tcw.model.Message;
import com.msh.tcw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public void insertMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public List<Message> selectMessageByRoomIdAndTime(int roomId, long time) {
        return messageMapper.selectList(new EntityWrapper<Message>().eq("room_id", roomId).ge("send_time", time));
    }

}
