package com.msh.tcw.model;

import com.msh.tcw.dao.pojo.GiftOrderDO;
import lombok.Data;

@Data
public class GiftOrder {
    private int giftId;
    private int number;
    private int totalMoney;
    private int totalTime;
    private String outTradeNo;

    public GiftOrder(GiftOrderDO orderDO) {
        this.giftId = orderDO.getGiftId();
        this.number = orderDO.getNumber();
        this.totalMoney = orderDO.getPrice() * this.number;
        this.outTradeNo = orderDO.getOutTradeNo();
        this.totalTime = orderDO.getCostTime();
    }

}
