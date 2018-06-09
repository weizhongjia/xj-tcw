package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.msh.tcw.domain.enums.RedpackStatus;
import lombok.Data;

@Data
@TableName("redpack_send_history")
public class RedpackSendHistory {
    private Integer id;
    private String openid;
    private Integer redpackId;
    private Integer money;
    private RedpackStatus status;
    private Long acceptTime;
    private Integer redpackPosition;

    @TableField(exist=false)
    private WxUser user;

    public RedpackSendHistory() {
    }

    public RedpackSendHistory(int redpackId, int money, RedpackStatus status, int redpackPosition) {
        this.redpackId = redpackId;
        this.money = money;
        this.status = status;
        this.redpackPosition = redpackPosition;
    }
}
