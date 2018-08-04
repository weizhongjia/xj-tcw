package com.msh.tcw.domain;

import lombok.Data;

@Data
public class Video {
    private Integer id;
    private String src;
    private String imageSrc;
    private Long createTime;
    private String title;
    private Integer typeId;
}
