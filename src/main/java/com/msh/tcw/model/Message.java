package com.msh.tcw.model;

import com.msh.tcw.model.MessageDetail.MessageDetail;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private MessageType type;

    private String openId;

    private long sendTime;

    private Integer roomId;

    private String detail;

}
