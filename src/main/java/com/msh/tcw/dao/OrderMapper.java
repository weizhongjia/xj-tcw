package com.msh.tcw.dao;

import com.msh.tcw.core.Mapper;
import com.msh.tcw.domain.Order;
import com.msh.tcw.dto.VideoDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {
    List<VideoDTO> findHistoryVideo(@Param("orderId") int orderId, @Param("openid") String openid, @Param("pageSize") int pageSize);
}
