package com.msh.tcw.service.impl;

import com.msh.tcw.core.ServiceException;
import com.msh.tcw.dao.GiftMapper;
import com.msh.tcw.dao.GiftMessageDetailMapper;
import com.msh.tcw.dao.MessageMapper;
import com.msh.tcw.dao.OrderMapper;
import com.msh.tcw.domain.Gift;
import com.msh.tcw.domain.GiftMessageDetail;
import com.msh.tcw.domain.Message;
import com.msh.tcw.domain.WxUser;
import com.msh.tcw.domain.enums.MessageType;
import com.msh.tcw.dto.MessageDTO;
import com.msh.tcw.service.MessageService;
import com.msh.tcw.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private WxUserService userService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private GiftMapper giftMapper;
    @Override
    public void insertMessage(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public List<MessageDTO> selectMessageByRoomIdAndTime(int roomId, long time) {
        List<Message> messages = messageMapper.findMessageByRoomIdAndTime(roomId, time);
        return constructMessageDTOList(messages);
    }

    @Override
    public MessageDTO constructMessageDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage(message);
        messageDTO.setUser(userService.findBy("openid", message.getOpenId()));
        return messageDTO;
    }

    private List<MessageDTO> constructMessageDTOList(List<Message> messages) {
        Map<String, WxUser> userMap = new HashMap<>(64);
        List<MessageDTO> messageDTOs = new ArrayList<>(messages.size());
        for (Message message : messages) {
            MessageDTO messageDTO = new MessageDTO();
            if (message.getType().equals(MessageType.REDPACK) || message.getType().equals(MessageType.SHOWTIME) || message.getType().equals(MessageType.GIFT)){
                message.setOrderDetail(orderMapper.selectById(message.getDetailId()));
            }
            if (message.getType().equals(MessageType.GIFT)) {
                Gift gift = giftMapper.selectById(message.getOrderDetail().getGiftId());
                message.getOrderDetail().setGiftName(gift.getName());
                message.getOrderDetail().setGiftAvatar(gift.getAvatar());
                message.getOrderDetail().setGiftGif(gift.getGif());
            }
            messageDTO.setMessage(message);
            WxUser user = userMap.get(message.getOpenId());
            if (user == null) {
                user = userService.findBy("openid", message.getOpenId());
                userMap.put(message.getOpenId(), user);
            }
            messageDTO.setUser(user);
            messageDTOs.add(messageDTO);
        }
        return messageDTOs;
    }

}
