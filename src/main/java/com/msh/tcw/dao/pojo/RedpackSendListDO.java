package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.msh.tcw.model.RedpackStatus;
import com.msh.tcw.model.WxUser;
import lombok.Data;

@Data
@TableName("redpack_send_list")
public class RedpackSendListDO {
    private Integer id;
    private String openid;
    private Integer redpackId;
    private Integer money;
    private RedpackStatus status;
    private Long acceptTime;
    private Integer redpackPosition;

    @TableField(exist=false)
    private WxUser user;

    public RedpackSendListDO() {
    }

    public RedpackSendListDO(int redpackId, int money, RedpackStatus status, int redpackPosition) {
        this.redpackId = redpackId;
        this.money = money;
        this.status = status;
        this.redpackPosition = redpackPosition;
    }
}
