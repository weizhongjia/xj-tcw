package com.msh.tcw.dao;

import com.msh.tcw.core.Mapper;
import com.msh.tcw.domain.RedpackSendHistory;

import com.msh.tcw.domain.enums.RedpackStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RedpackSendHistoryMapper extends Mapper<RedpackSendHistory> {
    int insertBatch(List<RedpackSendHistory> list);
    List<RedpackSendHistory> selectRedpacksAccepted(@Param("redpackId")int redpackId, @Param("status")RedpackStatus status);
}
