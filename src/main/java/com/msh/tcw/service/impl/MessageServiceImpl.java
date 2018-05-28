package com.msh.tcw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.msh.tcw.core.ServiceException;
import com.msh.tcw.dao.GiftMessageDetailMapper;
import com.msh.tcw.dao.MessageMapper;
import com.msh.tcw.dto.MessageDTO;
import com.msh.tcw.model.GiftMessageDetail;
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
    @Autowired
    private GiftMessageDetailMapper giftMessageDetailMapper;
    @Override
    public void insertMessage(Message message) {
        switch (message.getType()) {
            case GIFT:
                int detailId = insertGiftMessageDetail(message.getGiftMessageDetail());
                message.setDetailId(detailId);
                break;
        }
        messageMapper.insert(message);
    }

    private int insertGiftMessageDetail(GiftMessageDetail detail) {
        if (detail == null) {
            throw new ServiceException("GIFT 类型消息，礼物信息不能为空");
        }
        int affectedRow = giftMessageDetailMapper.insert(detail);
        if (affectedRow == 0) {
            throw new ServiceException("数据插入失败");
        }
        return detail.getId();

    }

    @Override
    public List<Message> selectMessageByRoomIdAndTime(int roomId, long time) {
        return messageMapper.findMessageByRoomIdAndTime(roomId, time);
    }

    @Override
    public MessageDTO constructMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(message);
        messageDTO.setUser(userService.findBy("openid", message.getOpenId()));
        return messageDTO;
    }

}
