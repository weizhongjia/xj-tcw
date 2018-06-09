package com.msh.tcw.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("gift")
public class Gift {
    private Integer id;
    private String name;
    private String avatar;
    private String gif;
    private String des;
    private int price;
    private int costTime;
}
