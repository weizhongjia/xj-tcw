package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.msh.tcw.dao.MessageMapper;
import com.msh.tcw.dto.MessageDTO;
import com.msh.tcw.model.Message;
import com.msh.tcw.service.MessageService;
import com.msh.tcw.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private WxUserService userService;
    @Override
    public void insertMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public List<Message> selectMessageByRoomIdAndTime(int roomId, long time) {
        return messageMapper.selectList(new EntityWrapper<Message>().eq("room_id", roomId).gt("send_time", time));
    }

    @Override
    public MessageDTO constructMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(message);
        messageDTO.setUser(userService.findBy("openid", message.getOpenId()));
        return messageDTO;
    }

}
