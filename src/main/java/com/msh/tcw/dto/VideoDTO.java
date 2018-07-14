package com.msh.tcw.dto;

import lombok.Data;

@Data
public class VideoDTO {
    private Integer orderId;
    private String openid;
    private Integer roomId;
    private String showtimeSrc;
    private String blessing;
    private String nickName;
    private String roomName;
    private String avatarUrl;
    private String roomEntered;
}
