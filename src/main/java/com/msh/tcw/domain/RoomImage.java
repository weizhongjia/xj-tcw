package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("room_bg_image")
public class RoomImage {
    private Integer id;
    private Integer roomId;
    private String src;
}
