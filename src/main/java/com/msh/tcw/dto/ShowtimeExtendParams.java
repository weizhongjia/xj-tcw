package com.msh.tcw.dto;

import com.msh.tcw.domain.Order;
import lombok.Data;

@Data
public class ShowtimeExtendParams implements ExtendParams {
    private String video;
}
