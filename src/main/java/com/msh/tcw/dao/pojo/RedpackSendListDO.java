package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import com.msh.tcw.model.RedpackStatus;
import lombok.Data;

@Data
@TableName("redpack_send_list")
public class RedpackSendListDO {
    private Integer id;
    private String openid;
    private int redpackId;
    private int money;
    private RedpackStatus status;
    private long acceptTime;

    public RedpackSendListDO() {
    }

    public RedpackSendListDO(int redpackId, int money, RedpackStatus status) {
        this.redpackId = redpackId;
        this.money = money;
        this.status = status;
    }
}
