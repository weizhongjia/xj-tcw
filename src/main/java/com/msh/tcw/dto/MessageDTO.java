package com.msh.tcw.dto;

import com.msh.tcw.model.Message;
import com.msh.tcw.model.WxUser;
import lombok.Data;

@Data
public class MessageDTO {
    private Message message;
    private WxUser user;

    public BapingMessageDTO getBapingDTO(){
        BapingMessageDTO dto = new BapingMessageDTO();
        dto.setId(message.getId());
        dto.setOpenid(user.getOpenid());
        dto.setAvatar(user.getAvatarurl());
        dto.setContent(message.getDetail());
        dto.setCreatetime(message.getSendTime());
        dto.setNickname(user.getNickname());
        dto.setSex(user.getGender()?"1":"0");
        dto.setType(message.getType().toString().toLowerCase());
        return dto;
    }
}
