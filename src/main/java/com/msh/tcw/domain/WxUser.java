package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("wx_user")
public class WxUser {
    private Integer id;

    private String openid;

    private String unionid;

    private String nickname;

    private Boolean gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarurl;

    private String timestamp;

    private String appid;
}