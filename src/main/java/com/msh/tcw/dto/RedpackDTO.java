package com.msh.tcw.dto;

import com.msh.tcw.domain.RedpackSendHistory;
import lombok.Data;

@Data
public class RedpackDTO {
    private String avatar;
    private String nickname;
    private int money;
    private long openTime;
    private String openid;

    public RedpackDTO() {
    }

    public RedpackDTO(RedpackSendHistory sendListDO){
        this.avatar = sendListDO.getUser().getAvatarurl();
        this.nickname = sendListDO.getUser().getNickname();
        this.money = sendListDO.getMoney();
        this.openTime = sendListDO.getAcceptTime();
        this.openid = sendListDO.getOpenid();
    }
}
