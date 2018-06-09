package com.msh.tcw.dto;

import com.msh.tcw.domain.GiftMessageDetail;
import lombok.Data;

@Data
public class GiftExtendParams implements ExtendParams {
    private String gift_name;
    private int gift_times;
    private String gift_avatar;
    private String gift_gif;
    private String gift_des;
    private int gift_money;
    private String gift_show = "1";
    private String to_openid = "oXYajs4vj-rib3oL150U3ljUS2pc";
    private String to_nickname = "Delevent";
    private String to_avatar = "";
    private String to_sex = "1";
    private int gift_nums;

    public GiftExtendParams(GiftMessageDetail detail) {
        this.gift_name = detail.getGiftName();
        this.gift_times = detail.getGiftTime();
        this.gift_avatar = detail.getGiftAvatar();
        this.gift_gif = detail.getGiftGif();
        this.gift_des = detail.getGiftDes();
        this.gift_nums = detail.getGiftNumber();
    }
}
