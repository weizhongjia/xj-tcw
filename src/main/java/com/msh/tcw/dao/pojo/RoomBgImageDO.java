package com.msh.tcw.dao.pojo;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("room_bg_image")
public class RoomBgImageDO {
    private Integer id;
    private int roomId;
    private String src;
}
