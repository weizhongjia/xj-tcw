package com.msh.tcw.service.impl;

import com.msh.tcw.dao.MessageMapper;
import com.msh.tcw.model.Message;
import com.msh.tcw.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    public void insertMessage(Message message) {
        messageMapper.insert(message);
    }
}
