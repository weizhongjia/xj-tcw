package com.msh.tcw.service;

import com.msh.tcw.domain.Video;
import com.msh.tcw.domain.VideoType;

import java.util.List;

public interface VideoTypeService {
    List<VideoType> getAllVideoType();
    List<Video> getVideoByType(int typeId, int beforeId, int size);
}
