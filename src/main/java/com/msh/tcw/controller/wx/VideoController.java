package com.msh.tcw.controller.wx;

import com.msh.tcw.core.Result;
import com.msh.tcw.core.ResultGenerator;
import com.msh.tcw.domain.Video;
import com.msh.tcw.domain.VideoType;
import com.msh.tcw.dto.VideoDTO;
import com.msh.tcw.service.OrderService;
import com.msh.tcw.service.VideoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wx/video")
public class VideoController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private VideoTypeService videoTypeService;

    @GetMapping
    public Result<List<VideoDTO>> getAllVideos(@RequestParam int orderId, @RequestParam int size) {
        return ResultGenerator.genSuccessResult(orderService.getHistoryVideos(orderId, size));
    }

    @GetMapping("/type")
    public Result<List<VideoType>> getAllType() {
        return ResultGenerator.genSuccessResult(videoTypeService.getAllVideoType());
    }

    @GetMapping("/type/{typeId}")
    public Result<List<Video>> getVideoByType(@PathVariable int typeId, @RequestParam int beforeId, @RequestParam int size) {
        return ResultGenerator.genSuccessResult(videoTypeService.getVideoByType(typeId, beforeId, size));
    }
    
}
