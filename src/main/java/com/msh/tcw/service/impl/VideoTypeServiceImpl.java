package com.msh.tcw.service.impl;

import com.msh.tcw.dao.VideoTypeMapper;
import com.msh.tcw.domain.Video;
import com.msh.tcw.domain.VideoType;
import com.msh.tcw.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoTypeServiceImpl implements VideoTypeService{

    @Autowired
    private VideoTypeMapper videoTypeMapper;
    @Override
    public List<VideoType> getAllVideoType() {
        return videoTypeMapper.findAllVideoType();
    }

    @Override
    public List<Video> getVideoByType(int typeId, int beforeId, int size) {
        return videoTypeMapper.findVideoByType(typeId, beforeId, size);
    }
}
