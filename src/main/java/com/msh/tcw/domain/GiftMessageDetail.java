package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("gift_message_detail")
public class GiftMessageDetail {
    private Integer id;
    private int giftId;
    private String giftName;
    private String giftAvatar;
    private String giftGif;
    private String giftDes;
    private int giftNumber;
    private int giftTime;
}
