package com.msh.tcw.dto;

import com.msh.tcw.domain.Message;
import com.msh.tcw.domain.WxUser;
import lombok.Data;

@Data
public class MessageDTO {
    private Message message;
    private WxUser user;
}
