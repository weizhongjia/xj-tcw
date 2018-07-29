package com.msh.tcw.domain;


import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("wx_room")
public class Room {

    private Integer id;

    private String roomName;

    private long startTime;

    private long endTime;

    private Boolean isLimitedByRegion;

    private long createTime;

    private String createUser;

    private String bgMovie;

    private String bgAudio;

    private String shareImage;
}
