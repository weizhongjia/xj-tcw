package com.msh.tcw.dto;

import com.msh.tcw.domain.Order;
import lombok.Data;

@Data
public class GiftOrder {
    private int giftId;
    private int number;
    private int totalMoney;
    private int totalTime;
    private String outTradeNo;

    public GiftOrder(Order order) {
        this.giftId = order.getGiftId();
        this.number = order.getNumber();
        this.totalMoney = order.getPrice() * this.number;
        this.outTradeNo = order.getOutTradeNo();
        this.totalTime = order.getCostTime();
    }

}
