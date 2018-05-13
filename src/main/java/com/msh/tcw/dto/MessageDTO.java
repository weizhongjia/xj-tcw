package com.msh.tcw.dto;

import com.msh.tcw.model.Message;
import com.msh.tcw.model.WxUser;
import lombok.Data;

@Data
public class MessageDTO {
    private Message message;
    private WxUser user;
}
