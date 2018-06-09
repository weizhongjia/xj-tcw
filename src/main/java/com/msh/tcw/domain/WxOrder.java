package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("wx_order")
public class WxOrder {
    private Integer id;
    private String outTradeNo;
    private String openid;
    private String tradeType;
    private Long createTime;
    private String status;
    private Long postTime;
    private String bankType;
    private Long endTime;
    private String transactionId;
    private Integer totalFee;

    public WxOrder(String outTradeNo, String openid, String status, int totalFee) {
        this.outTradeNo = outTradeNo;
        this.openid = openid;
        this.createTime = System.currentTimeMillis();
        this.status = status;
        this.totalFee = totalFee;
    }
}