package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("gift_order")
public class GiftOrderDO {
    private Integer id;
    private String outTradeNo;
    private String openid;
    private int giftId;
    private int price;
    private int costTime;
    private int number;
    private int roomId;
    private long createTime;

    public GiftOrderDO(String outTradeNo, String openid, int giftId, int price, int number, int roomId, int costTime) {
        this.outTradeNo = outTradeNo;
        this.openid = openid;
        this.giftId = giftId;
        this.price = price;
        this.number = number;
        this.roomId = roomId;
        this.costTime = costTime;
        this.createTime = System.currentTimeMillis();
    }

    public GiftOrderDO() {
    }
}