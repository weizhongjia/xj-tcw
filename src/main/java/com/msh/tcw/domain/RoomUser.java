package com.msh.tcw.domain;

import com.msh.tcw.domain.enums.RoomUserStatus;
import lombok.Data;

@Data
public class RoomUser {
    public RoomUser(String openid, Integer roomId, RoomUserStatus status, String sessionId, Long createTime, Long updateTime) {
        this.openid = openid;
        this.roomId = roomId;
        this.status = status;
        this.sessionId = sessionId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer id;
    public String openid;
    public Integer roomId;
    public RoomUserStatus status;
    public String sessionId;
    public Long createTime;
    public Long updateTime;
}
