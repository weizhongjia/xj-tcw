package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("redpack_send_lock")
public class RedpackSendLock {
    private Integer id;
    private Integer redpackId;
    private String openid;
    private Long sendTime;

    public RedpackSendLock(int redpackId, String openid) {
        this.redpackId = redpackId;
        this.openid = openid;
        this.sendTime = System.currentTimeMillis();
    }
}
