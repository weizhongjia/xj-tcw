package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("wx_order")
public class WxOrderDO {
    private Integer id;
    private String outTradeNo;
    private String openid;
    private String tradeType;
    private long createTime;
    private String status;
    private long postTime;
    private String bankType;
    private long endTime;
    private String transactionId;
    private int totalFee;

    public WxOrderDO(String outTradeNo, String openid, String status, int totalFee) {
        this.outTradeNo = outTradeNo;
        this.openid = openid;
        this.createTime = System.currentTimeMillis();
        this.status = status;
        this.totalFee = totalFee;
    }
}