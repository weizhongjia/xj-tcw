package com.msh.tcw.dao;

import com.msh.tcw.domain.Video;
import com.msh.tcw.domain.VideoType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoTypeMapper {
    List<VideoType> findAllVideoType();
    List<Video> findVideoByType(@Param("typeId")int typeId, @Param("id")int id, @Param("size")int size);
}
