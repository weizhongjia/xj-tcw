package com.msh.tcw.dto;

import com.msh.tcw.domain.enums.ShowtimeType;
import lombok.Data;

@Data
public class ShowtimeDTO {
    private int time;
    private int price;
    private int roomId;
    private String blessing;
    private String src;
    private ShowtimeType type;
}
