package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("redpack_send_lock")
public class RedpackSendLockDO {
    private Integer id;
    private int redpackId;
    private String openid;
    private long sendTime;

    public RedpackSendLockDO(int redpackId, String openid) {
        this.redpackId = redpackId;
        this.openid = openid;
        this.sendTime = System.currentTimeMillis();
    }
}
