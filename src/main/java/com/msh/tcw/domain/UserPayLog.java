package com.msh.tcw.domain;

import lombok.Data;

@Data
public class UserPayLog {
    private Integer id;
    private Integer orderId;
    private Integer account_payment;
    private Integer wechat_payment;
    private Integer wechat_order_id;
    private String description;
}
