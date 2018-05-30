package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("redpack")
public class RedpackDO {
    private Integer id;
    private String outTradeNo;
    private String owner;
    private int totalMoney;
    private int totalCount;
    private long sendTime;
    private int roomId;
    private long endTime;

    public RedpackDO(int money, int number, int roomId, String openid, String outTradeNo) {
        this.owner = openid;
        this.outTradeNo = outTradeNo;
        this.totalMoney = money;
        this.totalCount = number;
        this.roomId = roomId;
        this.sendTime = System.currentTimeMillis();
        this.endTime = this.sendTime + 24 * 60 * 60 * 1000;
    }
}
