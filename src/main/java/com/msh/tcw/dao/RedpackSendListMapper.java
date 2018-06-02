package com.msh.tcw.dao;

import com.msh.tcw.core.Mapper;
import com.msh.tcw.dao.pojo.RedpackSendListDO;
import com.msh.tcw.model.RedpackStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RedpackSendListMapper extends Mapper<RedpackSendListDO> {
    int insertBatch(List<RedpackSendListDO> list);
    List<RedpackSendListDO> selectRedpacksAccepted(@Param("redpackId")int redpackId, @Param("status")RedpackStatus status);
}
