package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import com.msh.tcw.domain.enums.OrderType;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@TableName("user_order")
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

    public Order() {
    }

    public Order(OrderType orderType, Integer giftId, Integer price, Integer costTime, Integer number, Integer totalMoney, Integer roomId) {
        this.orderType = orderType;
        this.giftId = giftId;
        this.price = price;
        this.costTime = costTime;
        this.number = number;
        this.totalMoney = totalMoney;
        this.roomId = roomId;
        this.createTime = System.currentTimeMillis();
    }
}