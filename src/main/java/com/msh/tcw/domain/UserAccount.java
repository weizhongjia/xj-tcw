package com.msh.tcw.domain;

import lombok.Data;

@Data
public class UserAccount {
    private Integer id;
    private Integer userId;
    private String openId;
    private Integer balance;
    private Long lastUpdate;
}
