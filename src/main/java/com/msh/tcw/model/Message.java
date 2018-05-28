package com.msh.tcw.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("message")
public class Message {

    @TableId
    private Integer id;

    private MessageType type;

    private String openId;

    private long sendTime;

    private Integer roomId;

    private String detail;

    private Integer detailId;

    @TableField(exist=false)
    private GiftMessageDetail giftMessageDetail;
}
