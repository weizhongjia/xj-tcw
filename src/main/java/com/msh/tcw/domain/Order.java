package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.msh.tcw.domain.enums.OrderType;
import lombok.Data;

@Data
@TableName("order")
public class Order {
    private Integer id;
    private String outTradeNo;
    private String openid;
    private OrderType orderType;
    private Integer giftId;
    private Integer price;
    private Integer costTime;
    private Integer number;
    private Integer totalMoney;
    private Integer roomId;
    private Long createTime;

    public Order(String outTradeNo, String openid, OrderType orderType, int giftId, int price, int number, int roomId, int costTime) {
        this.outTradeNo = outTradeNo;
        this.openid = openid;
        this.orderType = orderType;
        this.giftId = giftId;
        this.price = price;
        this.number = number;
        this.roomId = roomId;
        this.costTime = costTime;
        this.createTime = System.currentTimeMillis();
    }

    public Order(String outTradeNo, String openid, OrderType orderType, Integer number, Integer totalMoney, Integer roomId) {
        this.outTradeNo = outTradeNo;
        this.openid = openid;
        this.orderType = orderType;
        this.number = number;
        this.totalMoney = totalMoney;
        this.roomId = roomId;
        this.createTime = System.currentTimeMillis();
    }

    public Order() {
    }
}