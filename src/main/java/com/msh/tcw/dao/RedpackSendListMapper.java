package com.msh.tcw.dao;

import com.msh.tcw.core.Mapper;
import com.msh.tcw.dao.pojo.RedpackSendListDO;

import java.util.List;

public interface RedpackSendListMapper extends Mapper<RedpackSendListDO> {
    int insertBatch(List<RedpackSendListDO> list);
}
