package com.msh.tcw.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("message")
public class Message {

    private Integer id;

    private MessageType type;

    private String openId;

    private long sendTime;

    private Integer roomId;

    private String detail;

}
